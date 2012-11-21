package org.ktm.domain.party;

import javax.persistence.Entity;

/*
 * Organization represents an administrative and functional structure.
 */
@Entity
public class Organization extends Party {

	private static final long	serialVersionUID	= 1L;

	private String				thaiName;
	private String				englishName;
	private String				description;

	public String getThaiName() {
		return thaiName;
	}

	public void setThaiName( String thaiName ) {
		this.thaiName = thaiName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName( String englishName ) {
		this.englishName = englishName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription( String description ) {
		this.description = description;
	}
}
