package org.ktm.scc;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
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
import org.ktm.dao.article.ArticleDao;
import org.ktm.dao.article.ArticleTypeDao;
import org.ktm.domain.KTMEntity;
import org.ktm.domain.article.Article;
import org.ktm.domain.article.ArticleType;
import org.ktm.domain.upload.ImageUpload;
import org.ktm.exception.CreateException;
import org.ktm.exception.DeleteException;
import org.ktm.exception.KTMException;
import org.ktm.scc.bean.ArticleBean;
import org.ktm.scc.bean.ArticleTypeBean;
import org.ktm.scc.bean.UploadImageBean;
import org.ktm.servlet.ActionForward;
import org.ktm.utils.DateUtils;
import org.ktm.web.bean.FormBean;

@WebServlet( "/RGB7-backoffice-v4/CRUDArticle" )
public class CRUDArticleServlet extends CRUDAbstractImgUpload {

	private static final long	serialVersionUID	= 1L;
	private static Logger		logger				= Logger.getLogger( CRUDGalleryServlet.class );

	@Override
	public String getBeanClass() {
		return "org.ktm.scc.bean.ArticleBean";
	}

	public	ActionForward
			listArticle(	FormBean form,
							HttpServletRequest request,
							HttpServletResponse response )	throws ServletException,
															IOException,
															KTMException {

		logger.debug( ">>> listGallery: begin" );

		listData( form );

		logger.debug( ">>> listGallery: begin" );

		return ActionForward.getUri( this, request, "ListArticles.jsp" );
	}

	public	ActionForward
			editArticle(	FormBean form,
							HttpServletRequest request,
							HttpServletResponse response )	throws ServletException,
															IOException {
		HttpSession session = request.getSession();
		ArticleBean bean = (ArticleBean) form;
		ArticleTypeDao articleTypeDao = KTMEMDaoFactory.getInstance()
				.getArticleTypeDao();

		Collection<KTMEntity> articleTypes = articleTypeDao.findAll();
		bean.getArticleTypeCollection().clear();
		for ( KTMEntity entity : articleTypes ) {
			if ( entity instanceof ArticleType ) {
				ArticleType type = (ArticleType) entity;
				ArticleTypeBean typeBean = new ArticleTypeBean();
				typeBean.loadToForm( type );
				bean.getArticleTypeCollection().add( typeBean );
			}
		}

		ArticleDao articleDao = KTMEMDaoFactory.getInstance().getArticleDao();
		Article article = (Article) articleDao.get( Integer.parseInt( bean.getUniqueId() ) );
		if ( article != null ) {
			bean.loadToForm( article );

			session.setAttribute( ArticleBean.UNIQUD_ID, bean.getIdentifier() );
			session.setAttribute( bean.getIdentifier(), bean );
			try {
				bean.setDateModified( DateUtils.formatDate( new Date() ) );
			}
			catch ( ParseException e ) {
				e.printStackTrace();
			}

			return ActionForward.getUri( this, request, "EditArticles.jsp" );
		}

		if ( bean.getServlet() != null && bean.getServlet() instanceof CRUDArticleServlet ) {
			( (CRUDArticleServlet) bean.getServlet() ).resetAllCRUDCollection( session );
		}

		return ActionForward.getAction( this,
				request,
				"index?page=CRUDArticle&t=t&param=" + KTMCrypt.encrypt( "method=list&module=article&pageNumber=" + bean.getPageNumber() ),
				true );
	}

	public	ActionForward
			newArticle( FormBean form,
						HttpServletRequest request,
						HttpServletResponse response )	throws ServletException,
														IOException {
		HttpSession session = request.getSession();
		ArticleBean bean = (ArticleBean) form;

		logger.debug( ">>> newArticle: begin" );

		// ArticleType list
		ArticleTypeDao articleTypeDao = KTMEMDaoFactory.getInstance()
				.getArticleTypeDao();
		bean.getArticleTypeCollection().clear();
		Collection<KTMEntity> articleTypes = articleTypeDao.findAll();
		for ( KTMEntity entity : articleTypes ) {
			if ( entity instanceof ArticleType ) {
				ArticleType type = (ArticleType) entity;
				ArticleTypeBean typeBean = new ArticleTypeBean();
				typeBean.loadToForm( type );
				bean.getArticleTypeCollection().add( typeBean );
			}
		}

		newImageUpload( bean, session );

		logger.debug( ">>> newArticle: end" );

		return ActionForward.getUri( this, request, "EditArticles.jsp" );
	}

	public	ActionForward
			saveArticle(	FormBean form,
							HttpServletRequest request,
							HttpServletResponse response )	throws ServletException,
															IOException {
		ArticleBean bean = (ArticleBean) form;

		try {
			doSaveImageUpload( bean, request );
		}
		catch ( CreateException e ) {
			e.printStackTrace();
		}
		catch ( DeleteException e ) {
			e.printStackTrace();
		}

		if ( bean.getServlet() != null && bean.getServlet() instanceof CRUDArticleServlet ) {
			( (CRUDArticleServlet) bean.getServlet() ).resetAllCRUDCollection( request.getSession() );
		}

		return ActionForward.getAction( this,
				request,
				"index?page=CRUDArticle&t=t&param=" + KTMCrypt.encrypt( "method=list&module=article&pageNumber=" + bean.getPageNumber() ),
				true );
	}

	public	ActionForward
			delArticle( FormBean form,
						HttpServletRequest request,
						HttpServletResponse response )	throws ServletException,
														IOException {
		logger.debug( ">>> delArticle: begin" );

		delImageUpload( (UploadImageBean) form, request.getSession() );

		logger.debug( ">>> delArticle: eng" );

		return ActionForward.getAction( this,
				request,
				"index?page=CRUDArticle&t=t&param=" + KTMCrypt.encrypt( "method=list&module=article&pageNumber=" + form.getPageNumber() ),
				true );
	}

	@Override
	protected Dao getDao() {
		return KTMEMDaoFactory.getInstance().getArticleDao();
	}

	@Override
	protected ImageUpload getNewEntity() {
		return new Article();
	}

	@Override
	protected String getUUID() {
		return ArticleBean.UNIQUD_ID;
	}

}
