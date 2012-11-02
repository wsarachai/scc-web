package org.ktm.scc.bean;

import org.ktm.domain.KTMEntity;
import org.ktm.domain.party.Division;
import org.ktm.domain.party.Organization;

public class DivisionBean extends PartyRoleBean {

    public DivisionBean() {
        this.setParty(new OrganizationBean());
    }

    @Override
    public void loadToForm(KTMEntity entity) {
        if (entity != null && entity instanceof Division) {
            super.loadToForm(entity);
            Division division = (Division) entity;
            if (division.getParty() != null && division.getParty() instanceof Organization) {
                Organization org = (Organization) division.getParty();
                setName(org.getThaiName());
            }
        }
    }
}
