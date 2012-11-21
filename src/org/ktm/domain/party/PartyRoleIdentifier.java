package org.ktm.domain.party;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.ktm.domain.UniqueIdentifier;

/*
 * The PartyRoleIdentifier represents a unique identifier for a PartyRole.
 */
@Entity
public class PartyRoleIdentifier extends UniqueIdentifier {

	private static final long	serialVersionUID	= 1L;

	private Integer				uniqueId;
	private Integer				version;
	private String				identifier;

	@Override
	@Id
	@GeneratedValue
	@Column( name = "uniqueId" )
	public Integer getUniqueId() {
		return uniqueId;
	}

	@Override
	public void setUniqueId( Integer uniqueId ) {
		this.uniqueId = uniqueId;
	}

	@Override
	@Column( name = "version" )
	public Integer getVersion() {
		return version;
	}

	@Override
	public void setVersion( Integer version ) {
		this.version = version;
	}

	@Override
	@Column( name = "identifier" )
	public String getIdentifier() {
		return identifier;
	}

	@Override
	public void setIdentifier( String identifier ) {
		this.identifier = identifier;
	}

}
