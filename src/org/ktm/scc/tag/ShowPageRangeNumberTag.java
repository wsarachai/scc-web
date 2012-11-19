package org.ktm.scc.tag;

import java.io.IOException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.ktm.web.bean.FormBean;
import org.ktm.web.tags.Functions;

public class ShowPageRangeNumberTag extends SimpleTagSupport {

	@Override
	public void doTag() throws IOException {
		FormBean bean = (FormBean) getJspContext().findAttribute( "bean" );

		int st = ( bean.getPageNumber() * bean.getMaxPage() ) + 1;
		int en = st + bean.getMaxPage() - 1;
		en = en > bean.getMaxRows() ? bean.getMaxRows() : en;

		st = st > en ? en : st;

		JspWriter out = getJspContext().getOut();
		out.println( "<span id='page-number'>" + st + "</span>" );
		out.println( "<span id='page-range'> - " + en + "</span>" );
		out.println( "<span id='max-rows'>" + Functions.getText( "page.of" )
						+ " "
						+ bean.getMaxRows()
						+ "</span>" );
	}
}
