<%@ page language="java" contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8"
%>
<%@ taglib uri="/WEB-INF/tags/ktm-libs.tld" prefix="ktm"%>
<ktm:enforceAuthentication loginPage="/RGB7-backoffice-v4/login.jsp" />
<ktm:isUserNotInRoles roles="Root,Admin,Employee">
		<ktm:redirectPage page="/index.jsp" />
</ktm:isUserNotInRoles>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>RGB7 BK : ${ktm:getText('page.article.title') }</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<!-- Le styles -->
<link href="assets/css/bootstrap.css" rel="stylesheet">
<style type="text/css">
body {
		padding-top: 60px;
		padding-bottom: 40px;
}

.sidebar-nav {
		padding: 9px 0;
}
</style>
<link href="assets/css/bootstrap-responsive.css" rel="stylesheet">
<link href="assets/css/docs.css" rel="stylesheet">
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
<!-- Le fav and touch icons -->
<link rel="shortcut icon" href="assets/ico/favicon.ico">
<link rel="apple-touch-icon-precomposed" sizes="144x144"
		href="assets/ico/apple-touch-icon-144-precomposed.png"
>
<link rel="apple-touch-icon-precomposed" sizes="114x114"
		href="assets/ico/apple-touch-icon-114-precomposed.png"
>
<link rel="apple-touch-icon-precomposed" sizes="72x72"
		href="assets/ico/apple-touch-icon-72-precomposed.png"
>
<link rel="apple-touch-icon-precomposed"
		href="assets/ico/apple-touch-icon-57-precomposed.png"
>
</head>
<body>
		<div class="navbar navbar-fixed-top">
				<div class="navbar-inner">
						<jsp:include page="common/headerbar.jsp"></jsp:include>
				</div>
		</div>
		<div class="container-fluid">
				<div class="row-fluid">
						<div class="span3">
								<div class="well sidebar-nav hidden-phone">
										<ktm:ShowSideBar employeeRole="Root,Employee,Admin"
												adminRole="Root,Admin"
										/>
								</div>
								<!--/.well -->
						</div>
						<!--/span-->
						<!--  ===========Content====================-->
						<div class="span9  ">
								<h1>${ktm:getText('page.article.list_news') } <ktm:ShowUserInfo info="division_name"/></h1>
								<br />
								<!--  ===========breadcrumb====================-->
								<ul class="breadcrumb">
										<li><a href="start.jsp">${ktm:getText('page.home') }</a>
												<span class="divider">/</span></li>
										<li><a
												href="CRUDArticle?method=list&module=article&pageNumber=0"
										>${ktm:getText('page.article.title') }</a> <span class="divider">/</span></li>
										<li class="active">${ktm:getText('page.article.list_news') }</li>
								</ul>
								<br />
								<!--  ===========SEARCH ====================
								<form class="form-search ">
										<input type="text" class="input-medium search-query">
										<button type="submit" class="btn">${ktm:getText('page.search') }</button>
								</form>-->
								<!--  ===========Content====================-->
								<table class="table">
										<thead>
												<tr>
														<th width="5%">#</th>
														<th width="35%">${ktm:getText('page.article.title') }</th>
														<th width="15%">${ktm:getText('page.date') }</th>
														<th width="20%">${ktm:getText('page.members.division') }</th>
														<th width="5%"></th>
														<th width="20%">Action</th>
												</tr>
										</thead>
										<tbody>
												<ktm:scc_iterate name="bean" property="formCollection"
														id="article" module="article"
												>
														<tr>
																<td>${id + ( bean.pageNumber * bean.maxPage )}</td>
																<td>${article.title}</td>
																<td>${article.dateCreated}</td>
																<td>${article.author.name}</td>
																<td>
																	<ktm:if>
																	<ktm:condition>${article.publishOnMain == 'checked'}</ktm:condition>
																	<ktm:then>
																		<span class=" icon-home"></span>
																	</ktm:then>
																	</ktm:if>
																</td>
																<td>
																<a
																		href="index?page=CRUDArticle&t=t&param=${encryptEdit}"
																><span class=" icon-edit"></span>
																				${ktm:getText('page.btn.edit')}</a>&nbsp;| <a
																		href="index?page=CRUDArticle&t=t&param=${encryptDel}"
																		onclick="return confirm('${ktm:getText('page.confirm.delete')}');"
																><span class=" icon-remove"></span>
																				${ktm:getText('page.btn.delete')}</a></td>
														</tr>
												</ktm:scc_iterate>
										</tbody>
								</table>
								<!--  =========== End Content====================-->
								<br />
								<ktm:ShowPageRangeNumber />
								<div class="pagination ">
										<ktm:Pagination servletName="CRUDArticle" module="article" />
								</div>
						</div>
						<!--/span-->
				</div>
				<!--/row-->
				<hr>
				<footer>
						<p>&copy; RGB7 version 4.02</p>
				</footer>
		</div>
		<!--/.fluid-container-->
		<!-- Le javascript
    ================================================== -->
		<!-- Placed at the end of the document so the pages load faster -->
		<script src="assets/js/jquery.js"></script>
		<script src="assets/js/bootstrap-transition.js"></script>
		<script src="assets/js/bootstrap-alert.js"></script>
		<script src="assets/js/bootstrap-modal.js"></script>
		<script src="assets/js/bootstrap-dropdown.js"></script>
		<script src="assets/js/bootstrap-scrollspy.js"></script>
		<script src="assets/js/bootstrap-tab.js"></script>
		<script src="assets/js/bootstrap-tooltip.js"></script>
		<script src="assets/js/bootstrap-popover.js"></script>
		<script src="assets/js/bootstrap-button.js"></script>
		<script src="assets/js/bootstrap-collapse.js"></script>
		<script src="assets/js/bootstrap-carousel.js"></script>
		<script src="assets/js/bootstrap-typeahead.js"></script>
</body>
</html>
