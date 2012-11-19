package org.ktm.domain.upload;

import java.util.HashSet;
import java.util.Set;
import org.ktm.domain.KTMEntity;
import org.ktm.domain.gallery.Image;
import org.ktm.domain.party.Division;

public abstract class ImageUpload implements KTMEntity {

	private static final long	serialVersionUID	= 1L;

	private Division			author;
	private Set<Image>			images				= new HashSet<Image>();

	public Set<Image> getImages() {
		return images;
	}

	public void setImages( Set<Image> images ) {
		this.images = images;
	}

	public Division getAuthor() {
		return author;
	}

	public void setAuthor( Division author ) {
		this.author = author;
	}

}
