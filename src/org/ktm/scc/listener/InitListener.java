package org.ktm.scc.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.ktm.core.KTMContext;
import org.ktm.utils.Localizer;

// import java.util.logging.Logger;
@WebListener
public class InitListener implements ServletContextListener {
	public InitListener() {}
	@Override
	public void contextInitialized( ServletContextEvent sce ) {
		ServletContext context = sce.getServletContext();

		KTMContext ktmContext = new KTMContext();
		context.setAttribute( KTMContext.APPCONTEXT, ktmContext );

		KTMContext.dateFormat = context.getInitParameter( "date_format" );

		KTMContext.paging = Integer.valueOf( context.getInitParameter( "paging" ) );

		Localizer.switchLocale( Localizer.eLanguage.THAI );
	}
	@Override
	public void contextDestroyed( ServletContextEvent sce ) {}
}
