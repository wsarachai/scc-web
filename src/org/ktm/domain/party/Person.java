package org.ktm.domain.party;

import java.text.ParseException;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import org.ktm.utils.DateUtils;

/*
 * The Person represents information about a human being.
 */
@Entity
public class Person extends Party {

    private static final long serialVersionUID = 1L;

    private String            prename;
    private String            firstname;
    private String            lastname;
    private Date              birthDay;
    private ISOGender         gender;

    @Column(name = "prename")
    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }

    @Column(name = "firstname")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Column(name = "lastname")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Column(name = "birthDay")
    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public void setBirthDay(String birthDay) {
        try {
            setBirthDay(DateUtils.formatString(birthDay));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Column(name = "iso_gender")
    public ISOGender getGender() {
        return gender;
    }

    public void setGender(ISOGender gender) {
        this.gender = gender;
    }

    /*
     * This function is used for the Autocompleter example with seperate label
     * element.
     */
    @Transient
    public String getLabel() {
        return this.prename + this.firstname + " " + this.lastname;
    }

}
