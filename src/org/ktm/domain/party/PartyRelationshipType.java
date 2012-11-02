package org.ktm.domain.party;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

/*
 * The PartyRelationshipType provides a way to store all of the common
 * information for a set of PartyRelationship instances.
 */
@Entity
public class PartyRelationshipType implements Serializable {

    private static final long                serialVersionUID  = 1L;

    private Integer                          uniqueId;
    private Integer                          version;
    private String                           name;
    private RoleSet                          requirementsForRelationship;
    private Set<PartyRelationshipConstraint> validTypesOfParty = new HashSet<PartyRelationshipConstraint>(0);

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

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public RoleSet getRequirementsForRelationship() {
        return requirementsForRelationship;
    }

    public void setRequirementsForRelationship(RoleSet requirementsForRelationship) {
        this.requirementsForRelationship = requirementsForRelationship;
    }

    @OneToMany(mappedBy = "partyRelationshipType")
    public Set<PartyRelationshipConstraint> getValidTypesOfParty() {
        return validTypesOfParty;
    }

    public void setValidTypesOfParty(Set<PartyRelationshipConstraint> validTypesOfParty) {
        this.validTypesOfParty = validTypesOfParty;
    }

}
