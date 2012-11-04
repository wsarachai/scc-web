<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tags/ktm-libs.tld" prefix="ktm" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width">
    <title>${ktm:getText("page.article.title")}</title>
    <link rel="stylesheet" href="css/bootstrap.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/jquery.fileupload-ui.css">
    <link rel="stylesheet" href="css/jquery-ui.css" />
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>
<div class="container">
    <div class="page-header">
    <h1>${ktm:getText("page.article.title")}</h1>
    </div>
    <div>
        <form id="frmContent" method="post" action="CRUDArticle">
            <input type="hidden" name="uniqueId" value="${bean.uniqueId}">
            <input type="hidden" name="identifier" value="${bean.identifier}">
            <input type="hidden" name="dateCreated" value="${bean.dateCreated}">
            <input type="hidden" name="method" value="save"><br>
            <label for="articleType">Type</label>
            <select name="articleTypeId" id="articleTypeId" size="1">
              <ktm:options selected="articleTypeId" bean="bean" label="description" value="uniqueId" collection="articleTypeCollection" />
            </select><br>
            <label for="title">Title</label>
            <input style="width:400px;" type="text" name="title" id="title" value="${bean.title}" class="text ui-widget-content ui-corner-all" /><br>
            <label for="content">Content</label>
            <textarea id="content" name="content">${bean.content}</textarea>
        </form>
    </div>
    <br>
    <form id="fileupload" action="upload_image_article" method="POST" enctype="multipart/form-data">
        <input type="hidden" name="uniqueId" value="${bean.uniqueId}">
        <div class="row fileupload-buttonbar">
            <table role="presentation" class="table table-striped">
                <tbody class="files" data-toggle="modal-gallery" data-target="#modal-gallery">
                <ktm:map_iterate name="bean" id="img" property="images">
                  <tr class="template-download fade in">
                      <td class="preview">
                          <a href="upload_image_article?getfile=${img.name}" title="${img.name}" rel="gallery" download="${img.name}"><img src="upload_image_article?getthumb=${img.name}"></a>
                      </td>
                      <td class="name">
                          <a href="upload_image_article?getfile=${img.name}" title="${img.name}" rel="gallery" download="${img.name}">${img.name}</a>
                      </td>
                      <td class="size"><span>${img.size}</span></td>
                      <td colspan="2"></td>
                      <td class="delete">
                          <button class="btn btn-danger" data-type="GET" data-url="upload_image_article?delfile=${img.name}">
                              <i class="icon-trash icon-white"></i>
                              <span>Delete</span>
                          </button>
                          <input type="checkbox" name="delete" value="1">
                      </td>
                  </tr>
                </ktm:map_iterate>
                </tbody>
            </table>
            <div class="span7">
              <span class="btn btn-success fileinput-button">
                  <i class="icon-plus icon-white">
                  </i><span>Add image...</span>
                  <input type="file" name="files[]" multiple>
              </span>
              <button type="submit" class="btn btn-primary start">
                  <i class="icon-upload icon-white"></i>
                  <span>Upload</span>
              </button>
              <button type="reset" class="btn btn-warning cancel">
                  <i class="icon-ban-circle icon-white"></i>
                  <span>Cancel</span>
              </button>
              <button type="button" class="btn btn-danger delete">
                  <i class="icon-trash icon-white"></i>
                  <span>Delete</span>
              </button>
              <input type="checkbox" class="toggle">&nbsp;&nbsp;
              <button id="btnSave" type="button" class="btn">Save data</button>
              <div>
                  <p class="validateTips"></p>
              </div>
            </div>
            <div class="span5 fileupload-progress fade">
                <div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
                    <div class="bar" style="width:0%;"></div>
                </div>
                <div class="progress-extended">&nbsp;</div>
            </div>
            <div class="fileupload-loading"></div>
        </div>
    </form>
  </div>
  <div id="modal-gallery" class="modal modal-gallery hide fade" data-filter=":odd">
    <div class="modal-header">
        <a class="close" data-dismiss="modal">&times;</a>
        <h3 class="modal-title"></h3>
    </div>
    <div class="modal-body"><div class="modal-image"></div></div>
    <div class="modal-footer">
        <a class="btn modal-download" target="_blank">
            <i class="icon-download"></i>
            <span>Download</span>
        </a>
        <a class="btn btn-success modal-play modal-slideshow" data-slideshow="5000">
            <i class="icon-play icon-white"></i>
            <span>Slideshow</span>
        </a>
        <a class="btn btn-info modal-prev">
            <i class="icon-arrow-left icon-white"></i>
            <span>Previous</span>
        </a>
        <a class="btn btn-primary modal-next">
            <span>Next</span>
            <i class="icon-arrow-right icon-white"></i>
        </a>
    </div>
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
<script src="js/jquery-1.8.2.js"></script>
<script src="js/vendor/jquery.ui.widget.js"></script>
<script src="js/tmpl.js"></script>
<script src="js/load-image.js"></script>
<script src="js/canvas-to-blob.js"></script>
<script src="js/bootstrap.js"></script>
<script src="js/bootstrap-image-gallery.js"></script>
<script src="js/jquery.iframe-transport.js"></script>
<script src="js/jquery.fileupload.js"></script>
<script src="js/jquery.fileupload-fp.js"></script>
<script src="js/jquery.fileupload-ui.js"></script>
<script src="js/jquery-ui-datepicker-th.js"></script>
<script src="js/jquery-ui-1.9.0.custom.js"></script>
<script src="js/locale.js"></script>
<script src="js/ckeditor.js"></script>
<script src="js/adapters/jquery.js"></script>
<script src="js/ktm-lib.js"></script>
<script src="js/article-main.js"></script>
</body>
</html>