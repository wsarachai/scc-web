package org.ktm.scc.tag;

import java.io.IOException;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.ktm.authen.Authenticator;
import org.ktm.authen.AuthenticatorFactory;
import org.ktm.exception.AuthException;

public class ShowUserInfoTag extends SimpleTagSupport {

	private String	info;

	@Override
	public void doTag() throws IOException {
		PageContext pageContext = (PageContext) getJspContext();
		HttpSession session = (HttpSession) pageContext.getSession();

		try {
			Authenticator auth = AuthenticatorFactory.getAuthComponentNoCreate( session );
			String username = (String) auth.getProperty( Authenticator.PROP_USERNAME );
			Integer user_id = (Integer) auth.getProperty( "userid" );
			String user_division_name = (String) auth.getProperty( "user_division_name" );

			JspWriter out = getJspContext().getOut();

			if ( username != null && info.equals( "username" ) ) {
				out.print( username );
			}

			if ( user_id != null && info.equals( "id" ) ) {
				out.print( user_id );
			}

			if ( user_division_name != null && info.equals( "division_name" ) ) {
				out.print( user_division_name );
			}
		}
		catch ( AuthException e ) {
			e.printStackTrace();
		}
	}

	public String getInfo() {
		return info;
	}

	public void setInfo( String info ) {
		this.info = info;
	}
}
