package org.ktm.scc.login;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.ktm.authen.Authenticator;
import org.ktm.authen.AuthenticatorFactory;
import org.ktm.exception.AuthException;
import org.ktm.scc.bean.LoginBean;
import org.ktm.servlet.AbstractServlet;
import org.ktm.servlet.ActionForward;
import org.ktm.utils.Globals;
import org.ktm.web.bean.FormBean;

@WebServlet( "/RGB7-backoffice-v4/login" )
public class Login extends AbstractServlet {

	private static final long	serialVersionUID	= 1L;
	private final Logger		log					= Logger.getLogger( Login.class );

	@Override
	public String getBeanClass() {
		return "org.ktm.scc.bean.LoginBean";
	}

	@Override
	protected ActionForward processRequest( FormBean form,
											HttpServletRequest request,
											HttpServletResponse response,
											final String htmlMethod,
											final String htmlModule ) {
		ServletContext servletContext = request.getServletContext();
		LoginBean bean = (LoginBean) form;
		String message = "";

		log.info( "Username: " + bean.getLoginuser() );

		String forwardUri = servletContext.getInitParameter( Globals.LOGIN_PAGE );
		ActionForward action = ActionForward.getUri( this, request, forwardUri );

		Authenticator auth = null;
		try {
			String authenticatorClassName = servletContext.getInitParameter( "authenticator_class" );
			auth = AuthenticatorFactory.getAuthComponent( request,
					servletContext,
					authenticatorClassName );
			if ( auth != null ) {
				auth.doLogin( request,
						bean.getLoginuser(),
						bean.getLoginpassword() );
				if ( auth.isUserLoggedIn() ) {
					log.info( "Login success." );

					forwardUri = AuthenticatorFactory.obtainForward( AuthenticatorFactory.restoreRequestContext( request ) );
					if ( isEmptyString( forwardUri ) ) {
						forwardUri = servletContext.getInitParameter( Globals.MAIN_PAGE );
					}
					action = ActionForward.getAction( this,
							request,
							forwardUri,
							true );
				} else {
					log.info( "Login failed !!" );
				}
			} else {
				throw new AuthException( "Login error !!" );
			}
		}
		catch ( AuthException ex ) {
			log.fatal( ex );
			message = ex.getMessage();
		}

		if ( auth != null && bean.getUseCookie().equals( "on" ) ) {
			if ( auth.isUserLoggedIn() ) {
				int expiry = Integer.valueOf( 60 * 60 * 24 * 30 );
				this.setCookieValue( response,
						Authenticator.PROP_USERNAME,
						bean.getLoginuser() ).setMaxAge( expiry );
				this.setCookieValue( response,
						Authenticator.PROP_PASSWORD,
						bean.getLoginpassword() ).setMaxAge( expiry );
				this.setCookieValue( response,
						"saveCookie",
						bean.getUseCookie() ).setMaxAge( expiry );
			}
		}

		try {
			response.setCharacterEncoding( "UTF-8" );
			PrintWriter out = response.getWriter();
			JSONObject json = new JSONObject();

			if ( auth != null && auth.isUserLoggedIn() ) {
				HttpSession session = request.getSession();
				session.setAttribute( Authenticator.PROP_USERNAME,
						auth.getProperty( Authenticator.PROP_USERNAME ) );
				session.setAttribute( Authenticator.PROP_PASSWORD,
						auth.getProperty( Authenticator.PROP_PASSWORD ) );
				json.put( "result", "success" );
			} else {
				bean.setLoginuser( this.getCookieValue( request,
						Authenticator.PROP_USERNAME,
						"" ) );
				bean.setLoginpassword( this.getCookieValue( request,
						Authenticator.PROP_PASSWORD,
						"" ) );
				bean.setUseCookie( this.getCookieValue( request,
						"saveCookie",
						"" ) );
				json.put( "result", "fail" );
			}

			json.put( "forwardUri", action.getForwardUri() );
			json.put( "message", message );
			out.print( json.toString() );
		}
		catch ( IOException ex ) {
			ex.printStackTrace();
		}

		return null;
	}
}
