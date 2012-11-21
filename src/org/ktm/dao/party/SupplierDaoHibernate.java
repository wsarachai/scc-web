package org.ktm.dao.party;

import java.util.Collection;
import java.util.Iterator;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.ktm.dao.AbstractHibernateStorageDao;
import org.ktm.domain.party.Supplier;

public class SupplierDaoHibernate extends AbstractHibernateStorageDao implements
	SupplierDao {

	private static final long	serialVersionUID	= 1L;

	@Override
	public Class<?> getFeaturedClass() {
		return Supplier.class;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public Collection<Supplier> findAll() {
		return (Collection<Supplier>) super.findAll();
	}

	@Override
	public Supplier findByPartyIdentifier( String identifier ) {
		Supplier result = null;
		String queryString = "select supplier FROM Supplier AS supplier WHERE supplier.party.identifier.identifier = :identifier";

		try {
			Query query = getCurrentSession().createQuery( queryString );
			query.setParameter( "identifier", identifier );

			query.setFirstResult( getFirstResult() );
			if ( getMaxResults() < QUERY_MAX_RESULTS_DEFAULT ) {
				query.setMaxResults( getMaxResults() );
			}

			for ( Iterator<?> objectIt = query.list().iterator(); objectIt.hasNext(); ) {
				Object object = objectIt.next();

				if ( object instanceof Supplier ) {
					result = (Supplier) object;
					break;
				} else if ( object instanceof Collection ) {
					Collection<?> subList = (Collection<?>) object;
					for ( Object listObject : subList ) {
						if ( listObject instanceof Supplier ) {
							result = (Supplier) listObject;
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
