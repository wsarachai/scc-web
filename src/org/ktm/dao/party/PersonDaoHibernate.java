package org.ktm.dao.party;

import java.io.Serializable;
import java.util.List;
import org.ktm.dao.KTMEMDaoFactory;
import org.ktm.domain.party.Authen;
import org.ktm.domain.party.Person;
import org.ktm.exception.DeleteException;

public class PersonDaoHibernate extends PartyDaoHibernate implements PersonDao {

	private static final long	serialVersionUID	= 1L;

	@Override
	public Class<?> getFeaturedClass() {
		return Person.class;
	}

	@Override
	@SuppressWarnings( "unchecked" )
	public List<Person> findAll() {
		return (List<Person>) super.findAll();
	}

	@Override
	public int delete( Serializable id ) throws DeleteException {
		if ( getFeaturedClass() != null && id != null ) {
			AuthenDao authDao = KTMEMDaoFactory.getInstance().getAuthenDao();
			Authen auth = authDao.findByPartyId( (Integer) id );
			authDao.delete( auth );

			return super.delete( id );
		}
		return -1;
	}
}
