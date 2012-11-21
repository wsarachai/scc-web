package org.ktm.dao.party;

import org.ktm.dao.AbstractHibernateStorageDao;
import org.ktm.domain.party.RegisteredIdentifier;

public class RegisteredIdentifierDaoHibernate extends
	AbstractHibernateStorageDao implements RegisteredIdentifierDao {

	private static final long	serialVersionUID	= 5204963426613650477L;

	@Override
	public Class<?> getFeaturedClass() {
		return RegisteredIdentifier.class;
	}

}
