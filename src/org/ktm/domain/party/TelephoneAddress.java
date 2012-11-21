package org.ktm.domain.party;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

/*
 * The TelephoneAddress represents a number that can contact a telephone, mobile
 * phone, fax, pager, or other telephoneic device.
 */
@Entity
public class TelephoneAddress extends Address {

	private static final long	serialVersionUID	= 1L;

	private String				countryCode;
	private String				areaCode;
	private String				number;
	private String				extension;
	// Values for physicalType are: phone,fax,mobile,pager
	private String				physicalType;

	@Column( name = "countryCode" )
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode( String countryCode ) {
		this.countryCode = countryCode;
	}

	@Column( name = "areaCode" )
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode( String areaCode ) {
		this.areaCode = areaCode;
	}

	@Column( name = "number" )
	public String getNumber() {
		return number;
	}

	public void setNumber( String number ) {
		this.number = number;
	}

	@Column( name = "extension" )
	public String getExtension() {
		return extension;
	}

	public void setExtension( String extension ) {
		this.extension = extension;
	}

	@Column( name = "physicalType" )
	public String getPhysicalType() {
		return physicalType;
	}

	public void setPhysicalType( String physicalType ) {
		this.physicalType = physicalType;
	}

	@Transient
	public String getNumberText() {
		return "(+" + countryCode
				+ ") "
				+ areaCode
				+ "-"
				+ number
				+ "ext-"
				+ extension;
	}

}
