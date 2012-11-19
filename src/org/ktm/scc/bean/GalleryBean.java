package org.ktm.scc.bean;

import java.text.ParseException;
import java.util.Collection;
import java.util.Set;
import org.apache.log4j.Logger;
import org.ktm.domain.KTMEntity;
import org.ktm.domain.gallery.Gallery;
import org.ktm.domain.gallery.Image;
import org.ktm.exception.KTMException;
import org.ktm.utils.DateUtils;
import org.ktm.web.tags.Functions;

public class GalleryBean extends UploadImageBean {

	public static final String	UNIQUD_ID	= "GALLERY_UNIQUD_ID";

	private static Logger		logger		= Logger.getLogger( GalleryBean.class );

	private String				description;
	private String				coverStatus;

	public GalleryBean() {

	}

	@Override
	public void loadFormCollection( Collection<?> entitys ) throws KTMException {

		getFormCollection().clear();

		if ( entitys != null && entitys.size() > 0 ) {

			for ( Object entity : entitys ) {
				if ( entity instanceof Gallery ) {
					GalleryBean bean = new GalleryBean();
					bean.loadToForm( (Gallery) entity );
					getFormCollection().add( bean );
				}
			}
		}
	}

	@Override
	public void loadToForm( KTMEntity entity ) {
		if ( entity != null && entity instanceof Gallery ) {
			super.loadToForm( entity );

			Gallery gallery = (Gallery) entity;
			this.setIdentifier( gallery.getIdentifier() );
			this.setTitle( gallery.getTitle() );
			this.setDescription( gallery.getDescription() );
			try {
				this.setDateCreated( DateUtils.formatDate( gallery.getDateCreated() ) );
				this.setDateModified( DateUtils.formatDate( gallery.getDateModified() ) );
			}
			catch ( ParseException e ) {
				e.printStackTrace();
			}

			this.getAuthor().loadToForm( gallery.getAuthor() );

			this.getImages().clear();

			try {
				Set<Image> imgs = gallery.getImages();
				if ( imgs != null && imgs.size() > 0 ) {
					Image coverImage = null;
					for ( Image img : imgs ) {
						ImageBean imgBean = new ImageBean();
						imgBean.loadToForm( img );
						this.getImages().put( imgBean.getPath(), imgBean );

						logger.debug( "GalleryBean [loadToForm] adding image: " + imgBean.getPath() );

						if ( coverImage == null ) {
							coverImage = img;
						}
					}
					// set cover image
					if ( coverImage != null ) {
						this.setCoverImage( "GetImage?getfile&uuid=" + coverImage.getUniqueId() );
					}
					// set cover status
					this.setCoverStatus( ""	+ this.getImages().size()
											+ " "
											+ Functions.getText( "page.image" ) );

				}
			}
			catch ( Exception ex ) {
				logger.error( "Null image !!" );
			}
		}
	}

	@Override
	public void syncToEntity( KTMEntity entity ) {
		if ( entity != null ) {
			super.syncToEntity( entity );

			Gallery gallery = (Gallery) entity;
			gallery.setIdentifier( this.getIdentifier() );
			if ( Functions.isEmpty( this.getTitle() ) ) {
				this.setTitle( "untitle gallery" );
			}
			gallery.setTitle( this.getTitle() );
			gallery.setDescription( this.getDescription() );
			try {
				gallery.setDateCreated( DateUtils.formatString( this.getDateCreated() ) );
				gallery.setDateModified( DateUtils.formatString( this.getDateModified() ) );
			}
			catch ( ParseException e ) {
				e.printStackTrace();
			}
			gallery.setPublishOnMain( this.getPublishOnMain() );
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription( String description ) {
		this.description = description;
	}

	public String getCoverStatus() {
		return coverStatus;
	}

	public void setCoverStatus( String coverStatus ) {
		this.coverStatus = coverStatus;
	}

}
