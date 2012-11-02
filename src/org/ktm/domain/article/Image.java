package org.ktm.domain.article;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import org.ktm.domain.KTMEntity;

@Entity
public class Image implements KTMEntity {

    private static final long serialVersionUID = 1L;

    private Integer           uniqueId;
    private Integer           version;
    private String            path;
    private Article           article;
    private Boolean           isTitle;

    public Image() {

    }

    @Id
    @GeneratedValue
    @Override
    public Integer getUniqueId() {
        return uniqueId;
    }

    @Version
    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setUniqueId(Integer uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @ManyToOne
    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Boolean getIsTitle() {
        return isTitle;
    }

    public void setIsTitle(Boolean isTitle) {
        this.isTitle = isTitle;
    }

}
