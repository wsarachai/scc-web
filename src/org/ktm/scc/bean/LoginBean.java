package org.ktm.scc.bean;

import org.ktm.web.bean.FormBean;

public class LoginBean extends FormBean {

    private String loginuser;
    private String loginpassword;

    public String getLoginuser() {
        return loginuser;
    }

    public void setLoginuser(String loginuser) {
        this.loginuser = loginuser;
    }

    public String getLoginpassword() {
        return loginpassword;
    }

    public void setLoginpassword(String loginpassword) {
        this.loginpassword = loginpassword;
    }

}
