package org.ktm.scc.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.ktm.authen.AuthenticatorFactory;
import org.ktm.crypt.KTMCrypt;
import org.ktm.web.tags.Functions;

public class ShowSideBarTag extends SimpleTagSupport {
	private List<String>	adminRoles		= new ArrayList<String>();
	private List<String>	employeeRoles	= new ArrayList<String>();

	public void setAdminRole( String adminRole ) {
		adminRoles.clear();
		String [] roles = adminRole.split( "," );
		for ( String role : roles ) {
			adminRoles.add( role );
		}
	}
	public void setEmployeeRole( String employeeRole ) {
		employeeRoles.clear();
		String [] roles = employeeRole.split( "," );
		for ( String role : roles ) {
			employeeRoles.add( role );
		}
	}
	@Override
	public void doTag() throws IOException {
		PageContext pageContext = (PageContext) getJspContext();
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

		boolean inAdminRole = AuthenticatorFactory.isUserInRoles( request,
				adminRoles );
		boolean inEmployeeRole = AuthenticatorFactory.isUserInRoles( request,
				employeeRoles );

		String method = request.getParameter( "method" );
		String module = request.getParameter( "module" );

		if ( !Functions.isEmpty( method ) && !Functions.isEmpty( module ) ) {
			JspWriter out = getJspContext().getOut();
			out.println( "<ul class='nav nav-list'>" );
			if ( inAdminRole ) {
				// Member
				out.println( "<li class='nav-header'>" + Functions.getText( "page.members.title" )
								+ "</li>" );
				// - Add member
				String urlParam = KTMCrypt.encrypt( "method=new&module=member" );
				String url = "page=CRUDMembers&t=t&param=" + urlParam;

				if ( ( method.equals( "new" ) || method.equals( "edit" ) ) && module.equals( "member" ) ) {
					out.print( "<li class='active'><a href='#' ><span class='icon-pencil'></span>" + Functions.getText( "page.member.add" )
								+ "</a></li>" );
				} else {
					out.print( "<li><a href='index?" + url
								+ "' ><span class='icon-pencil'></span>"
								+ Functions.getText( "page.member.add" )
								+ "</a></li>" );
				}

				// - List member
				urlParam = KTMCrypt.encrypt( "method=list&module=member&pageNumber=0" );
				url = "page=CRUDMembers&t=t&param=" + urlParam;

				if ( method.equals( "list" ) && module.equals( "member" ) ) {
					out.print( "<li class='active'><a href='#' ><span class='icon-th'></span>" + Functions.getText( "page.member.list" )
								+ "</a></li>" );
				} else {
					out.print( "<li><a href='index?" + url
								+ "' ><span class='icon-th'></span>"
								+ Functions.getText( "page.member.list" )
								+ "</a></li>" );
				}
			}
			if ( inEmployeeRole ) {
				// Article
				out.println( "<li class='nav-header'>" + Functions.getText( "page.article.title" )
								+ "</li>" );
				// - Add article
				String urlParam = KTMCrypt.encrypt( "method=new&module=article" );
				String url = "page=CRUDArticle&t=t&param=" + urlParam;

				if ( ( method.equals( "new" ) || method.equals( "edit" ) ) && module.equals( "article" ) ) {
					out.print( "<li class='active'><a href='#' ><span class='icon-pencil'></span>" + Functions.getText( "page.article.add_news" )
								+ "</a></li>" );
				} else {
					out.print( "<li><a href='index?" + url
								+ "' ><span class='icon-pencil'></span>"
								+ Functions.getText( "page.article.add_news" )
								+ "</a></li>" );
				}
				// List article
				urlParam = KTMCrypt.encrypt( "method=list&module=article&pageNumber=0" );
				url = "page=CRUDArticle&t=t&param=" + urlParam;

				if ( method.equals( "list" ) && module.equals( "article" ) ) {
					out.print( "<li class='active'><a href='#'><span class='icon-th'></span>" + Functions.getText( "page.article.list_news" )
								+ "</a></li>" );
				} else {
					out.print( "<li><a href='index?" + url
								+ "' ><span class='icon-th'></span>"
								+ Functions.getText( "page.article.list_news" )
								+ "</a></li>" );
				}

				// Gallery
				out.println( "<li class='nav-header'>" + Functions.getText( "page.gallery.title" )
								+ "</li>" );
				// Add gallery
				urlParam = KTMCrypt.encrypt( "method=new&module=gallery" );
				url = "page=CRUDGallery&t=t&param=" + urlParam;

				if ( ( method.equals( "new" ) || method.equals( "edit" ) ) && module.equals( "gallery" ) ) {
					out.print( "<li class='active'><a href='#' ><span class='icon-pencil'></span>" + Functions.getText( "page.gallery.add" )
								+ "</a></li>" );
				} else {
					out.print( "<li><a href='index?" + url
								+ "' ><span class='icon-pencil'></span>"
								+ Functions.getText( "page.gallery.add" )
								+ "</a></li>" );
				}
				// List gallery
				urlParam = KTMCrypt.encrypt( "method=list&module=gallery&pageNumber=0" );
				url = "page=CRUDGallery&t=t&param=" + urlParam;

				if ( method.equals( "list" ) && module.equals( "gallery" ) ) {
					out.print( "<li class='active'><a href='#' ><span class='icon-th'></span>" + Functions.getText( "page.gallery.list" )
								+ "</a></li>" );
				} else {
					out.print( "<li><a href='index?" + url
								+ "' ><span class='icon-th'></span>"
								+ Functions.getText( "page.gallery.list" )
								+ "</a></li>" );
				}
			}
			out.println( "</ul>" );
		}
	}
}
