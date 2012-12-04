package org.ktm.dao.gallery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.ktm.core.KTMContext;
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
	public Collection<?> find( int pageNumber ) {
		List<Gallery> result = new ArrayList<Gallery>();
		String queryString = "select gallery FROM Gallery AS gallery ORDER BY gallery.dateCreated DESC, gallery.uniqueId DESC";

		try {
			Query query = getCurrentSession().createQuery( queryString );

			if ( KTMContext.paging > 0 ) {
				setMaxResults( KTMContext.paging );
				setFirstResult( getMaxResults() * ( pageNumber - 1 ) );
			}

			query.setFirstResult( getFirstResult() );
			if ( getMaxResults() < QUERY_MAX_RESULTS_DEFAULT ) {
				query.setMaxResults( getMaxResults() );
			}

			for ( Iterator<?> objectIt = query.list().iterator(); objectIt.hasNext(); ) {
				Object object = objectIt.next();

				if ( object instanceof Gallery ) {
					result.add( (Gallery) object );
				} else if ( object instanceof Collection ) {
					Collection<?> subList = (Collection<?>) object;
					for ( Object listObject : subList ) {
						if ( listObject instanceof Gallery ) {
							result.add( (Gallery) listObject );
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

	@Override
	public Collection<?> findByDivision( Integer division_id, int pageNumber ) {
		List<Gallery> result = new ArrayList<Gallery>();
		String queryString = "select gallery FROM Gallery AS gallery WHERE gallery.author.uniqueId = :division_id ORDER BY gallery.dateCreated DESC, gallery.uniqueId DESC";

		try {
			Query query = getCurrentSession().createQuery( queryString );
			query.setParameter( "division_id", division_id );

			if ( KTMContext.paging > 0 ) {
				setMaxResults( KTMContext.paging );
				setFirstResult( getMaxResults() * ( pageNumber - 1 ) );
			}

			query.setFirstResult( getFirstResult() );
			if ( getMaxResults() < QUERY_MAX_RESULTS_DEFAULT ) {
				query.setMaxResults( getMaxResults() );
			}

			for ( Iterator<?> objectIt = query.list().iterator(); objectIt.hasNext(); ) {
				Object object = objectIt.next();

				if ( object instanceof Gallery ) {
					result.add( (Gallery) object );
				} else if ( object instanceof Collection ) {
					Collection<?> subList = (Collection<?>) object;
					for ( Object listObject : subList ) {
						if ( listObject instanceof Gallery ) {
							result.add( (Gallery) listObject );
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

	@Override
	public long getCountByDivision( Integer division_id ) {
		try {
			Query query = getCurrentSession().createQuery( "SELECT count(*) FROM Gallery as gallery WHERE gallery.author.uniqueId = :division_id" );

			query.setParameter( "division_id", division_id );

			return ( (Long) query.uniqueResult() ).longValue();
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}

		return -1l;
	}

}
