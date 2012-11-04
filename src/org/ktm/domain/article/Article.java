package org.ktm.domain.article;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import org.ktm.domain.KTMEntity;
import org.ktm.domain.party.Party;

@Entity
public class Article implements KTMEntity {

    private static final long serialVersionUID = 1L;

    private Integer           uniqueId;
    private String            identifier;
    private Integer           version;
    private Party             author;
    private Date              dateCreated;
    private String            title;
    private String            content;
    private ArticleType       type;
    private List<Image>       images           = new ArrayList<Image>();

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

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @ManyToOne
    public Party getAuthor() {
        return author;
    }

    public void setAuthor(Party author) {
        this.author = author;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Lob
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @OneToMany(mappedBy = "article")
    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    @ManyToOne
    public ArticleType getType() {
        return type;
    }

    public void setType(ArticleType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
