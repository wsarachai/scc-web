package org.ktm.domain.party;

import javax.persistence.Column;
import javax.persistence.Entity;

/*
 * The GeographicAddress represents a geographic location at which a Party may
 * be contacted. It is a postal address for the Party
 */
@Entity
public class GeographicAddress extends Address {

    private static final long serialVersionUID = 1L;

    private String            addressLine1;
    private String            addressLine2;
    private String            addressLine3;
    private String            city;
    private String            zipcode;

    @Column(name = "addressLine1")
    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    @Column(name = "addressLine2")
    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    @Column(name = "addressLine3")
    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "zipcode")
    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

}
