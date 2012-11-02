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
import javax.persistence.Version;
import org.ktm.domain.KTMEntity;

/*
 * The Party represents an identifiable, addressable unit that may have a legal
 * status and that normally has autonomous control over (at least some of) its
 * actions.
 */
@Entity
public class Party implements KTMEntity {

    private static final long         serialVersionUID      = 1L;

    private Integer                   uniqueId;
    private Integer                   version;
    private PartyIdentifier           identifier;
    private Set<RegisteredIdentifier> registeredIdentifiers = new HashSet<RegisteredIdentifier>(0);
    private Set<AddressProperties>    addresses             = new HashSet<AddressProperties>(0);
    private Set<PartyRole>            roles                 = new HashSet<PartyRole>(0);

    // private Set<Preperence> preperences = new HashSet<Preperence>(0);

    @Override
    @Id
    @GeneratedValue
    @Column(name = "uniqueId", nullable = false)
    public Integer getUniqueId() {
        return uniqueId;
    }

    @Override
    public void setUniqueId(Integer uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Override
    @Version
    @Column(name = "version")
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }

    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL)
    public Set<RegisteredIdentifier> getRegisteredIdentifiers() {
        return registeredIdentifiers;
    }

    public void setRegisteredIdentifiers(Set<RegisteredIdentifier> registeredIdentifiers) {
        this.registeredIdentifiers = registeredIdentifiers;
    }

    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL)
    public Set<AddressProperties> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<AddressProperties> addresses) {
        this.addresses = addresses;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public PartyIdentifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(PartyIdentifier identifier) {
        this.identifier = identifier;
    }

    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL)
    public Set<PartyRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<PartyRole> roles) {
        this.roles = roles;
    }

}
