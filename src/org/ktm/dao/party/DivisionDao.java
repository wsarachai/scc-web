package org.ktm.dao.party;

import java.util.List;
import org.ktm.domain.party.Division;

public interface DivisionDao extends PartyRoleDao {

	public List<Division> findAll();

}
