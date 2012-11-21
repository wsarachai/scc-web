package org.ktm.dao.party;

import java.util.Collection;
import org.ktm.dao.Dao;
import org.ktm.domain.party.Customer;

public interface CustomerDao extends Dao {

	@Override
	public Collection<Customer> findAll();

}
