package org.ktm.domain.party;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.persistence.Version;

/*
 * The PartyRelationshipContraint specifics the names of PartyRoles that may
 * adopt the client and supplier sides of a PartyRelationship of a specific
 * PartyRelationshipType.
 */
@Entity
public class PartyRelationshipConstraint implements Serializable {

    private static final long     serialVersionUID = 1L;

    private Integer               uniqueId;
    private Integer               version;
    private String                clientRoleName;
    private String                supplierRoleName;
    private PartyRelationshipType partyRelationshipType;

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

    @Column(name = "clientRole")
    public String getClientRoleName() {
        return clientRoleName;
    }

    public void setClientRoleName(String clientRoleName) {
        this.clientRoleName = clientRoleName;
    }

    @Column(name = "supplierRole")
    public String getSupplierRoleName() {
        return supplierRoleName;
    }

    public void setSupplierRoleName(String supplierRoleName) {
        this.supplierRoleName = supplierRoleName;
    }

    @ManyToOne
    public PartyRelationshipType getPartyRelationshipType() {
        return partyRelationshipType;
    }

    public void setPartyRelationshipType(PartyRelationshipType partyRelationshipType) {
        this.partyRelationshipType = partyRelationshipType;
    }

    @Transient
    public boolean canFromRelationship(PartyRole partyRoleA, PartyRole partyRoleB) {
        return true;
    }
}
