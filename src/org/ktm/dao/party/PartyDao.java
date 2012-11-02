package org.ktm.dao.party;

import org.ktm.dao.Dao;
import org.ktm.domain.party.Party;

public interface PartyDao extends Dao {

    Party findByIdentifier(String supplierId);

}
