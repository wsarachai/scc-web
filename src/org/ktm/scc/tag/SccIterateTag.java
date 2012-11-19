package org.ktm.scc.tag;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.jsp.JspException;
import org.ktm.crypt.KTMCrypt;
import org.ktm.utils.PropertyUtils;
import org.ktm.web.bean.FormBean;
import org.ktm.web.tags.IterateTag;

public class SccIterateTag extends IterateTag {

	private String	module;

	@Override
	public void doTag() throws JspException, IOException {
		FormBean bean = null;
		if ( collection == null ) {
			try {
				bean = (FormBean) getJspContext().findAttribute( name );
				if ( bean == null ) {
					throw new JspException( "no_attribute_name" );
				}
				if ( property == null ) {
					collection = bean;
				} else {
					collection = PropertyUtils.getProperty( bean, property );
				}
				if ( collection == null ) {
					throw new JspException( "no_collection_object" );
				}
			}
			catch ( Exception ex ) {}
		}

		if ( collection instanceof Collection ) {
			iterator = ( (Collection<?>) collection ).iterator();
		} else if ( collection instanceof Iterator ) {
			iterator = (Iterator<?>) collection;
		}

		int idx = 1;
		while ( iterator.hasNext() ) {
			FormBean element = (FormBean) iterator.next();
			getJspContext().setAttribute( id, element );
			getJspContext().setAttribute( "id", "" + idx );
			String encryptEdit = "method=edit" + "&module="
									+ module
									+ "&uniqueId="
									+ element.getUniqueId()
									+ "&pageNumber="
									+ bean.getPageNumber();
			getJspContext().setAttribute( "encryptEdit",
					KTMCrypt.encrypt( encryptEdit ) );
			String encryptDel = "method=del" + "&module="
								+ module
								+ "&uniqueId="
								+ element.getUniqueId()
								+ "&pageNumber="
								+ bean.getPageNumber();
			getJspContext().setAttribute( "encryptDel",
					KTMCrypt.encrypt( encryptDel ) );
			getJspBody().invoke( null );
			idx++;
		}
	}

	public String getModule() {
		return module;
	}

	public void setModule( String module ) {
		this.module = module;
	}
}
