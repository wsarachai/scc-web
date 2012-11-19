package org.ktm.scc;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.ktm.crypt.KTMCrypt;
import org.ktm.servlet.AbstractServlet;
import org.ktm.servlet.ActionForward;
import org.ktm.utils.Globals;
import org.ktm.web.bean.FormBean;

@WebServlet( name = "IndexServlet",
				urlPatterns = { "/index", "/RGB7-backoffice-v4/index" } )
public class Index extends AbstractServlet {
	private static final long	serialVersionUID	= 1L;

	private final Logger		log					= Logger.getLogger( Index.class );

	private String				redirectName;

	@Override
	public String getBeanClass() {
		return "org.ktm.scc.bean.MainBean";
	}

	public String getRedirectName() {
		return redirectName;
	}

	@Override
	protected ActionForward processRequest( FormBean form,
											HttpServletRequest request,
											HttpServletResponse response,
											final String htmlMethod,
											final String htmlModule ) {
		redirectName = request.getParameter( "page" );
		ActionForward action = null;

		String pageType = request.getParameter( "t" );
		if ( pageType != null && redirectName != null ) {
			if ( pageType.equals( "t" ) ) {
				log.info( "Go to page: " + redirectName );
				String param = request.getParameter( "param" );
				param = KTMCrypt.decrypt( param );
				action = ActionForward.getAction( this,
						request,
						redirectName + "?" + param );
			}
		} else if ( redirectName == null ) {
			ServletContext context = request.getServletContext();
			String mainPage = context.getInitParameter( Globals.MAIN_PAGE );
			action = ActionForward.getAction( this, request, mainPage );
		} else {
			action = ActionForward.getAction( this, request, redirectName );
		}
		return action;
	}

}
