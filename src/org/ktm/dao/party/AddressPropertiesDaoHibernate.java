package org.ktm.dao.party;

import org.ktm.dao.AbstractHibernateStorageDao;
import org.ktm.domain.party.AddressProperties;

public class AddressPropertiesDaoHibernate extends AbstractHibernateStorageDao
	implements AddressPropertiesDao {

	private static final long	serialVersionUID	= 1L;

	@Override
	public Class<?> getFeaturedClass() {
		return AddressProperties.class;
	}

}
