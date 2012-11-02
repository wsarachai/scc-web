package org.ktm.dao.party;

import java.util.List;
import org.ktm.dao.Dao;
import org.ktm.domain.party.PartyRoleType;

public interface PartyRoleTypeDao extends Dao {

    public List<PartyRoleType> findAll();

    public PartyRoleType findByName(String name);

}
