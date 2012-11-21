package org.ktm.scc;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.ktm.crypt.KTMCrypt;
import org.ktm.dao.Dao;
import org.ktm.dao.KTMEMDaoFactory;
import org.ktm.dao.gallery.GalleryDao;
import org.ktm.domain.gallery.Gallery;
import org.ktm.domain.upload.ImageUpload;
import org.ktm.exception.CreateException;
import org.ktm.exception.DeleteException;
import org.ktm.exception.KTMException;
import org.ktm.scc.bean.GalleryBean;
import org.ktm.scc.bean.UploadImageBean;
import org.ktm.servlet.ActionForward;
import org.ktm.utils.DateUtils;
import org.ktm.web.bean.FormBean;

@WebServlet( "/RGB7-backoffice-v4/CRUDGallery" )
public class CRUDGalleryServlet extends CRUDAbstractImgUpload {

	private static final long	serialVersionUID	= 1L;
	private static Logger		logger				= Logger.getLogger( CRUDGalleryServlet.class );

	@Override
	public String getBeanClass() {
		return "org.ktm.scc.bean.GalleryBean";
	}

	public	ActionForward
			listGallery(	FormBean form,
							HttpServletRequest request,
							HttpServletResponse response )	throws ServletException,
															IOException,
															KTMException {
		logger.debug( ">>> listGallery: begin" );

		listData( request.getSession(), form );

		logger.debug( ">>> listGallery: end" );

		return ActionForward.getUri( this, request, "ListGallery.jsp" );
	}
	public	ActionForward
			editGallery(	FormBean form,
							HttpServletRequest request,
							HttpServletResponse response )	throws ServletException,
															IOException {
		HttpSession session = request.getSession();
		GalleryBean bean = (GalleryBean) form;

		logger.debug( ">>> editGallery: begin" );
		logger.debug( "CRUDGalleryServlet editing Gallery-id: " + bean.getUniqueId() );

		GalleryDao galleryDao = KTMEMDaoFactory.getInstance().getGalleryDao();
		Gallery gallery = (Gallery) galleryDao.get( Integer.parseInt( bean.getUniqueId() ) );

		if ( gallery != null ) {

			bean.loadToForm( gallery );

			session.setAttribute( GalleryBean.UNIQUD_ID, bean.getIdentifier() );
			session.setAttribute( bean.getIdentifier(), bean );

			try {
				bean.setDateModified( DateUtils.formatDate( new Date() ) );
			}
			catch ( ParseException e ) {
				e.printStackTrace();
			}

			if ( bean.getServlet() != null && bean.getServlet() instanceof CRUDGalleryServlet ) {
				( (CRUDGalleryServlet) bean.getServlet() ).resetAllCRUDCollection( session );
			}

			logger.debug( ">>> editGallery: end" );

			return ActionForward.getUri( this, request, "EditGallery.jsp" );
		}

		return ActionForward.getAction( this,
				request,
				"index?page=CRUDGallery&t=t&param=" + KTMCrypt.encrypt( "method=list&module=gallery&pageNumber=" + bean.getPageNumber() ),
				true );
	}

	public	ActionForward
			newGallery( FormBean form,
						HttpServletRequest request,
						HttpServletResponse response )	throws ServletException,
														IOException {
		HttpSession session = request.getSession();
		GalleryBean bean = (GalleryBean) form;

		logger.debug( ">>> newGallery: begin" );

		newImageUpload( bean, session );

		logger.debug( ">>> newGallery: end" );

		return ActionForward.getUri( this, request, "EditGallery.jsp" );
	}

	public	ActionForward
			saveGallery(	FormBean form,
							HttpServletRequest request,
							HttpServletResponse response )	throws ServletException,
															IOException,
															KTMException {
		GalleryBean bean = (GalleryBean) form;

		logger.debug( ">>> saveGallery: begin" );

		try {
			doSaveImageUpload( bean, request );
		}
		catch ( CreateException e ) {
			e.printStackTrace();
		}
		catch ( DeleteException e ) {
			e.printStackTrace();
		}

		if ( bean.getServlet() != null && bean.getServlet() instanceof CRUDGalleryServlet ) {
			( (CRUDGalleryServlet) bean.getServlet() ).resetAllCRUDCollection( request.getSession() );
		}

		logger.debug( ">>> saveGallery: end" );

		return ActionForward.getAction( this,
				request,
				"index?page=CRUDGallery&t=t&param=" + KTMCrypt.encrypt( "method=list&module=gallery&pageNumber=" + bean.getPageNumber() ),
				true );
	}

	public	ActionForward
			delGallery( FormBean form,
						HttpServletRequest request,
						HttpServletResponse response )	throws ServletException,
														IOException,
														KTMException {
		logger.debug( ">>> delGallery: begin" );

		delImageUpload( (UploadImageBean) form, request.getSession() );

		logger.debug( ">>> delGallery: end" );

		return ActionForward.getAction( this,
				request,
				"index?page=CRUDGallery&t=t&param=" + KTMCrypt.encrypt( "method=list&module=gallery&pageNumber=" + form.getPageNumber() ),
				true );
	}

	@Override
	protected Dao getDao() {
		return KTMEMDaoFactory.getInstance().getGalleryDao();
	}

	@Override
	protected ImageUpload getNewEntity() {
		return new Gallery();
	}

	@Override
	protected String getUUID() {
		return GalleryBean.UNIQUD_ID;
	}
}
