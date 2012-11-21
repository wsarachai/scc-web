package org.ktm.dao.party;

import org.ktm.domain.party.Employment;

public interface EmploymentDao extends PartyRelationshipDao {

	Employment findBySupply( Integer divisionId );

	Employment findByClient( Integer divisionId );

}
