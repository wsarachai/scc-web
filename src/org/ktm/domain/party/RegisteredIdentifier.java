package org.ktm.domain.party;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import org.ktm.domain.UniqueIdentifier;

/*
 * The RegisteredIdentifier represents an identifier for a Party that has been
 * assigned by a recognized or statutory body.
 */
@Entity
public class RegisteredIdentifier extends UniqueIdentifier {

	private static final long	serialVersionUID	= 1L;

	private Integer				uniqueId;
	private Integer				version;
	private String				identifier;
	private String				type;
	private Date				validFrom;
	private Date				validTo;
	private String				registationAuthority;
	private Party				party;

	@Id
	@GeneratedValue
	@Column( name = "uniqueId", nullable = false )
	public Integer getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId( Integer uniqueId ) {
		this.uniqueId = uniqueId;
	}

	@Version
	@Column( name = "version" )
	public Integer getVersion() {
		return version;
	}

	public void setVersion( Integer version ) {
		this.version = version;
	}

	@Column( name = "identifier" )
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier( String identifier ) {
		this.identifier = identifier;
	}

	@Column( name = "type" )
	public String getType() {
		return type;
	}

	public void setType( String type ) {
		this.type = type;
	}

	@Column( name = "validFrom" )
	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom( Date validFrom ) {
		this.validFrom = validFrom;
	}

	@Column( name = "validTo" )
	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo( Date validTo ) {
		this.validTo = validTo;
	}

	@Column( name = "registationAuthority" )
	public String getRegistationAuthority() {
		return registationAuthority;
	}

	public void setRegistationAuthority( String registationAuthority ) {
		this.registationAuthority = registationAuthority;
	}

	@ManyToOne
	public Party getParty() {
		return party;
	}

	public void setParty( Party party ) {
		this.party = party;
	}

}
