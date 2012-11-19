package org.ktm.scc.bean;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.ktm.dao.KTMEMDaoFactory;
import org.ktm.dao.article.ArticleTypeDao;
import org.ktm.domain.KTMEntity;
import org.ktm.domain.article.Article;
import org.ktm.domain.article.ArticleType;
import org.ktm.domain.gallery.Image;
import org.ktm.exception.KTMException;
import org.ktm.utils.DateUtils;
import org.ktm.web.bean.FormBean;
import org.ktm.web.tags.Functions;

public class ArticleBean extends UploadImageBean {

	public static final String		UNIQUD_ID				= "ARTICLE_UNIQUD_ID";

	private static Logger			logger					= Logger.getLogger( ArticleBean.class );

	private Integer					articleTypeId;
	private String					content;

	private List<ArticleTypeBean>	articleTypeCollection	= new ArrayList<ArticleTypeBean>();

	@Override
	public void loadFormCollection( Collection<?> entitys ) throws KTMException {

		getFormCollection().clear();

		if ( entitys != null && entitys.size() > 0 ) {
			for ( Object entity : entitys ) {
				if ( entity instanceof KTMEntity ) {
					ArticleBean bean = new ArticleBean();
					bean.loadToForm( (KTMEntity) entity );
					getFormCollection().add( bean );
				}
			}
		}
	}

	@Override
	public void loadToForm( KTMEntity entity ) {
		if ( entity != null && entity instanceof Article ) {
			super.loadToForm( entity );

			Article article = (Article) entity;
			this.setIdentifier( article.getIdentifier() );
			this.setTitle( article.getTitle() );
			try {
				this.setDateCreated( DateUtils.formatDate( article.getDateCreated() ) );
				this.setDateModified( DateUtils.formatDate( article.getDateModified() ) );
			}
			catch ( ParseException e ) {
				e.printStackTrace();
			}
			this.setContent( article.getContent() );
			this.setPublishOnMain( article.getPublishOnMain() );

			//this.setArticleTypeId(article.getType().getUniqueId());

			if ( getAuthor() != null ) {
				getAuthor().loadToForm( article.getAuthor() );
			}

			this.getImages().clear();

			try {
				Set<Image> imgs = article.getImages();
				if ( imgs != null && imgs.size() > 0 ) {
					Image coverImage = null;
					for ( Image img : imgs ) {
						ImageBean imgBean = new ImageBean();
						imgBean.loadToForm( img );
						this.getImages().put( imgBean.getPath(), imgBean );

						logger.debug( "ArticleBean [loadToForm] adding image: " + imgBean.getPath() );

						if ( coverImage == null ) {
							coverImage = img;
						}
					}

					if ( coverImage != null ) {
						this.setCoverImage( "GetImage?getfile&uuid=" + coverImage.getUniqueId() );
					}
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

			Article article = (Article) entity;
			article.setIdentifier( this.getIdentifier() );

			if ( Functions.isEmpty( this.getTitle() ) ) {
				this.setTitle( "no title" );
			}
			article.setTitle( this.getTitle() );
			if ( Functions.isEmpty( this.getContent() ) ) {
				this.setContent( "no content" );
			}
			article.setContent( this.getContent() );

			article.setPublishOnMain( this.getPublishOnMain() );

			try {
				article.setDateCreated( DateUtils.formatString( this.getDateCreated() ) );
				article.setDateModified( DateUtils.formatString( this.getDateModified() ) );
			}
			catch ( ParseException e ) {
				e.printStackTrace();
			}

			ArticleTypeDao articleTypeDao = KTMEMDaoFactory.getInstance()
					.getArticleTypeDao();
			ArticleType type = (ArticleType) articleTypeDao.get( getArticleTypeId() );
			if ( type != null ) {
				article.setType( type );
			}
		}
	}

	@Override
	public void mergeForm( FormBean form ) {
		if ( form != null ) {
			super.mergeForm( form );

			ArticleBean bean = (ArticleBean) form;

			if ( bean.getArticleTypeId() != null ) {
				this.setArticleTypeId( bean.getArticleTypeId() );
			}
			if ( !Functions.isEmpty( bean.getContent() ) ) {
				this.setContent( bean.getContent() );
			}
			if ( !Functions.isEmpty( bean.getDateCreated() ) ) {
				this.setDateCreated( bean.getDateCreated() );
			}
			if ( !Functions.isEmpty( bean.getIdentifier() ) ) {
				this.setIdentifier( bean.getIdentifier() );
			}
			if ( !Functions.isEmpty( bean.getTitle() ) ) {
				this.setTitle( bean.getTitle() );
			}
		}
	}

	public String getContent() {
		return content;
	}

	public void setContent( String content ) {
		this.content = content;
	}

	public Integer getArticleTypeId() {
		return articleTypeId;
	}

	public void setArticleTypeId( Integer articleTypeId ) {
		this.articleTypeId = articleTypeId;
	}

	public List<ArticleTypeBean> getArticleTypeCollection() {
		return articleTypeCollection;
	}

	public	void
			setArticleTypeCollection( List<ArticleTypeBean> articleTypeCollection ) {
		this.articleTypeCollection = articleTypeCollection;
	}

}
