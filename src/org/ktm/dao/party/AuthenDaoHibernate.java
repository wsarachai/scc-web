package org.ktm.dao.party;

import java.util.Collection;
import java.util.Iterator;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.ktm.dao.AbstractHibernateStorageDao;
import org.ktm.domain.party.Authen;

public class AuthenDaoHibernate extends AbstractHibernateStorageDao implements AuthenDao {

    private static final long serialVersionUID = 1L;

    @Override
    public Class<?> getFeaturedClass() {
        return Authen.class;
    }

    @Override
    public Authen findByUsername(String username) {
        Authen result = null;
        String queryString = "select authen FROM Authen AS authen WHERE authen.username = :username";

        try {
            Query query = getCurrentSession().createQuery(queryString);
            query.setParameter("username", username);

            query.setFirstResult(getFirstResult());
            if (getMaxResults() < QUERY_MAX_RESULTS_DEFAULT) {
                query.setMaxResults(getMaxResults());
            }

            for (Iterator<?> objectIt = query.list().iterator(); objectIt.hasNext();) {
                Object object = objectIt.next();

                if (object instanceof Authen) {
                    result = (Authen) object;
                    break;
                } else if (object instanceof Collection) {
                    Collection<?> subList = (Collection<?>) object;
                    for (Object listObject : subList) {
                        if (listObject instanceof Authen) {
                            result = (Authen) listObject;
                            break;
                        }
                    }
                }
            }
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return result;
    }

    @Override
    public Authen findByPartyId(Integer id) {
        Authen result = null;

        try {
            String queryString = "select new list(authen) " + "FROM Authen AS authen " + "WHERE authen.party.uniqueId = :partyId";

            Query query = getCurrentSession().createQuery(queryString);
            query.setParameter("partyId", id);

            query.setFirstResult(getFirstResult());

            if (getMaxResults() < QUERY_MAX_RESULTS_DEFAULT) {
                query.setMaxResults(getMaxResults());
            }

            for (Iterator<?> objectIt = query.list().iterator(); objectIt.hasNext();) {
                Object object = objectIt.next();

                if (object instanceof Authen) {
                    result = (Authen) object;
                    break;
                } else if (object instanceof Collection) {
                    Collection<?> subList = (Collection<?>) object;
                    for (Object listObject : subList) {
                        if (listObject instanceof Authen) {
                            result = (Authen) listObject;
                            break;
                        }
                    }
                    break;
                }
            }
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return result;
    }

}
