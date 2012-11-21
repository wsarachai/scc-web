package org.ktm.dao.party;

import java.util.Collection;
import java.util.Iterator;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.ktm.dao.AbstractHibernateStorageDao;
import org.ktm.domain.party.Party;

public class PartyDaoHibernate extends AbstractHibernateStorageDao implements
	PartyDao {

	private static final long	serialVersionUID	= 1L;

	@Override
	public Class<?> getFeaturedClass() {
		return Party.class;
	}

	@Override
	public Party findByIdentifier( String identifier ) {
		Party result = null;
		String queryString = "select party FROM Party AS party WHERE party.identifier.identifier = :identifier";

		try {
			Query query = getCurrentSession().createQuery( queryString );
			query.setParameter( "identifier", identifier );

			query.setFirstResult( getFirstResult() );
			if ( getMaxResults() < QUERY_MAX_RESULTS_DEFAULT ) {
				query.setMaxResults( getMaxResults() );
			}

			for ( Iterator<?> objectIt = query.list().iterator(); objectIt.hasNext(); ) {
				Object object = objectIt.next();

				if ( object instanceof Party ) {
					result = (Party) object;
					break;
				} else if ( object instanceof Collection ) {
					Collection<?> subList = (Collection<?>) object;
					for ( Object listObject : subList ) {
						if ( listObject instanceof Party ) {
							result = (Party) listObject;
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
