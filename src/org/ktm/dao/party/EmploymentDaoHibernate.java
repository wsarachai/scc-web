package org.ktm.dao.party;

import java.util.Collection;
import java.util.Iterator;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.ktm.domain.party.Employment;

public class EmploymentDaoHibernate extends PartyRelationshipDaoHibernate implements EmploymentDao {

    private static final long serialVersionUID = 1L;

    @Override
    public Class<?> getFeaturedClass() {
        return Employment.class;
    }

    @Override
    public Employment findBySupply(Integer supplyId) {
        Employment result = null;

        try {
            String queryString = "select new list(empm) " + "FROM Employment AS empm " + "WHERE empm.supply.uniqueId = :supplyId";

            Query query = getCurrentSession().createQuery(queryString);
            query.setParameter("supplyId", supplyId);

            query.setFirstResult(getFirstResult());

            if (getMaxResults() < QUERY_MAX_RESULTS_DEFAULT) {
                query.setMaxResults(getMaxResults());
            }

            for (Iterator<?> objectIt = query.list().iterator(); objectIt.hasNext();) {
                Object object = objectIt.next();

                if (object instanceof Employment) {
                    result = (Employment) object;
                    break;
                } else if (object instanceof Collection) {
                    Collection<?> subList = (Collection<?>) object;
                    for (Object listObject : subList) {
                        if (listObject instanceof Employment) {
                            result = (Employment) listObject;
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

    @Override
    public Employment findByClient(Integer clientId) {
        Employment result = null;

        try {
            String queryString = "select new list(empm) " + "FROM Employment AS empm " + "WHERE empm.client.uniqueId = :clientId";

            Query query = getCurrentSession().createQuery(queryString);
            query.setParameter("clientId", clientId);

            query.setFirstResult(getFirstResult());

            if (getMaxResults() < QUERY_MAX_RESULTS_DEFAULT) {
                query.setMaxResults(getMaxResults());
            }

            for (Iterator<?> objectIt = query.list().iterator(); objectIt.hasNext();) {
                Object object = objectIt.next();

                if (object instanceof Employment) {
                    result = (Employment) object;
                    break;
                } else if (object instanceof Collection) {
                    Collection<?> subList = (Collection<?>) object;
                    for (Object listObject : subList) {
                        if (listObject instanceof Employment) {
                            result = (Employment) listObject;
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
