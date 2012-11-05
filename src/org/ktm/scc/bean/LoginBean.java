package org.ktm.scc.bean;

import org.ktm.web.bean.FormBean;

public class LoginBean extends FormBean {

    private String loginuser;
    private String loginpassword;
    private String useCookie;

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

    public String getUseCookie() {
        return useCookie;
    }

    public String getIsChecked() {
        if (useCookie.equals("on")) { return "checked"; }
        return "";
    }

    public void setUseCookie(String useCookie) {
        this.useCookie = useCookie;
    }

}
