package org.ktm.scc;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.ktm.core.KTMContext;
import org.ktm.dao.KTMEMDaoFactory;
import org.ktm.dao.article.ArticleDao;
import org.ktm.dao.article.ArticleTypeDao;
import org.ktm.dao.gallery.ImageDao;
import org.ktm.domain.KTMEntity;
import org.ktm.domain.article.Article;
import org.ktm.domain.article.ArticleType;
import org.ktm.domain.gallery.Image;
import org.ktm.exception.CreateException;
import org.ktm.exception.DeleteException;
import org.ktm.exception.KTMException;
import org.ktm.scc.bean.ArticleBean;
import org.ktm.scc.bean.ArticleTypeBean;
import org.ktm.scc.bean.ImageBean;
import org.ktm.servlet.ActionForward;
import org.ktm.servlet.CRUDServlet;
import org.ktm.utils.DateUtils;
import org.ktm.utils.Globals;
import org.ktm.web.bean.FormBean;
import org.ktm.web.tags.Functions;

@WebServlet( "/RGB7-backoffice-v4/CRUDArticle" )
public class CRUDArticleServlet extends CRUDServlet {

	private static final long	serialVersionUID	= 1L;

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
		ArticleBean bean = (ArticleBean) form;

		ArticleDao articleDao = KTMEMDaoFactory.getInstance().getArticleDao();
		Collection<?> articles = articleDao.find( bean.getPageNumber() + 1 );

		bean.setMaxPage( KTMContext.paging );
		bean.setMaxRows( (int) articleDao.getCount() );

		if ( articles != null && articles.size() > 0 ) {
			bean.loadFormCollection( articles );
		}
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
				bean.setDateCreated( DateUtils.formatDate( new Date() ) );
			}
			catch ( ParseException e ) {
				e.printStackTrace();
			}

			return ActionForward.getUri( this, request, "EditArticles.jsp" );
		}

		return ActionForward.getAction( this,
				request,
				"CRUDArticle?method=list",
				true );
	}

	public	ActionForward
			newArticle( FormBean form,
						HttpServletRequest request,
						HttpServletResponse response )	throws ServletException,
														IOException {
		HttpSession session = request.getSession();
		ArticleBean bean = (ArticleBean) form;

		bean.getImages().clear();

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

		// New Article
		try {
			String uuid = Globals.generateUniqueId();
			session.setAttribute( ArticleBean.UNIQUD_ID, uuid );
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

		return ActionForward.getUri( this, request, "EditArticles.jsp" );
	}

	public static synchronized void
			doSaveArticle( ArticleBean bean, HttpServletRequest request )	throws CreateException,
																			DeleteException {
		ImageDao imageDao = KTMEMDaoFactory.getInstance().getImageDao();
		ArticleDao articleDao = KTMEMDaoFactory.getInstance().getArticleDao();

		if ( articleDao != null ) {
			Article article = null;
			if ( !Functions.isEmpty( bean.getUniqueId() ) ) {
				Integer aid = Integer.valueOf( bean.getUniqueId() );
				article = (Article) articleDao.get( aid );
			}
			if ( article == null ) {
				article = articleDao.findByIdentifier( bean.getIdentifier() );
				if ( article == null ) {
					article = new Article();
				}
			}
			bean.syncToEntity( article );

			List<Image> imgs = article.getImages();
			Map<String,Image> imageKey = new HashMap<String,Image>();
			for ( Image img : imgs ) {
				if ( !bean.getImages().containsKey( img.getPath() ) ) {
					// delete
					imageDao.getCrudAdmin().addDeleted( img );
				} else {
					// keep remain to map
					imageKey.put( img.getPath(), img );
				}
			}
			Iterator<String> it = bean.getImages().keySet().iterator();
			while ( it.hasNext() ) {
				String key = it.next();
				if ( !imageKey.containsKey( key ) ) {
					// add new
					ImageBean imageBean = bean.getImages().get( key );
					Image newImage = new Image();
					imageBean.syncToEntity( newImage );
					imageDao.getCrudAdmin().addCreated( newImage );
				}
			}

			for ( Object obj : imageDao.getCrudAdmin().getDeletedCollection() ) {
				if ( obj instanceof Image ) {
					article.getImages().remove( obj );
				}
			}
			for ( Object obj : imageDao.getCrudAdmin().getCreatedCollection() ) {
				if ( obj instanceof Image ) {
					imageDao.createOrUpdate( (KTMEntity) obj );
					article.getImages().add( (Image) obj );
				}
			}
			articleDao.createOrUpdate( article );

			for ( Object obj : imageDao.getCrudAdmin().getDeletedCollection() ) {
				if ( obj instanceof Image ) {
					imageDao.delete( ( (Image) obj ).getUniqueId() );
				}
			}
		}
	}

	public	ActionForward
			saveArticle(	FormBean form,
							HttpServletRequest request,
							HttpServletResponse response )	throws ServletException,
															IOException {
		String result = "fail";
		PrintWriter out = response.getWriter();
		ArticleBean bean = (ArticleBean) form;

		try {
			doSaveArticle( bean, request );
			result = "success";
		}
		catch ( CreateException e ) {
			e.printStackTrace();
		}
		catch ( DeleteException e ) {
			e.printStackTrace();
		}

		out.print( result );
		return null;
	}

	public	ActionForward
			delArticle( FormBean form,
						HttpServletRequest request,
						HttpServletResponse response )	throws ServletException,
														IOException {
		ArticleBean bean = (ArticleBean) form;
		ImageDao imageDao = KTMEMDaoFactory.getInstance().getImageDao();
		ArticleDao articleDao = KTMEMDaoFactory.getInstance().getArticleDao();
		Article article = (Article) articleDao.get( Integer.valueOf( bean.getUniqueId() ) );
		if ( article != null ) {
			for ( Image img : article.getImages() ) {
				imageDao.getCrudAdmin().addDeleted( img );
			}
			article.getImages().clear();
			try {
				articleDao.delete( article );

				for ( Object obj : imageDao.getCrudAdmin()
						.getDeletedCollection() ) {
					imageDao.delete( ( (Image) obj ).getUniqueId() );
				}
			}
			catch ( DeleteException e ) {
				e.printStackTrace();
			}
		}
		return ActionForward.getAction( this,
				request,
				"CRUDArticle?method=list&module=article&pageNumber=0",
				true );
	}

}
