package org.ktm.dao.party;

import org.ktm.dao.AbstractHibernateStorageDao;
import org.ktm.domain.party.TelephoneAddress;

public class TelephoneAddressDaoHibernate extends AbstractHibernateStorageDao
	implements TelephoneAddressDao {

	private static final long	serialVersionUID	= 7584799141117100983L;

	@Override
	public Class<?> getFeaturedClass() {
		return TelephoneAddress.class;
	}

}
