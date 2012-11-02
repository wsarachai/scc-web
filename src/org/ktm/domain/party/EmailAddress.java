package org.ktm.domain.party;

import javax.persistence.Column;
import javax.persistence.Entity;

/*
 * The EmailAddress specifics a way for contacting a Party via e-mail.
 */
@Entity
public class EmailAddress extends Address {

    private static final long serialVersionUID = -7200271415099570886L;

    private String            email;

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof EmailAddress) {
            EmailAddress email = (EmailAddress) other;
            if (email != null && email.getEmail() != null && this.getEmail() != null) {
                if (this.getEmail() == email.getEmail()) {
                    result = super.equals(other);
                }
            }
        }
        return result;
    }
}
