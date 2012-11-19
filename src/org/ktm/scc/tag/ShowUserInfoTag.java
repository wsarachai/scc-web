package org.ktm.scc.tag;

import java.io.IOException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.ktm.authen.Authenticator;
import org.ktm.dao.KTMEMDaoFactory;
import org.ktm.dao.party.AuthenDao;
import org.ktm.domain.party.Authen;

public class ShowUserInfoTag extends SimpleTagSupport {

	private String	info;

	@Override
	public void doTag() throws IOException {
		Object obj = getJspContext().findAttribute( Authenticator.PROP_USERNAME );
		if ( obj != null && obj instanceof String ) {
			JspWriter out = getJspContext().getOut();
			String username = (String) obj;
			AuthenDao authenDao = KTMEMDaoFactory.getInstance().getAuthenDao();
			Authen auth = authenDao.findByUsername( username );
			if ( auth != null ) {
				if ( info.equals( "username" ) ) {
					out.print( username );
				} else if ( info.equals( "id" ) ) {
					if ( auth.getParty() != null ) {
						out.print( auth.getParty().getUniqueId() );
					}
				}
			}
		}
	}

	public String getInfo() {
		return info;
	}

	public void setInfo( String info ) {
		this.info = info;
	}
}
