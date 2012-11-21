package org.ktm.dao.article;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.ktm.core.KTMContext;
import org.ktm.dao.AbstractHibernateStorageDao;
import org.ktm.domain.KTMEntity;
import org.ktm.domain.article.Article;

public class ArticleDaoHibernate extends AbstractHibernateStorageDao implements
	ArticleDao {

	private static final long	serialVersionUID	= 1L;

	@Override
	public Class<?> getFeaturedClass() {
		return Article.class;
	}

	@SuppressWarnings( "unchecked" )
	public Collection<KTMEntity> findAll() {
		return (Collection<KTMEntity>) super.findAll();
	}

	@Override
	public Collection<?> find( int pageNumber ) {
		List<Article> result = new ArrayList<Article>();
		String queryString = "select article FROM Article AS article ORDER BY article.dateCreated, article.uniqueId DESC";

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

				if ( object instanceof Article ) {
					result.add( (Article) object );
				} else if ( object instanceof Collection ) {
					Collection<?> subList = (Collection<?>) object;
					for ( Object listObject : subList ) {
						if ( listObject instanceof Article ) {
							result.add( (Article) listObject );
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
	public Article findByIdentifier( String identifier ) {
		Article result = null;
		String queryString = "select article FROM Article AS article WHERE article.identifier = :identifier";

		try {
			Query query = getCurrentSession().createQuery( queryString );
			query.setParameter( "identifier", identifier );

			query.setFirstResult( getFirstResult() );
			if ( getMaxResults() < QUERY_MAX_RESULTS_DEFAULT ) {
				query.setMaxResults( getMaxResults() );
			}

			for ( Iterator<?> objectIt = query.list().iterator(); objectIt.hasNext(); ) {
				Object object = objectIt.next();

				if ( object instanceof Article ) {
					result = (Article) object;
					break;
				} else if ( object instanceof Collection ) {
					Collection<?> subList = (Collection<?>) object;
					for ( Object listObject : subList ) {
						if ( listObject instanceof Article ) {
							result = (Article) listObject;
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
		List<Article> result = new ArrayList<Article>();
		String queryString = "select article FROM Article AS article WHERE article.author.uniqueId = :division_id ORDER BY article.dateCreated, article.uniqueId DESC";

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

				if ( object instanceof Article ) {
					result.add( (Article) object );
				} else if ( object instanceof Collection ) {
					Collection<?> subList = (Collection<?>) object;
					for ( Object listObject : subList ) {
						if ( listObject instanceof Article ) {
							result.add( (Article) listObject );
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
			Query query = getCurrentSession().createQuery( "SELECT count(*) FROM Article as article WHERE article.author.uniqueId = :division_id" );

			query.setParameter( "division_id", division_id );

			return ( (Long) query.uniqueResult() ).longValue();
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}

		return -1l;
	}
}
