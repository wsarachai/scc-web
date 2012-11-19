package org.ktm.scc.bean;

import java.util.HashMap;
import java.util.Map;
import org.ktm.web.bean.FormBean;

public class UploadImageBean extends FormBean {

	private String					identifier;
	private String					title;
	private String					dateCreated;
	private String					dateModified;
	private String					publishOnMain;
	private String					coverImage;
	private DivisionBean			author	= new DivisionBean();
	private Map<String,ImageBean>	images	= new HashMap<String,ImageBean>();

	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier( String identifier ) {
		this.identifier = identifier;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle( String title ) {
		this.title = title;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated( String dateCreated ) {
		this.dateCreated = dateCreated;
	}
	public String getDateModified() {
		return dateModified;
	}
	public void setDateModified( String dateModified ) {
		this.dateModified = dateModified;
	}
	public String getPublishOnMain() {
		return publishOnMain;
	}
	public void setPublishOnMain( String publishOnMain ) {
		this.publishOnMain = publishOnMain;
	}
	public String getCoverImage() {
		return coverImage;
	}
	public void setCoverImage( String coverImage ) {
		this.coverImage = coverImage;
	}
	public DivisionBean getAuthor() {
		return author;
	}
	public void setAuthor( DivisionBean author ) {
		this.author = author;
	}
	public Map<String,ImageBean> getImages() {
		return images;
	}
	public void setImages( Map<String,ImageBean> images ) {
		this.images = images;
	}

}
