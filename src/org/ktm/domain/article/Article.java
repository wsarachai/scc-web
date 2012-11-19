package org.ktm.domain.article;

import java.util.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import org.ktm.domain.gallery.Image;
import org.ktm.domain.party.Division;
import org.ktm.domain.upload.ImageUpload;

@Entity
public class Article extends ImageUpload {

	private static final long	serialVersionUID	= 1L;

	private Integer				uniqueId;
	private String				identifier;
	private Integer				version;
	private String				title;
	private String				content;
	private Date				dateCreated;
	private Date				dateModified;
	private String				publishOnMain;
	private ArticleType			type;

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
	public void setUniqueId( Integer uniqueId ) {
		this.uniqueId = uniqueId;
	}

	@Override
	public void setVersion( Integer version ) {
		this.version = version;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier( String identifier ) {
		this.identifier = identifier;
	}

	@ManyToOne
	public Division getAuthor() {
		return super.getAuthor();
	}

	public void setAuthor( Division author ) {
		super.setAuthor( author );
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated( Date dateCreated ) {
		this.dateCreated = dateCreated;
	}

	@Lob
	public String getContent() {
		return content;
	}

	public void setContent( String content ) {
		this.content = content;
	}

	@OneToMany
	public Set<Image> getImages() {
		return super.getImages();
	}

	public void setImages( Set<Image> images ) {
		super.setImages( images );
	}

	@ManyToOne
	public ArticleType getType() {
		return type;
	}

	public void setType( ArticleType type ) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle( String title ) {
		this.title = title;
	}

	public String getPublishOnMain() {
		return publishOnMain;
	}

	public void setPublishOnMain( String publishOnMain ) {
		this.publishOnMain = publishOnMain;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified( Date dateModified ) {
		this.dateModified = dateModified;
	}

}
