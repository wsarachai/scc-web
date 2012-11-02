package org.ktm.scc.bean;

import org.ktm.domain.KTMEntity;
import org.ktm.domain.article.ArticleType;
import org.ktm.web.bean.FormBean;

public class ArticleTypeBean extends FormBean {

    private String name;
    private String description;

    @Override
    public void loadToForm(KTMEntity entity) {
        if (entity != null && entity instanceof ArticleType) {
            super.loadToForm(entity);

            ArticleType type = (ArticleType) entity;
            this.setName(type.getName());
            this.setDescription(type.getDescription());
        }
    }

    @Override
    public void syncToEntity(KTMEntity entity) {
        if (entity != null && entity instanceof ArticleType) {
            super.syncToEntity(entity);

            ArticleType type = (ArticleType) entity;
            type.setName(this.getName());
            type.setDescription(this.getDescription());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
