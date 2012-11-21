package org.ktm.dao.party;

import java.util.Collection;
import org.ktm.dao.Dao;
import org.ktm.domain.party.Supplier;

public interface SupplierDao extends Dao {

	@Override
	public Collection<Supplier> findAll();

	public Supplier findByPartyIdentifier( String partyIdentifier );

}
