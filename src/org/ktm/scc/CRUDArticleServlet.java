package org.ktm.scc;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ktm.dao.KTMEMDaoFactory;
import org.ktm.dao.article.ArticleDao;
import org.ktm.dao.article.ArticleTypeDao;
import org.ktm.domain.KTMEntity;
import org.ktm.domain.article.ArticleType;
import org.ktm.exception.KTMException;
import org.ktm.scc.bean.ArticleBean;
import org.ktm.scc.bean.ArticleTypeBean;
import org.ktm.servlet.ActionForward;
import org.ktm.servlet.CRUDServlet;
import org.ktm.web.bean.FormBean;

@WebServlet("/CRUDArticle")
public class CRUDArticleServlet extends CRUDServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public String getBeanClass() {
        return "org.ktm.scc.bean.ArticleBean";
    }

    public ActionForward listArticle(FormBean form, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, KTMException {
        ArticleBean bean = (ArticleBean) form;
        ArticleDao articleDao = KTMEMDaoFactory.getInstance().getArticleDao();
        Collection<KTMEntity> articles = (Collection<KTMEntity>) articleDao.findAll();
        if (articles != null && articles.size() > 0) {
            bean.loadFormCollection(articles);
        }
        return ActionForward.getUri(this, request, "article/ListArticle.jsp");
    }

    public ActionForward editArticle(FormBean form, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArticleBean bean = (ArticleBean) form;
        ArticleTypeDao articleTypeDao = KTMEMDaoFactory.getInstance().getArticleTypeDao();

        Collection<KTMEntity> articleTypes = articleTypeDao.findAll();
        for (KTMEntity entity : articleTypes) {
            bean.getArticleTypeCollection().clear();

            if (entity instanceof ArticleType) {
                ArticleType type = (ArticleType) entity;
                ArticleTypeBean typeBean = new ArticleTypeBean();
                typeBean.loadToForm(type);
                bean.getArticleTypeCollection().add(typeBean);
            }
        }

        return ActionForward.getUri(this, request, "article/EditArticle.jsp");
    }

    public ActionForward newArticle(FormBean form, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArticleBean bean = (ArticleBean) form;
        ArticleTypeDao articleTypeDao = KTMEMDaoFactory.getInstance().getArticleTypeDao();
        bean.getArticleTypeCollection().clear();
        Collection<KTMEntity> articleTypes = articleTypeDao.findAll();
        for (KTMEntity entity : articleTypes) {
            if (entity instanceof ArticleType) {
                ArticleType type = (ArticleType) entity;
                ArticleTypeBean typeBean = new ArticleTypeBean();
                typeBean.loadToForm(type);
                bean.getArticleTypeCollection().add(typeBean);
            }
        }

        return ActionForward.getUri(this, request, "article/EditArticle.jsp");
    }

    public ActionForward saveArticle(FormBean form, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return ActionForward.getAction(this, request, "CRUDArticle?method=list", true);
    }

    public ActionForward delArticle(FormBean form, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return ActionForward.getAction(this, request, "CRUDArticle?method=list", true);
    }

    @Override
    protected boolean prepareRequest(HttpServletRequest request) throws ServletException, IOException {
        super.prepareRequest(request);
        return true;
    }

}
