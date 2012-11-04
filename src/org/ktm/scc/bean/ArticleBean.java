package org.ktm.scc.bean;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.ktm.domain.KTMEntity;
import org.ktm.domain.article.Article;
import org.ktm.exception.KTMException;
import org.ktm.utils.DateUtils;
import org.ktm.web.bean.FormBean;

public class ArticleBean extends FormBean {

    public static final String     UNIQUD_ID             = "ARTICLE_UNIQUD_ID";
    private String                 identifier;
    private String                 title;
    private String                 dateCreated;
    private Integer                articleTypeId;
    private String                 content;
    private Map<String, ImageBean> images                = new HashMap<String, ImageBean>();

    private List<ArticleTypeBean>  articleTypeCollection = new ArrayList<ArticleTypeBean>();

    @Override
    public void loadFormCollection(Collection<?> entitys) throws KTMException {
        if (entitys != null && entitys.size() > 0) {
            getFormCollection().clear();

            for (Object entity : entitys) {
                if (entity instanceof KTMEntity) {
                    ArticleBean bean = new ArticleBean();
                    bean.loadToForm((KTMEntity) entity);
                    getFormCollection().add(bean);
                }
            }
        }
    }

    @Override
    public void loadToForm(KTMEntity entity) {
        if (entity != null && entity instanceof Article) {
            super.loadToForm(entity);

            Article article = (Article) entity;
            this.setIdentifier(article.getIdentifier());
            this.setTitle(article.getTitle());
            try {
                this.setDateCreated(DateUtils.formatDate(article.getDateCreated()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            this.setContent(article.getContent());
        }
    }

    @Override
    public void syncToEntity(KTMEntity entity) {
        if (entity != null) {
            super.syncToEntity(entity);

            Article article = (Article) entity;
            article.setIdentifier(this.getIdentifier());
            article.setTitle(this.getTitle());
            try {
                article.setDateCreated(DateUtils.formatString(this.getDateCreated()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            article.setContent(this.getContent());
        }
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getArticleTypeId() {
        return articleTypeId;
    }

    public void setArticleTypeId(Integer articleTypeId) {
        this.articleTypeId = articleTypeId;
    }

    public List<ArticleTypeBean> getArticleTypeCollection() {
        return articleTypeCollection;
    }

    public void setArticleTypeCollection(List<ArticleTypeBean> articleTypeCollection) {
        this.articleTypeCollection = articleTypeCollection;
    }

    public Map<String, ImageBean> getImages() {
        return images;
    }

    public void setImages(Map<String, ImageBean> images) {
        this.images = images;
    }

}
