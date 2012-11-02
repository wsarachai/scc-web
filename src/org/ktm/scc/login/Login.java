package org.ktm.scc.login;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.ktm.authen.Authenticator;
import org.ktm.authen.AuthenticatorFactory;
import org.ktm.exception.AuthException;
import org.ktm.scc.bean.LoginBean;
import org.ktm.servlet.AbstractServlet;
import org.ktm.servlet.ActionForward;
import org.ktm.utils.Globals;
import org.ktm.web.bean.FormBean;

@WebServlet("/login")
public class Login extends AbstractServlet {

    private static final long serialVersionUID = 1L;
    private final Logger      log              = Logger.getLogger(Login.class);

    @Override
    public String getBeanClass() {
        return "org.ktm.stock.bean.LoginBean";
    }

    @Override
    protected ActionForward processRequest(FormBean form, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = request.getServletContext();
        LoginBean bean = (LoginBean) form;

        log.info("Username: " + bean.getLoginuser());

        String forwardUri = servletContext.getInitParameter(Globals.LOGIN_PAGE);
        ActionForward action = ActionForward.getUri(this, request, forwardUri);

        try {
            String authenticatorClassName = servletContext.getInitParameter("authenticator_class");
            Authenticator auth = AuthenticatorFactory.getAuthComponent(request, servletContext, authenticatorClassName);
            if (auth != null) {
                auth.doLogin(request, bean.getLoginuser(), bean.getLoginpassword());
                if (auth.isUserLoggedIn()) {
                    log.info("Login success.");

                    forwardUri = AuthenticatorFactory.obtainForward(AuthenticatorFactory.restoreRequestContext(request));
                    if (isEmptyString(forwardUri)) {
                        forwardUri = servletContext.getInitParameter(Globals.MAIN_PAGE);
                    }
                    action = ActionForward.getAction(this, request, forwardUri, true);
                } else {
                    log.info("Login failed !!");
                }
            } else {
                throw new AuthException("Login error !!");
            }
        } catch (AuthException ex) {
            log.fatal(ex);
        }
        return action;
    }
}
