package org.ktm.dao.party;

import org.ktm.dao.AbstractHibernateStorageDao;
import org.ktm.domain.party.EmailAddress;

public class EmailAddressDaoHibernate extends AbstractHibernateStorageDao
	implements EmailAddressDao {

	private static final long	serialVersionUID	= 1L;

	@Override
	public Class<?> getFeaturedClass() {
		return EmailAddress.class;
	}

}
