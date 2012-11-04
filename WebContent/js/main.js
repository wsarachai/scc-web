$(function () {
  var content = "";
  var btnSave = $("#btnSave");
  var title = $("#title");
  var articleTypeId = $("#articleTypeId");
  $('#fileupload').fileupload();
  $('#fileupload').fileupload(
      'option',
      'redirect',
      window.location.href.replace(
          /\/[^\/]*$/,
          '/cors/result.html?%s'
      )
  );
  $('#fileupload').fileupload('option', {
      url: 'upload',
      maxFileSize: 5000000,
      acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
      process: [
          {
              action: 'load',
              fileTypes: /^image\/(gif|jpeg|png)$/,
              maxFileSize: 20000000 // 20MB
          },
          {
              action: 'resize',
              maxWidth: 1440,
              maxHeight: 900
          },
          {
              action: 'save'
          }
      ]
  });
  $('#fileupload')
  .bind('fileuploaddestroy', function (e, data) { /*alert('destroy');*/ })
  .bind('fileuploaddestroyed', function (e, data) { /*alert('destroyed');*/ })
  .bind('fileuploadadded', function (e, data) { /*data.submit();*/ })
  .bind('fileuploadsent', function (e, data) { /*alert('sent');*/ })
  .bind('fileuploadcompleted', function (e, data) {
    //alert('completed: ' + data.files[0].name);
   })
  .bind('fileuploadfailed', function (e, data) { /*alert('failed');*/ })
  .bind('fileuploadstarted', function (e) { /*alert('started');*/ })
  .bind('fileuploadstopped', function (e) { /*alert('stoped');*/ });
  // Upload server status check for browsers with CORS support:
  if ($.support.cors) {
      $.ajax({
          url: 'upload',
          type: 'HEAD'
      }).fail(function () {
          $('<span class="alert alert-error"/>')
              .text('Upload server currently unavailable - ' +
                      new Date())
              .appendTo('#fileupload');
      });
  }
  articleTypeId.change(function() {
    btnSave.attr("disabled", false); 
  });
  title.change(function() {
    btnSave.attr("disabled", false); 
  });
  $('#content').ckeditor();
  CKEDITOR.instances['content'].on('blur', function(e) {
      if (e.editor.checkDirty()) {
        btnSave.attr("disabled", false);
      }
  });
  btnSave.click(function(){
    //var e = encodeURIComponent($("#content").val());
    //alert(e);
    //e = decodeURIComponent(e);
    //alert(e);
    $.post("CRUDArticle", $("#frmContent").serialize(),
        function(data) {
          alert("Data Loaded: " + data);
        });
    btnSave.attr("disabled", true); 
  });
});