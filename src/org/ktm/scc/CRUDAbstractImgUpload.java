package org.ktm.scc;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.ktm.authen.Authenticator;
import org.ktm.core.KTMContext;
import org.ktm.dao.Dao;
import org.ktm.dao.KTMEMDaoFactory;
import org.ktm.dao.gallery.ImageDao;
import org.ktm.dao.image_upload.ImageUploadDao;
import org.ktm.dao.party.AuthenDao;
import org.ktm.dao.party.EmploymentDao;
import org.ktm.domain.KTMEntity;
import org.ktm.domain.gallery.Image;
import org.ktm.domain.party.Authen;
import org.ktm.domain.party.Division;
import org.ktm.domain.party.Employee;
import org.ktm.domain.party.Employment;
import org.ktm.domain.party.PartyRole;
import org.ktm.domain.upload.ImageUpload;
import org.ktm.exception.CreateException;
import org.ktm.exception.DeleteException;
import org.ktm.exception.KTMException;
import org.ktm.scc.bean.ImageBean;
import org.ktm.scc.bean.UploadImageBean;
import org.ktm.servlet.CRUDServlet;
import org.ktm.servlet.file.UploadServlet;
import org.ktm.utils.DateUtils;
import org.ktm.utils.Globals;
import org.ktm.web.bean.FormBean;
import org.ktm.web.tags.Functions;

public abstract class CRUDAbstractImgUpload extends CRUDServlet {

	private static final long	serialVersionUID	= 1L;

	private static Logger		logger				= Logger.getLogger( CRUDAbstractImgUpload.class );

	private File				fileUploadPath;

	protected abstract Dao getDao();
	protected abstract ImageUpload getNewEntity();
	protected abstract String getUUID();

	@Override
	public void init( ServletConfig config ) {
		ServletContext context = config.getServletContext();
		String upload_path = context.getInitParameter( "upload_path" );
		fileUploadPath = new File( upload_path );
	}

	protected void listData( FormBean bean ) {

		Dao dao = getDao();

		Collection<?> list = dao.find( bean.getPageNumber() + 1 );

		bean.setMaxPage( KTMContext.paging );
		bean.setMaxRows( (int) dao.getCount() );

		try {
			bean.loadFormCollection( list );
		}
		catch ( KTMException e ) {
			e.printStackTrace();
		}
	}

	public void newImageUpload( UploadImageBean bean, HttpSession session ) {

		bean.getImages().clear();

		// New Gallery
		try {
			String uuid = Globals.generateUniqueId();
			session.setAttribute( getUUID(), uuid );
			session.setAttribute( uuid, bean );
			bean.setIdentifier( uuid );

			try {
				bean.setDateCreated( DateUtils.formatDate( new Date() ) );
			}
			catch ( ParseException e ) {
				e.printStackTrace();
			}
		}
		catch ( NoSuchAlgorithmException e ) {
			e.printStackTrace();
		}

		if ( bean.getServlet() != null && bean.getServlet() instanceof CRUDGalleryServlet ) {
			( (CRUDGalleryServlet) bean.getServlet() ).resetAllCRUDCollection( session );
		}
	}

	private void deleteFile( Image image ) {

		File imgFile = new File( fileUploadPath, image.getPath() );

		if ( imgFile.exists() ) {

			imgFile.delete();

			logger.debug( "CRUDAbstractImgUpload Delete file : " + imgFile );

		} else {

			logger.debug( "CRUDAbstractImgUpload Delete file not exist : " + imgFile );

		}
	}

	public void delImageUpload( UploadImageBean bean, HttpSession session ) {

		ImageDao imageDao = KTMEMDaoFactory.getInstance().getImageDao();
		Dao dao = getDao();

		ImageUpload imgUpload = (ImageUpload) dao.get( Integer.valueOf( bean.getUniqueId() ) );
		bean.loadToForm( imgUpload );

		if ( imgUpload != null ) {

			for ( Image img : imgUpload.getImages() ) {
				addDeleted( session, img );
			}

			// delete images
			imgUpload.getImages().clear();

			try {

				dao.delete( imgUpload );

				for ( Object obj : getDeleteCollection( session ) ) {
					if ( obj instanceof Image ) {
						imageDao.delete( ( (Image) obj ).getUniqueId() );
						deleteFile( (Image) obj );
					}
				}

				File toDeletePath = UploadServlet.getFilePath( fileUploadPath,
						bean );
				if ( toDeletePath.exists() ) {
					toDeletePath.delete();
				}
			}
			catch ( DeleteException e ) {
				e.printStackTrace();
			}
		}
	}
	public synchronized static void
			doSaveImageUpload( UploadImageBean bean, HttpServletRequest request )	throws CreateException,
																					DeleteException {
		HttpSession session = request.getSession();

		if ( bean.getServlet() != null && bean.getServlet() instanceof CRUDAbstractImgUpload ) {
			CRUDAbstractImgUpload servlet = (CRUDAbstractImgUpload) bean.getServlet();

			ImageDao imageDao = KTMEMDaoFactory.getInstance().getImageDao();
			ImageUploadDao dao = (ImageUploadDao) servlet.getDao();

			AuthenDao authenDao = KTMEMDaoFactory.getInstance().getAuthenDao();
			EmploymentDao empDao = KTMEMDaoFactory.getInstance()
					.getEmploymentDao();

			logger.debug( "CRUDAbstractImgUpload doSaveGallery Gallery-id  [thread_id=" + Thread.currentThread()
									.getId()
							+ "]" );

			if ( dao != null ) {
				ImageUpload imageUpload = null;
				if ( !Functions.isEmpty( bean.getUniqueId() ) ) {
					Integer aid = Integer.valueOf( bean.getUniqueId() );
					imageUpload = (ImageUpload) dao.get( aid );
				}
				if ( imageUpload == null ) {
					imageUpload = dao.findByIdentifier( bean.getIdentifier() );
					if ( imageUpload == null ) {
						imageUpload = servlet.getNewEntity();
					}

					// Assign Division only on create
					Object userObj = session.getAttribute( Authenticator.PROP_USERNAME );
					if ( userObj != null && userObj instanceof String ) {
						String username = (String) userObj;
						Authen auth = authenDao.findByUsername( username );
						if ( auth != null ) {
							Set<PartyRole> roles = (Set<PartyRole>) auth.getParty()
									.getRoles();
							if ( roles != null && roles.size() > 0 ) {
								for ( PartyRole role : roles ) {
									if ( role instanceof Employee ) {
										Employment emp = empDao.findByClient( role.getUniqueId() );
										PartyRole supply = emp.getSupply();
										if ( supply != null && supply instanceof Division ) {
											imageUpload.setAuthor( (Division) supply );

											logger.debug( "CRUDGalleryServlet set division: " + supply.getName() );

										}
									}
								}
							}
						}
					}
				}

				bean.syncToEntity( imageUpload );

				Set<Image> entityCollection = imageUpload.getImages();

				servlet.addAllRetriveCollection( session, entityCollection );

				// Create image key
				Map<String,Image> imageKey = new HashMap<String,Image>();

				for ( Image img : entityCollection ) {

					// if not exist in bean, the image was deleted
					if ( !bean.getImages().containsKey( img.getPath() ) ) {

						// add delete image to collection
						servlet.addDeleted( session, img );
					} else {

						// keep remain to map
						imageKey.put( img.getPath(), img );
					}
				}

				Iterator<String> it = bean.getImages().keySet().iterator();

				while ( it.hasNext() ) {

					String key = it.next();

					// if key not exist imageKey, the image was added (the new image)
					if ( !imageKey.containsKey( key ) ) {

						Image newImage = new Image();

						// Get image bean
						ImageBean imageBean = bean.getImages().get( key );

						// Sync to new entity
						imageBean.syncToEntity( newImage );

						// add new image to collection
						servlet.addCreated( session, newImage );
					}
				}

				for ( Object obj : servlet.getDeleteCollection( session ) ) {
					if ( obj instanceof Image ) {
						imageUpload.getImages().remove( obj );
					}
				}

				for ( Object obj : servlet.getCreateCollection( session ) ) {
					if ( obj instanceof Image ) {
						try {
							imageDao.createOrUpdate( (KTMEntity) obj );
							imageUpload.getImages().add( (Image) obj );
						}
						catch ( Exception ex ) {
							ex.printStackTrace();
						}
					}
				}
				servlet.getCreateCollection( session ).clear();

				// Create or Update Image Gallery
				dao.createOrUpdate( imageUpload );

				// now need to reload new data from entity
				bean.loadToForm( imageUpload );

				// Then delete image in collection
				for ( Object obj : servlet.getDeleteCollection( session ) ) {
					if ( obj instanceof Image ) {
						imageDao.delete( ( (Image) obj ).getUniqueId() );
					}
				}
				servlet.getDeleteCollection( session ).clear();
			}
		}
	}

}
