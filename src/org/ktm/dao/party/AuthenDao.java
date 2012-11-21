package org.ktm.dao.party;

import org.ktm.dao.Dao;
import org.ktm.domain.party.Authen;

public interface AuthenDao extends Dao {

	public Authen findByUsername( String username );

	public Authen findByPartyId( Integer id );

}
