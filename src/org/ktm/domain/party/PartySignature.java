package org.ktm.domain.party;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import org.ktm.domain.KTMEntity;

/*
 * The PartySignature represents the identifying mark of a party. When specific
 * optional task.
 */
@Entity
public class PartySignature implements KTMEntity {

    private static final long serialVersionUID = 1L;

    private Integer           uniqueId;
    private Integer           version;
    private Date              when;
    private String            reason;
    private PartyIdentifier   partyIdentifier;
    private Authen            authen;

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

    @Column(name = "signedDate")
    public Date getWhen() {
        return when;
    }

    public void setWhen(Date when) {
        this.when = when;
    }

    @Column(name = "reason")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Column(name = "partyIdentifier")
    public PartyIdentifier getPartyIdentifier() {
        return partyIdentifier;
    }

    public void setPartyIdentifier(PartyIdentifier partyIdentifier) {
        this.partyIdentifier = partyIdentifier;
    }

    @OneToOne
    public Authen getAuthen() {
        return authen;
    }

    public void setAuthen(Authen authen) {
        this.authen = authen;
    }
}
