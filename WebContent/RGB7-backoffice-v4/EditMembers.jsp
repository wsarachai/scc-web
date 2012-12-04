<%@ page language="java" contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8"
%>
<%@ taglib uri="/WEB-INF/tags/ktm-libs.tld" prefix="ktm"%>
<%@ taglib uri="http://granule.com/tags" prefix="g" %>
<ktm:enforceAuthentication loginPage="/RGB7-backoffice-v4/login.jsp" />
<ktm:isUserNotInRoles roles="Root,Admin,Employee">
		<ktm:redirectPage page="/index.jsp" />
</ktm:isUserNotInRoles>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>RGB7 BK : ${ktm:getText('page.members.title') }</title>
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
						<div class="span9">
								<h1>${ktm:getText('page.member.edit') }</h1>
								<br />
								<!--  =========== Display Error====================-->
								<div id="form-error" class="alert alert-error"
										style="display: none"
								>
										<h4 class="alert-heading">Data Error !!!</h4>
										<p id="alert-assist">Please Check Data again.</p>
								</div>
								<!--  ===========breadcrumb====================-->
								<ul class="breadcrumb">
										<li><a href="start.jsp">${ktm:getText('page.home') }</a>
												<span class="divider">/</span></li>
										<li>${ktm:getText('page.members.title') } <span class="divider">/</span></li>
										<li class="active">${ktm:getText('page.member.edit') }</li>
								</ul>
								<!--  ===========form====================-->
								<form action="CRUDMembers" method="post" class="span9"
										onsubmit="return validateForm();"
								>
										<!-- // inputnormal -->
										<p>
												<span class="badge badge-inverse">1.</span> <strong>${ktm:getText('page.members.general_data')}</strong>
										</p>
										<input type="hidden" id="uniqueId" name="uniqueId"
												value="${bean.uniqueId}"
										> <input type="hidden" name="method" value="save"> <input
												type="hidden" name="module" value="member"
										>
										<input type="hidden" name="pageNumber" value="${bean.pageNumber}">
										<div class="control-group">
												<label class="control-label required" for="loginuser"><span
														class="required"
												>*</span>${ktm:getText('page.members.name') }</label>
												<div class="controls">
														<input type="text" id="loginuser"
																value="${bean.loginuser }"
																placeholder="${ktm:getText('page.members.name') }…"
																name="loginuser" class="span9"
														>
												</div>
										</div>
										<br />
										<div class="control-group">
												<label><span class="required">*</span>${ktm:getText('page.members.email')
														}</label>
												<div class="controls">
														<input type="text" id="email" value="${bean.email }"
																placeholder="${ktm:getText('page.members.email') }…"
																name="email" class="span9"
														>
												</div>
										</div>
										<br />
										<div class="control-group">
												<label>${ktm:getText('page.members.password') }</label>
												<div class="controls">
														<input type="password" id="loginpassword"
																name="loginpassword"
																placeholder="${ktm:getText('page.members.password') }…"
																class="span9"
														>
												</div>
										</div>
										<br />
										<div class="control-group">
												<label>${ktm:getText('page.members.password_confirm')
														}</label>
												<div class="controls">
														<input type="password" id="loginpassword-confirm"
																placeholder="${ktm:getText('page.members.password_confirm') }…"
																class="span9"
														>
												</div>
										</div>
										<ktm:isUserInRoles roles="Root,Admin">
										<br />
										<p>
												<span class="badge badge-inverse">2.</span> <strong>${ktm:getText('page.members.group')}</strong>
										</p>
										<label><span class="required">*</span>${ktm:getText('page.members.division')}</label>
										<select id="divisionId" name="divisionId" class="span9">
												<option value="0" selected="selected" disabled="disabled">${ktm:getText("page.choose")}</option>
												<ktm:options selected="divisionId" bean="bean" label="name"
														value="uniqueId" collection="divisionCollection"
												/>
										</select>
										</ktm:isUserInRoles>
										<div class="span9">
											<hr/>
											<div class="button-group pull-right">
												<button type="submit" class="btn btn-primary start">
														<i class="icon-upload icon-white"></i> <span>${ktm:getText('page.save')}</span>
												</button>
												<a class="btn btn-warning cancel" href="CRUDMembers?method=list&module=member&pageNumber=${bean.pageNumber}"><i class="icon-ban-circle icon-white"></i>${ktm:getText('page.cancel')}</a>
											</div>
										</div>
								</form>
						</div>
						<!--/span-->
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
		<g:compress method="jsfastmin">
		<script src="assets/js/ktm-lib.js"></script>
		<script>
		function validatePassword( bValid ) {
			var password = $( "#loginpassword" );
			var password_confirm = $( "#loginpassword-confirm" );
			bValid = bValid && checkLength( password,
																			"${ktm:getText('page.login.input')}${ktm:getText('page.members.password')}",
																			3,
																			32 );
			bValid = bValid && checkRegexp( password,
																			/^([0-9a-zA-Z])+$/,
																			"${ktm:getText('page.valid.password')}" );
			if ( bValid && ( password.val() != password_confirm.val() ) ) {
				$( ".alert-heading" )
						.text( "${ktm:getText('page.valid.password_confirm')}" );
				password_confirm.parent().parent( ".control-group" ).addClass( "error" );
				password_confirm.focus();
				bValid = false;
			}
			return bValid;
		}
		function validateForm() {
			var bValid = true;
			$( ".control-group" ).removeClass( "error" );
			bValid = bValid && checkLength( $( "#loginuser" ),
																			"${ktm:getText('page.login.input')}${ktm:getText('page.members.name')}",
																			3,
																			32 );
			bValid = bValid && checkLength( $( "#email" ),
																			"${ktm:getText('page.login.input')}${ktm:getText('page.members.email')}",
																			1,
																			80 );
			bValid = bValid && checkRegexp( $( "#loginuser" ),
																			/^[a-z]([0-9a-z_])+$/i,
																			"${ktm:getText('page.valid.username')}" );
			bValid = bValid && checkRegexp( $( "#email" ),
																			/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i,
																			"${ktm:getText('page.valid.email')}" );
			var uid = $.trim( $( "#uniqueId" ).val() );
			if ( bValid && uid == "" ) {
				bValid = validatePassword( bValid );
			}
			if ( bValid && needToCheckPasswd( $( "#loginpassword" ).val() ) ) {
				bValid = validatePassword( bValid );
			}
			bValid = bValid && checkSelect( $( "#divisionId" ),
																			"${ktm:getText('page.valid.division')}",
																			{
																				valueNotEquals: [
																					"0"
																				]
																			} );
			if ( bValid ) {
				$( "#form-error" ).fadeOut();
			} else {
				$( "#alert-assist" ).text( "${ktm:getText('page.form-error')}" );
				$( "#form-error" ).fadeIn();
			}
			return bValid;
		}
		function needToCheckPasswd( passwd ) {
			passwd = $.trim( passwd );
			if ( passwd == "" ) return false;
			return true;
		}

		</script>
		</g:compress>
</body>
</html>
