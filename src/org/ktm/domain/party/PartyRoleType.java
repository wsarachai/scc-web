package org.ktm.domain.party;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import org.ktm.domain.KTMEntity;

/*
 * The PartyRoleType provides a way to store all of the common information for a
 * set of PartyRole instance
 */
@Entity
@Table( uniqueConstraints = { @UniqueConstraint( columnNames = { "uniqueId",
																"name" } ) } )
public class PartyRoleType implements KTMEntity {

	private static final long			serialVersionUID	= 1L;

	private Integer						uniqueId;
	private Integer						version;
	private String						name;
	private String						description;
	private RoleSet						requirementsForRole;
	private Set<PartyRoleConstraint>	validTypesOfParty	= new HashSet<PartyRoleConstraint>( 0 );

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

	@Column( name = "name" )
	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription( String description ) {
		this.description = description;
	}

	@OneToOne( cascade = CascadeType.ALL )
	public RoleSet getRequirementsForRole() {
		return requirementsForRole;
	}

	public void setRequirementsForRole( RoleSet requirementsForRole ) {
		this.requirementsForRole = requirementsForRole;
	}

	@OneToMany
	public Set<PartyRoleConstraint> getValidTypesOfParty() {
		return validTypesOfParty;
	}

	public	void
			setValidTypesOfParty( Set<PartyRoleConstraint> validTypesOfParty ) {
		this.validTypesOfParty = validTypesOfParty;
	}

	@Transient
	public boolean canPlayRole( Party party ) {
		return true;
	}

	@Transient
	public PartyRoleConstraint [] getContraints() {
		return null;
	}

}
