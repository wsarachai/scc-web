package org.ktm.dao.party;

import java.util.List;
import org.ktm.dao.Dao;
import org.ktm.domain.party.Person;

public interface PersonDao extends Dao {

	public List<Person> findAll();

}
