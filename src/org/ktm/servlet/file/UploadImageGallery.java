package org.ktm.servlet.file;

import java.io.File;
import java.util.Vector;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.ktm.exception.CreateException;
import org.ktm.exception.DeleteException;
import org.ktm.scc.CRUDGalleryServlet;
import org.ktm.scc.bean.GalleryBean;
import org.ktm.scc.bean.ImageBean;

@WebServlet( "/RGB7-backoffice-v4/upload_image_gallery" )
public class UploadImageGallery extends UploadServlet {

	private static final long	serialVersionUID	= 1L;
	private static Logger		logger				= Logger.getLogger( UploadImageGallery.class );

	@Override
	public String getBeanClass() {
		return "org.ktm.scc.bean.UploadImageGallery";
	}

	@Override
	protected void removeImage( HttpServletRequest request, File filename ) {
		HttpSession session = request.getSession();

		String uuid = (String) session.getAttribute( GalleryBean.UNIQUD_ID );
		GalleryBean bean = (GalleryBean) session.getAttribute( uuid );

		if ( bean != null && filename != null ) {

			String rootPath = fileUploadPath.getAbsolutePath();

			String key = filename.getAbsolutePath();
			key = key.substring( rootPath.length() + 1 );

			if ( bean.getImages().containsKey( key ) ) {
				bean.getImages().remove( key );
			}

			try {
				CRUDGalleryServlet.doSaveImageUpload( bean, request );
			}
			catch ( CreateException e ) {
				e.printStackTrace();
			}
			catch ( DeleteException e ) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected synchronized void addImage(	HttpServletRequest request,
											Vector<FileItem> items ) {
		HttpSession session = request.getSession();

		logger.debug( "UplaodImageGallery.addImage begin... [thread_id=" + Thread.currentThread()
								.getId()
						+ "]" );

		String uuid = (String) session.getAttribute( GalleryBean.UNIQUD_ID );
		GalleryBean bean = (GalleryBean) session.getAttribute( uuid );

		logger.debug( "Load GalleryBean form session: " + bean.getIdentifier() );

		if ( bean != null && items != null && items.size() > 0 ) {

			try {
				for ( FileItem item : items ) {

					ImageBean img = new ImageBean();

					String path = getImageUploadPath( bean );
					img.setPath( path + "/" + item.getName() );
					img.setSize( item.getSize() );

					logger.debug( "addImage: Getting saved image [" + img.getPath()
									+ "]" );

					bean.getImages().put( img.getPath(), img );
				}

				CRUDGalleryServlet.doSaveImageUpload( bean, request );
			}
			catch ( CreateException e ) {
				e.printStackTrace();
			}
			catch ( DeleteException e ) {
				e.printStackTrace();
			}
			catch ( Exception e ) {
				e.printStackTrace();
			}
		}

		logger.debug( "UplaodImageGallery.addImage end..." );
	}

	@Override
	protected String getServletPath() {
		return "upload_image_gallery";
	}

	@Override
	protected String getUUID( HttpSession session ) {
		return (String) session.getAttribute( GalleryBean.UNIQUD_ID );
	}

}
