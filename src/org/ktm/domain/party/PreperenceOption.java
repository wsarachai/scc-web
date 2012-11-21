package org.ktm.domain.party;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.ktm.domain.KTMEntity;

@Entity
public class PreperenceOption implements KTMEntity {

	private static final long	serialVersionUID	= 1L;

	private Integer				uniqueId;
	private Integer				version;
	private String				name;
	private String				description;

	@Id
	@GeneratedValue
	@Column( name = "uniqueId", nullable = false )
	public Integer getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId( Integer uniqueId ) {
		this.uniqueId = uniqueId;
	}

	@Column( name = "version" )
	public Integer getVersion() {
		return version;
	}

	public void setVersion( Integer version ) {
		this.version = version;
	}

	@Column( name = "name" )
	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	@Column( name = "description" )
	public String getDescription() {
		return description;
	}

	public void setDescription( String description ) {
		this.description = description;
	}
}
