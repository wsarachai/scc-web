package org.ktm.dao.party;

import org.ktm.dao.AbstractHibernateStorageDao;
import org.ktm.domain.party.PartyRelationship;

public class PartyRelationshipDaoHibernate extends AbstractHibernateStorageDao
	implements PartyRelationshipDao {

	private static final long	serialVersionUID	= 1L;

	@Override
	public Class<?> getFeaturedClass() {
		return PartyRelationship.class;
	}

}
