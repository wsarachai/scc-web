$( function() {
	tips = $( ".validateTips" );
	var btnSave = $( "#btnSave" );
	$( '#fileupload' ).fileupload();
	$( '#fileupload' ).fileupload(	'option',
																	'redirect',
																	window.location.href
																			.replace( /\/[^\/]*$/,
																								'/cors/result.html?%s' ) );
	$( '#fileupload' ).fileupload( 'option', {
		url: 'upload_image_article',
		maxFileSize: 5000000,
		acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
		process: [
				{
					action: 'load',
					fileTypes: /^image\/(gif|jpeg|png)$/,
					maxFileSize: 20000000
				// 20MB
				}, {
					action: 'resize',
					maxWidth: 1440,
					maxHeight: 900
				}, {
					action: 'save'
				}
		]
	} );
	$( '#fileupload' ).bind( 'fileuploaddestroy', function( e, data ) { /* alert('destroy'); */
	} ).bind( 'fileuploaddestroyed', function( e, data ) { /* alert('destroyed'); */
	} ).bind( 'fileuploadadded', function( e, data ) { /* data.submit(); */
	} ).bind( 'fileuploadsent', function( e, data ) { /* alert('sent'); */
	} ).bind( 'fileuploadcompleted', function( e, data ) {
		// alert('completed: ' + data.files[0].name);
	} ).bind( 'fileuploadfailed', function( e, data ) { /* alert('failed'); */
	} ).bind( 'fileuploadstarted', function( e ) { /* alert('started'); */
	} ).bind( 'fileuploadstopped', function( e ) { /* alert('stoped'); */
	} );
	$( '#content' ).ckeditor();
	btnSave.click( function() {
		$( "#frmContent" ).submit();
	} );
} );
