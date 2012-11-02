package org.ktm.scc.bean;

import java.util.Collection;
import org.ktm.domain.KTMEntity;
import org.ktm.domain.party.PartyRole;
import org.ktm.domain.party.PartyRoleIdentifier;
import org.ktm.web.bean.FormBean;

public class PartyRoleBean extends FormBean {

    private String    name;
    private PartyBean party;
    private String    identifier;

    @Override
    public void reset() {
        super.reset();
        party.reset();
    }

    @Override
    public void loadToForm(KTMEntity entity) {
        if (entity != null && entity instanceof PartyRole) {
            super.loadToForm(entity);
            PartyRole partyRole = (PartyRole) entity;
            this.setIdentifier(partyRole.getIdentifier().getIdentifier());
            party.loadToForm(partyRole.getParty());
        }
    }

    @Override
    public void syncToEntity(KTMEntity entity) {
        if (entity != null && entity instanceof PartyRole) {
            super.syncToEntity(entity);

            PartyRole partyRole = (PartyRole) entity;
            party.syncToEntity(partyRole.getParty());
            partyRole.setName(this.getName());
            PartyRoleIdentifier identifier = partyRole.getIdentifier();
            if (identifier == null) {
                identifier = new PartyRoleIdentifier();
                partyRole.setIdentifier(identifier);
            }
            identifier.setIdentifier(this.getIdentifier());
        }
    }

    @Override
    public void loadFormCollection(Collection<?> entitys) {
        if (entitys != null) {
            getFormCollection().clear();
            for (Object entity : entitys) {
                if (entity instanceof PartyRole) {
                    PartyRole partyRole = (PartyRole) entity;
                    PartyRoleBean bean = new PartyRoleBean();
                    bean.loadToForm(partyRole);
                    getFormCollection().add(bean);
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PartyBean getParty() {
        return party;
    }

    public void setParty(PartyBean party) {
        this.party = party;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

}
