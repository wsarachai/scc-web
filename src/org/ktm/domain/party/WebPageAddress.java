package org.ktm.domain.party;

import javax.persistence.Column;
import javax.persistence.Entity;

/*
 * The WebPageAddress represents th URL for a Web page related to the Party
 */
@Entity
public class WebPageAddress extends Address {

    private static final long serialVersionUID = 2201717416338538494L;

    private String            urlAddress;

    @Column(name = "url_address")
    public String getUrlAddress() {
        return urlAddress;
    }

    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress;
    }
}
