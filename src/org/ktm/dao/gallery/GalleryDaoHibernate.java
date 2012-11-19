package org.ktm.dao.gallery;

import java.util.Collection;
import java.util.Iterator;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.ktm.dao.AbstractHibernateStorageDao;
import org.ktm.domain.gallery.Gallery;

public class GalleryDaoHibernate extends AbstractHibernateStorageDao implements
	GalleryDao {

	private static final long	serialVersionUID	= 1L;

	@Override
	public Class<?> getFeaturedClass() {
		return Gallery.class;
	}

	@Override
	public Gallery findByIdentifier( String identifier ) {
		Gallery result = null;
		String queryString = "select gallery FROM Gallery AS gallery WHERE gallery.identifier = :identifier";

		try {
			Query query = getCurrentSession().createQuery( queryString );
			query.setParameter( "identifier", identifier );

			query.setFirstResult( getFirstResult() );
			if ( getMaxResults() < QUERY_MAX_RESULTS_DEFAULT ) {
				query.setMaxResults( getMaxResults() );
			}

			for ( Iterator<?> objectIt = query.list().iterator(); objectIt.hasNext(); ) {
				Object object = objectIt.next();

				if ( object instanceof Gallery ) {
					result = (Gallery) object;
					break;
				} else if ( object instanceof Collection ) {
					Collection<?> subList = (Collection<?>) object;
					for ( Object listObject : subList ) {
						if ( listObject instanceof Gallery ) {
							result = (Gallery) listObject;
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
