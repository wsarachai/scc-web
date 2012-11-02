package org.ktm.scc.bean;

import org.ktm.domain.KTMEntity;
import org.ktm.domain.party.Organization;

public class OrganizationBean extends PartyBean {

    private String thaiName;
    private String englishName;
    private String description;

    @Override
    public void loadToForm(KTMEntity entity) {
        if (entity != null && entity instanceof Organization) {
            super.loadToForm(entity);
            Organization org = (Organization) entity;
            this.setThaiName(org.getThaiName());
            this.setEnglishName(org.getEnglishName());
            this.setDescription(org.getDescription());
        }
    }

    @Override
    public void syncToEntity(KTMEntity entity) {
        if (entity != null && entity instanceof Organization) {
            super.syncToEntity(entity);
            Organization org = (Organization) entity;
            org.setThaiName(this.getThaiName());
            org.setEnglishName(this.getEnglishName());
            org.setDescription(this.getDescription());
        }
    }

    public String getThaiName() {
        return thaiName;
    }

    public void setThaiName(String thaiName) {
        this.thaiName = thaiName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
