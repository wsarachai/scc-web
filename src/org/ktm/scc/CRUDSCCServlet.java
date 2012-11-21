package org.ktm.scc;

import java.util.Vector;
import org.ktm.servlet.CRUDServlet;

public abstract class CRUDSCCServlet extends CRUDServlet {

	protected static Vector<String>	roles				= new Vector<String>();

	static {
		roles.add( "Root" );
		roles.add( "Admin" );
	}

	private static final long		serialVersionUID	= 1L;

}
