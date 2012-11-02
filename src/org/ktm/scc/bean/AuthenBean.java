package org.ktm.scc.bean;

import org.ktm.domain.KTMEntity;
import org.ktm.domain.party.Authen;
import org.ktm.web.bean.FormBean;

public class AuthenBean extends FormBean {

    private String    username;
    private String    password;
    private PartyBean party;

    @Override
    public void loadToForm(KTMEntity entity) {
        if (entity != null && entity instanceof Authen) {
            super.loadToForm(entity);

            Authen authen = (Authen) entity;

            if (party != null) {
                party.loadToForm(authen.getParty());
            }

            this.setUsername(authen.getUsername());
            this.setPassword(authen.getPassword());
        }
    }

    @Override
    public void syncToEntity(KTMEntity entity) {
        if (entity != null && entity instanceof Authen) {
            super.syncToEntity(entity);

            Authen authen = (Authen) entity;

            if (party != null) {
                party.syncToEntity(entity);
            }

            authen.setUsername(this.getUsername());
            authen.setPassword(this.getPassword());
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PartyBean getParty() {
        return party;
    }

    public void setParty(PartyBean party) {
        this.party = party;
    }
}
