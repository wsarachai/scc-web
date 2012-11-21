package org.ktm.domain.party;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import org.ktm.domain.KTMEntity;

@Entity
public class Preperence implements KTMEntity {

	private static final long	serialVersionUID	= 1L;

	private Integer				uniqueId;
	private Integer				version;
	private String				preperenceWeight;
	private PreperenceOption	option;

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

	@Column( name = "perperenceWeight" )
	public String getPreperenceWeight() {
		return preperenceWeight;
	}

	public void setPreperenceWeight( String preperenceWeight ) {
		this.preperenceWeight = preperenceWeight;
	}

	@Transient
	public String getOptionName() {
		return null;
	}

	@Transient
	public String getOptionDescription() {
		return null;
	}

	@Transient
	public PreperenceType getType() {
		return null;
	}

	@ManyToOne
	public PreperenceOption getOption() {
		return option;
	}

	public void setOption( PreperenceOption option ) {
		this.option = option;
	}

}
