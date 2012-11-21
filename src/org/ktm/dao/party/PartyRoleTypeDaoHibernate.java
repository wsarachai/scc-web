package org.ktm.dao.party;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.ktm.dao.AbstractHibernateStorageDao;
import org.ktm.domain.party.PartyRoleType;

public class PartyRoleTypeDaoHibernate extends AbstractHibernateStorageDao
	implements PartyRoleTypeDao {

	private static final long	serialVersionUID	= 1L;

	public Class<?> getFeaturedClass() {
		return PartyRoleType.class;
	}

	@SuppressWarnings( "unchecked" )
	public List<PartyRoleType> findAll() {
		return (List<PartyRoleType>) super.findAll();
	}

	@Override
	public PartyRoleType findByName( String name ) {
		PartyRoleType result = null;
		String queryString = "select partyRoleType FROM PartyRoleType AS partyRoleType WHERE partyRoleType.name = :name";

		try {
			Query query = getCurrentSession().createQuery( queryString );
			query.setParameter( "name", name );

			query.setFirstResult( getFirstResult() );
			if ( getMaxResults() < QUERY_MAX_RESULTS_DEFAULT ) {
				query.setMaxResults( getMaxResults() );
			}

			for ( Iterator<?> objectIt = query.list().iterator(); objectIt.hasNext(); ) {
				Object object = objectIt.next();

				if ( object instanceof PartyRoleType ) {
					result = (PartyRoleType) object;
					break;
				} else if ( object instanceof Collection ) {
					Collection<?> subList = (Collection<?>) object;
					for ( Object listObject : subList ) {
						if ( listObject instanceof PartyRoleType ) {
							result = (PartyRoleType) object;
							break;
						}
					}
				}
			}
		}
		catch ( HibernateException he ) {
			he.printStackTrace();
		}
		return result;
	}

}
