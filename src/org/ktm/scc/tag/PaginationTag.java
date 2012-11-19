package org.ktm.scc.tag;

import java.io.IOException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.ktm.crypt.KTMCrypt;
import org.ktm.web.bean.FormBean;
import org.ktm.web.tags.Functions;

public class PaginationTag extends SimpleTagSupport {

	private static final int	PAGING_NUM	= 4;

	private String				servletName;
	private String				module;

	@Override
	public void doTag() throws IOException {
		FormBean bean = (FormBean) getJspContext().findAttribute( "bean" );

		if ( bean != null ) {
			// Get total paging
			int totalPage = bean.getMaxRows() / bean.getMaxPage();
			totalPage = ( bean.getMaxRows() % bean.getMaxPage() ) == 0	? totalPage
																		: totalPage + 1;

			// Get start paging group
			int startPaging = ( bean.getPageNumber() / PAGING_NUM ) * PAGING_NUM;

			// Determine paging number
			int pagingCount = PAGING_NUM;
			if ( ( startPaging + PAGING_NUM ) > totalPage ) {
				pagingCount = totalPage - startPaging;
			}

			String urlParam = "method=list&module=" + module;
			String url = "index?page=" + servletName + "&t=t&param=";

			JspWriter out = getJspContext().getOut();
			out.println( "<ul>" );

			if ( startPaging >= PAGING_NUM ) {
				int st = startPaging - 1;
				out.print( "<li><a href='" + url
							+ KTMCrypt.encrypt( urlParam + "&pageNumber=" + st )
							+ "'>" );
				out.println( Functions.getText( "page.pre" ) + "</a></li>" );
			}

			int base = startPaging + 1;
			int pageIndex = bean.getPageNumber() + 1;
			for ( int i = 0; i < pagingCount; i++ ) {
				int idx = startPaging + i;
				if ( pageIndex == ( base + i ) ) {
					out.print( "<li class='active'>" );
				} else {
					out.print( "<li>" );
				}
				out.println( "<a href='" + url
								+ KTMCrypt.encrypt( urlParam + "&pageNumber="
													+ idx )
								+ "'>"
								+ ( base + i )
								+ "</a></li>" );
			}

			if ( ( startPaging + PAGING_NUM ) < totalPage ) {
				int st = startPaging + PAGING_NUM;
				out.print( "<li><a href='" + url
							+ KTMCrypt.encrypt( urlParam + "&pageNumber=" + st )
							+ "'>" );
				out.println( Functions.getText( "page.next" ) + "</a></li>" );
			}
			out.println( "</ul>" );
		}
	}
	public String getServletName() {
		return servletName;
	}

	public void setServletName( String servletName ) {
		this.servletName = servletName;
	}

	public String getModule() {
		return module;
	}

	public void setModule( String module ) {
		this.module = module;
	}
}
