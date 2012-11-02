$(function () {
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

  $( "#datepicker" ).datepicker();
  var date_created = $("#dateCreated");
  $.datepicker.setDefaults($.datepicker.regional["th"]);
  date_created.datepicker({
    showOn : "button",
    dateFormat: "dd-mm-yy",
    buttonImage : "img/calendar.gif",
    buttonImageOnly : true,
    changeMonth : true,
    changeYear : true,
    showButtonPanel : true
  });
  date_created.datepicker('setDate', new Date());
  bkLib.onDomLoaded(function() {
      new nicEditor().panelInstance('content');
  });
});