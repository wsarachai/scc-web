package org.ktm.domain.party;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import org.ktm.domain.KTMEntity;

/*
 * The Address represents information that can used to contact a Party.
 */
@Entity
public class Address implements KTMEntity {

	private static final long	serialVersionUID	= 1L;

	private Integer				uniqueId;
	private Integer				version;

	@Id
	@Override
	@GeneratedValue
	@Column( name = "uniqueId", nullable = false )
	public Integer getUniqueId() {
		return uniqueId;
	}

	@Override
	public void setUniqueId( Integer uniqueId ) {
		this.uniqueId = uniqueId;
	}

	@Version
	@Override
	@Column( name = "version" )
	public Integer getVersion() {
		return version;
	}

	@Override
	public void setVersion( Integer version ) {
		this.version = version;
	}

}
