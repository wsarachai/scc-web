package org.ktm.domain.party;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.ktm.domain.KTMEntity;

@Entity
public class PreperenceType implements KTMEntity {

	private static final long		serialVersionUID	= 1L;

	private Integer					uniqueId;
	private Integer					version;
	private Set<PreperenceOption>	options				= new HashSet<PreperenceOption>( 0 );

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

	@OneToMany
	public Set<PreperenceOption> getOptions() {
		return options;
	}

	public void setOptions( Set<PreperenceOption> options ) {
		this.options = options;
	}

}
