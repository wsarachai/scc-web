<%@ page language="java" contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8"
%>
<%@ taglib uri="/WEB-INF/tags/ktm-libs.tld" prefix="ktm"%>
<%@ taglib uri="http://granule.com/tags" prefix="g" %>
<ktm:enforceAuthentication loginPage="/RGB7-backoffice-v4/login.jsp" />
<ktm:isUserNotInRoles roles="Root,Admin,Employee">
		<ktm:redirectPage page="/index.jsp" />
</ktm:isUserNotInRoles>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width">
<title>${ktm:getText("page.article.title")}</title>
<link rel="stylesheet" href="assets/css/bootstrap.css">
<link rel="stylesheet" href="assets/css/style.css">
<link rel="stylesheet" href="assets/css/jquery.fileupload-ui.css">
<link rel="stylesheet" href="assets/css/jquery-ui.css" />
<link rel="stylesheet" href="assets/css/style.css" />
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
								<h1>${ktm:getText('page.article.edit') }</h1>
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
										<li><a
												href="CRUDArticle?method=list&module=article&pageNumber=0"
										>${ktm:getText('page.article.title') }</a> <span class="divider">/</span></li>
										<li class="active">${ktm:getText('page.article.edit') }</li>
								</ul>
								<br />
								<!--  ===========form====================-->
								<form id="frmContent" method="post" action="CRUDArticle"
										class="span10" onsubmit="return validateForm();"
								>
										<input type="hidden" name="uniqueId" value="${bean.uniqueId}">
										<input type="hidden" name="identifier"
												value="${bean.identifier}"
										> <input type="hidden" name="dateCreated"
												value="${bean.dateCreated}"
										><input type="hidden" name="dateModified"
												value="${bean.dateModified}"
										> <input type="hidden" name="module" value="article">
										<input type="hidden" name="pageNumber"
												value="${bean.pageNumber}"
										> <input type="hidden" name="method" value="save"><br>
										<div class="control-group">
												<label for="title">${ktm:getText('page.article.head')
														}</label>
												<div class="controls">
														<input style="width: 400px;" type="text" name="title"
																id="title" value="${bean.title}"
																class="text ui-widget-content ui-corner-all"
														/>
												</div>
										</div>
										<ktm:isUserInRoles roles="Root,Admin">
												<label class="checkbox inline"> <input
														type="checkbox" id="publishOnMain" ${bean.publishOnMain}
														name="publishOnMain" value="checked"
												> ${ktm:getText('page.publish_on_main') }
												</label>
												<br>
												<br>
										</ktm:isUserInRoles>
										<label for="content">${ktm:getText('page.article.content')
												}</label>
										<textarea id="content" name="content">${bean.content}</textarea>
								</form>
								<br>
								<form id="fileupload" action="upload_image_article"
										class="span10" method="POST" enctype="multipart/form-data"
								>
										<input type="hidden" name="uniqueId" value="${bean.uniqueId}">
										<div class="fileupload-buttonbar">
												<table role="presentation" class="table table-striped">
														<tbody class="files" data-toggle="modal-gallery"
																data-target="#modal-gallery"
														>
																<ktm:map_iterate name="bean" id="img" property="images">
																		<tr class="template-download fade in">
																				<td class="preview"><a
																						href="upload_image_article?getfile=${img.name}"
																						title="${img.name}" rel="gallery"
																						download="${img.name}"
																				><img
																								src="upload_image_article?getthumb=${img.name}"
																						></a></td>
																				<td class="name"><a
																						href="upload_image_article?getfile=${img.name}"
																						title="${img.name}" rel="gallery"
																						download="${img.name}"
																				>${img.name}</a></td>
																				<td class="size"><span>${img.size}</span></td>
																				<td colspan="2"></td>
																				<td class="delete">
																						<button class="btn btn-danger" data-type="GET"
																								data-url="upload_image_article?delfile=${img.name}"
																						>
																								<i class="icon-trash icon-white"></i> <span>Delete</span>
																						</button> <input type="checkbox" name="delete" value="1">
																				</td>
																		</tr>
																</ktm:map_iterate>
														</tbody>
												</table>
												<div class="span7">
														<span class="btn btn-success fileinput-button"> <i
																class="icon-plus icon-white"
														> </i><span>Add image...</span> <input type="file"
																name="files[]" multiple
														>
														</span>
														<button type="submit" class="btn btn-primary start">
																<i class="icon-upload icon-white"></i> <span>Upload</span>
														</button>
														<button type="reset" class="btn btn-warning cancel">
																<i class="icon-ban-circle icon-white"></i> <span>Cancel</span>
														</button>
														<button type="button" class="btn btn-danger delete">
																<i class="icon-trash icon-white"></i> <span>Delete</span>
														</button>
														<input type="checkbox" class="toggle">&nbsp;&nbsp;
														<div>
																<p class="validateTips"></p>
														</div>
												</div>
												<div class="span4 fileupload-progress fade">
														<div
																class="progress progress-success progress-striped active"
																role="progressbar" aria-valuemin="0" aria-valuemax="100"
														>
																<div class="bar" style="width: 100%;"></div>
														</div>
														<div class="progress-extended">&nbsp;</div>
												</div>
												<div class="fileupload-loading"></div>
										</div>
								</form>
								<div class="span10">
										<br />
										<hr />
										<div class="button-group pull-right">
												<button id="btnSave" type="button"
														class="btn btn-primary start"
												>
														<i class="icon-upload icon-white"></i> <span>${ktm:getText('page.save')}</span>
												</button>
												<a class="btn btn-warning cancel"
														href="CRUDArticle?method=list&module=article&pageNumber=${bean.pageNumber}"
												><i class="icon-ban-circle icon-white"></i>${ktm:getText('page.cancel')}</a>
										</div>
								</div>
						</div>
				</div>
				<hr>
				<footer>
						<p>&copy; RGB7 version 4.02</p>
				</footer>
		</div>
		<div id="modal-gallery" class="modal modal-gallery hide fade"
				data-filter=":odd"
		>
				<div class="modal-header">
						<a class="close" data-dismiss="modal">&times;</a>
						<h3 class="modal-title"></h3>
				</div>
				<div class="modal-body">
						<div class="modal-image"></div>
				</div>
				<div class="modal-footer">
						<a class="btn modal-download" target="_blank"> <i
								class="icon-download"
						></i> <span>Download</span>
						</a> <a class="btn btn-success modal-play modal-slideshow"
								data-slideshow="5000"
						> <i class="icon-play icon-white"></i> <span>Slideshow</span>
						</a> <a class="btn btn-info modal-prev"> <i
								class="icon-arrow-left icon-white"
						></i> <span>Previous</span>
						</a> <a class="btn btn-primary modal-next"> <span>Next</span> <i
								class="icon-arrow-right icon-white"
						></i>
						</a>
				</div>
		</div>
		<script id="template-upload" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-upload fade">
        <td class="preview"><span class="fade"></span></td>
        <td class="name"><span>{%=file.name%}</span></td>
        <td class="size"><span>{%=o.formatFileSize(file.size)%}</span></td>
        {% if (file.error) { %}
            <td class="error" colspan="2"><span class="label label-important">{%=locale.fileupload.error%}</span> {%=locale.fileupload.errors[file.error] || file.error%}</td>
        {% } else if (o.files.valid && !i) { %}
            <td>
                <div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="bar" style="width:0%;"></div></div>
            </td>
            <td class="start">{% if (!o.options.autoUpload) { %}
                <button class="btn btn-primary">
                    <i class="icon-upload icon-white"></i>
                    <span>{%=locale.fileupload.start%}</span>
                </button>
            {% } %}</td>
        {% } else { %}
            <td colspan="2"></td>
        {% } %}
        <td class="cancel">{% if (!i) { %}
            <button class="btn btn-warning">
                <i class="icon-ban-circle icon-white"></i>
                <span>{%=locale.fileupload.cancel%}</span>
            </button>
        {% } %}</td>
    </tr>
{% } %}
</script>
		<script id="template-download" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-download fade">
        {% if (file.error) { %}
            <td></td>
            <td class="name"><span>{%=file.name%}</span></td>
            <td class="size"><span>{%=o.formatFileSize(file.size)%}</span></td>
            <td class="error" colspan="2"><span class="label label-important">{%=locale.fileupload.error%}</span> {%=locale.fileupload.errors[file.error] || file.error%}</td>
        {% } else { %}
            <td class="preview">{% if (file.thumbnail_url) { %}
                <a href="{%=file.url%}" title="{%=file.name%}" rel="gallery" download="{%=file.name%}"><img src="{%=file.thumbnail_url%}"></a>
            {% } %}</td>
            <td class="name">
                <a href="{%=file.url%}" title="{%=file.name%}" rel="{%=file.thumbnail_url&&'gallery'%}" download="{%=file.name%}">{%=file.name%}</a>
            </td>
            <td class="size"><span>{%=o.formatFileSize(file.size)%}</span></td>
            <td colspan="2"></td>
        {% } %}
        <td class="delete">
            <button class="btn btn-danger" data-type="{%=file.delete_type%}" data-url="{%=file.delete_url%}">
                <i class="icon-trash icon-white"></i>
                <span>{%=locale.fileupload.destroy%}</span>
            </button>
            <input type="checkbox" name="delete" value="1">
        </td>
    </tr>
{% } %}
</script>
		<script src="assets/js/jquery-1.8.2.js"></script>
		<script src="assets/js/vendor/jquery.ui.widget.js"></script>
		<script src="assets/js/tmpl.js"></script>
		<script src="assets/js/load-image.js"></script>
		<script src="assets/js/canvas-to-blob.js"></script>
		<script src="assets/js/bootstrap.js"></script>
		<script src="assets/js/bootstrap-image-gallery.js"></script>
		<script src="assets/js/jquery.iframe-transport.js"></script>
		<script src="assets/js/jquery.fileupload.js"></script>
		<script src="assets/js/jquery.fileupload-fp.js"></script>
		<script src="assets/js/jquery.fileupload-ui.js"></script>
		<script src="assets/js/locale.js"></script>
		<script src="assets/js/ckeditor.js"></script>
		<script src="assets/js/adapters/jquery.js"></script>
		<g:compress method="jsfastmin">
		<script src="assets/js/ktm-lib.js"></script>
		<script src="assets/js/article-main.js"></script>
		<script>
			function validateForm() {
				var bValid = true;
				$( ".control-group" ).removeClass( "error" );
				var title = $( "#title" );
				var content = $( "#content" );
				bValid = bValid && checkLength( title,
																				"${ktm:getText('page.login.input')}${ktm:getText('page.article.head') }",
																				3,
																				512 );
				bValid = bValid && checkLength( content,
																				"${ktm:getText('page.login.input')}${ktm:getText('page.article.content') }",
																				3,
																				16384 );
				if ( bValid ) {
					$( "#form-error" ).fadeOut();
				} else {
					$( "#alert-assist" ).text( "${ktm:getText('page.form-error')}" );
					$( "#form-error" ).fadeIn();
				}
				
				return bValid;
			}
		</script>
		</g:compress>
</body>
</html>