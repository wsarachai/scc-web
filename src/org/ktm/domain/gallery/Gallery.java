package org.ktm.domain.gallery;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.ktm.domain.KTMEntity;
import org.ktm.domain.party.Division;

@Entity
public class Gallery implements KTMEntity {

    private static final long serialVersionUID = 1L;

    private Integer           uniqueId;
    private Integer           version;
    private String            description;
    private Division          auther;
    private List<Image>       images;

    @Id
    @GeneratedValue
    @Override
    public Integer getUniqueId() {
        return uniqueId;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany
    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Division getAuther() {
        return auther;
    }

    public void setAuther(Division auther) {
        this.auther = auther;
    }

}
