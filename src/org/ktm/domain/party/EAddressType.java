package org.ktm.domain.party;

public enum EAddressType {
	EMAIL( "email" ),
	TELEPHONE( "phone" ),
	FAX( "fax" ),
	GEOGRAPHICS( "geographics" ),
	WEBPAGE( "web" );

	private String	type;

	private EAddressType( String type ) {
		this.setType( type );
	}

	public String getType() {
		return type;
	}

	public void setType( String type ) {
		this.type = type;
	}

	public boolean equals( String type ) {
		return this.type.equals( type );
	}

	public String toString() {
		return this.type;
	}

	public static EAddressType parse( String parseValue ) {
		for ( int i = 0; i < values().length; i++ ) {
			if ( parseValue != null && parseValue.equals( values()[ i ].toString() ) ) {
				return values()[ i ];
			}
		}
		return null;
	}
}
