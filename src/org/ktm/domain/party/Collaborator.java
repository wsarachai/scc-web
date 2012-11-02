package org.ktm.domain.party;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Collaborator extends PartyRole {

    private static final long serialVersionUID = 1L;

    private String            description;
    private Integer           payDuration;
    private String            contactName;
    private String            payMethod;
    private String            payPolicy;
    private String            mark;

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "payDuration")
    public Integer getPayDuration() {
        return payDuration;
    }

    public void setPayDuration(Integer payDuration) {
        this.payDuration = payDuration;
    }

    @Column(name = "contactName")
    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    @Column(name = "mark")
    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Column(name = "pay_method")
    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    @Column(name = "pay_policy")
    public String getPayPolicy() {
        return payPolicy;
    }

    public void setPayPolicy(String payPolicy) {
        this.payPolicy = payPolicy;
    }
}
