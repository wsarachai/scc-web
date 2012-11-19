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
<title>RGB7 Backoffice V4</title>
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
						<div class="span12">
								<div class="hero-unit">
										<h1>ยินดีต้อนรับ</h1>
										<p>ศุนย์ดำเนินการระบบ วิทยาลัยเทคโนโลยีศรีธนาพณิชยการ
												เชียงใหม่่</p>
								</div>
								<!--/ Data Storage -->
								<div class="row-fluid"></div>
								<!--/.View Detail-->
								<div class="row-fluid">
									<ktm:isUserInRoles roles="Root,Admin">
										<div class="span4">
												<h2>${ktm:getText('page.members.title') }</h2>
												<p>ดูรายชื่อสมาชิก, ค้นหาและจัดการสมาชิก</p>
												<p>
														<a class="btn btn-danger"
																href="CRUDMembers?method=list&module=member&pageNumber=0"
														>${ktm:getText('pabe.btn.view_detail') } &raquo;</a>
												</p>
										</div>
									</ktm:isUserInRoles>
										<!--/span-->
										<div class="span4">
												<h2>${ktm:getText('page.article.title') }</h2>
												<p>ดูรายละเอียดข่าวหรือกิจกรรม,
														ค้นหาและจัดการข่าวหรือกิจกรรม</p>
												<p>
														<a class="btn btn-danger"
																href="CRUDArticle?method=list&module=article&pageNumber=0"
														>${ktm:getText('pabe.btn.view_detail') } &raquo;</a>
												</p>
										</div>
										<!--/span-->
										<div class="span4 ">
												<h2>${ktm:getText('page.gallery.title') }</h2>
												<p>ดูรายการอัลบั้มรูปภาพ, ค้นหาและจัดการอัลบั้มรูปภาพ</p>
												<p>
														<a class="btn btn-danger" href="CRUDGallery?method=list&module=gallery&pageNumber=0">${ktm:getText('pabe.btn.view_detail')
																} &raquo;</a>
												</p>
										</div>
										<!--/span-->
								</div>
								<!--/row-->
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
