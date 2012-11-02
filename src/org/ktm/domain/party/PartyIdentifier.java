package org.ktm.domain.party;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import org.ktm.domain.UniqueIdentifier;

/*
 * The PartyIdentifier represents a unique identifier for a Party
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "uniqueId", "identifier" }) })
public class PartyIdentifier extends UniqueIdentifier {

    private static final long serialVersionUID = 1L;

    private Integer           uniqueId;
    private Integer           version;
    private String            identifier;

    @Id
    @GeneratedValue
    @Column(name = "uniqueId", nullable = false)
    public Integer getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Integer uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Version
    @Column(name = "version")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    @Column(name = "identifier")
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

}
