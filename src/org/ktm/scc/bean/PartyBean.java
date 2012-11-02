package org.ktm.scc.bean;

import java.util.Set;
import org.ktm.domain.KTMEntity;
import org.ktm.domain.party.AddressProperties;
import org.ktm.domain.party.EAddressType;
import org.ktm.domain.party.EmailAddress;
import org.ktm.domain.party.GeographicAddress;
import org.ktm.domain.party.Party;
import org.ktm.domain.party.PartyIdentifier;
import org.ktm.domain.party.TelephoneAddress;
import org.ktm.web.bean.FormBean;

public class PartyBean extends FormBean {

    private String identifier;
    private String address;
    private String tel;
    private String fax;
    private String email;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void loadToForm(KTMEntity entity) {
        if (entity != null && entity instanceof Party) {
            Party party = (Party) entity;
            super.loadToForm(entity);
            this.setIdentifier(party.getIdentifier().getIdentifier());

            Set<AddressProperties> addrps = party.getAddresses();
            if (addrps != null) {
                for (AddressProperties addrp : addrps) {
                    if (addrp.getUseage().equals(EAddressType.GEOGRAPHICS.toString())) {
                        if (addrp.getAddress() != null && addrp.getAddress() instanceof GeographicAddress) {
                            GeographicAddress adds = (GeographicAddress) addrp.getAddress();
                            setAddress(adds.getAddressLine1());
                        }
                    } else if (addrp.getUseage().equals(EAddressType.TELEPHONE.toString())) {
                        if (addrp.getAddress() != null && addrp.getAddress() instanceof TelephoneAddress) {
                            TelephoneAddress phone = (TelephoneAddress) addrp.getAddress();
                            setTel(phone.getNumber());
                        }
                    } else if (addrp.getUseage().equals(EAddressType.FAX.toString())) {
                        if (addrp.getAddress() != null && addrp.getAddress() instanceof TelephoneAddress) {
                            TelephoneAddress phone = (TelephoneAddress) addrp.getAddress();
                            setFax(phone.getNumber());
                        }
                    } else if (addrp.getUseage().equals(EAddressType.EMAIL.toString())) {
                        if (addrp.getAddress() != null && addrp.getAddress() instanceof EmailAddress) {
                            EmailAddress email = (EmailAddress) addrp.getAddress();
                            setEmail(email.getEmail());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void syncToEntity(KTMEntity entity) {
        if (entity != null && entity instanceof Party) {
            super.syncToEntity(entity);
            Party party = (Party) entity;
            PartyIdentifier identifier = party.getIdentifier();
            if (identifier == null) {
                identifier = new PartyIdentifier();
                party.setIdentifier(identifier);
            }
            identifier.setIdentifier(this.getIdentifier());
            syncAddressToEntity(party);
            syncTelephoneToEntity(party);
            syncFaxToEntity(party);
            syncEmailToEntity(party);
        }
    }

    private void syncEmailToEntity(Party party) {
        AddressProperties addrp = null;
        Set<AddressProperties> addrps = party.getAddresses();
        for (AddressProperties _addrp : addrps) {
            if (_addrp.getUseage().equals(EAddressType.EMAIL.toString())) {
                addrp = _addrp;
                break;
            }
        }
        if (addrp == null) {
            addrp = new AddressProperties();
            addrp.setParty(party);
            addrp.setUseage(EAddressType.EMAIL.toString());
            party.getAddresses().add(addrp);
        }
        if (addrp.getAddress() == null) {
            addrp.setAddress(new EmailAddress());
            addrp.setParty(party);
        }
        EmailAddress email = (EmailAddress) addrp.getAddress();
        email.setEmail(this.getEmail());
    }

    private void syncFaxToEntity(Party party) {
        AddressProperties addrp = null;
        Set<AddressProperties> addrps = party.getAddresses();
        for (AddressProperties _addrp : addrps) {
            if (_addrp.getUseage().equals(EAddressType.FAX.toString())) {
                addrp = _addrp;
                break;
            }
        }
        if (addrp == null) {
            addrp = new AddressProperties();
            addrp.setParty(party);
            addrp.setUseage(EAddressType.FAX.toString());
            party.getAddresses().add(addrp);
        }
        if (addrp.getAddress() == null) {
            addrp.setAddress(new TelephoneAddress());
            addrp.setParty(party);
        }
        TelephoneAddress phone = (TelephoneAddress) addrp.getAddress();
        phone.setNumber(this.getTel());
    }

    private void syncTelephoneToEntity(Party party) {
        AddressProperties addrp = null;
        Set<AddressProperties> addrps = party.getAddresses();
        for (AddressProperties _addrp : addrps) {
            if (_addrp.getUseage().equals(EAddressType.TELEPHONE.toString())) {
                addrp = _addrp;
                break;
            }
        }
        if (addrp == null) {
            addrp = new AddressProperties();
            addrp.setParty(party);
            addrp.setUseage(EAddressType.TELEPHONE.toString());
            party.getAddresses().add(addrp);
        }
        if (addrp.getAddress() == null) {
            addrp.setAddress(new TelephoneAddress());
            addrp.setParty(party);
        }
        TelephoneAddress phone = (TelephoneAddress) addrp.getAddress();
        phone.setNumber(this.getTel());
    }

    private void syncAddressToEntity(Party party) {
        AddressProperties addrp = null;
        Set<AddressProperties> addrps = party.getAddresses();
        for (AddressProperties _addrp : addrps) {
            if (_addrp.getUseage().equals(EAddressType.GEOGRAPHICS.toString())) {
                addrp = _addrp;
                break;
            }
        }
        if (addrp == null) {
            addrp = new AddressProperties();
            addrp.setParty(party);
            addrp.setUseage(EAddressType.GEOGRAPHICS.toString());
            party.getAddresses().add(addrp);
        }
        if (addrp.getAddress() == null) {
            addrp.setAddress(new GeographicAddress());
        }
        GeographicAddress adds = (GeographicAddress) addrp.getAddress();
        adds.setAddressLine1(this.getAddress());
    }
}
