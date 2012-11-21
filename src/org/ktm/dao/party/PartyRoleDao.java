package org.ktm.dao.party;

import java.util.Set;
import java.util.Vector;
import org.ktm.dao.Dao;
import org.ktm.domain.party.Party;
import org.ktm.domain.party.PartyRole;

public interface PartyRoleDao extends Dao {

	public PartyRole findByRoleName( Party party, String roleName );

	public Set<PartyRole> findByParty( Party party );

	public Vector<String> findByPartyString( Party party );

}
