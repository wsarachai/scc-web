//
function goTo( url ) {
	window.location.href = url;
}
function checkLength( o, n, min, max ) {
	if ( o.val().length > max || o.val().length < min ) {
		o.parent().parent( ".control-group" ).addClass( "error" );
		o.focus();
		$( ".alert-heading" ).text( n );
		return false;
	}
	return true;
}
function checkRegexp( o, regexp, n ) {
	if ( !( regexp.test( o.val() ) ) ) {
		o.parent().parent( ".control-group" ).addClass( "error" );
		o.focus();
		$( ".alert-heading" ).text( n );
		return false;
	}
	return true;
}
function checkSelect( o, n, regexp ) {
	var bValid = true;
	var value = o.val();
	if ( value ) {
		$.each( regexp.valueNotEquals, function( i, item ) {
			if ( o.val() == item ) {
				bValid = false;
				o.parent().parent( ".control-group" ).addClass( "error" );
				$( ".alert-heading" ).text( n );
			}
		} );
	}
	return bValid;
}
