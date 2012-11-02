package org.ktm.dao.party;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.ktm.dao.AbstractHibernateStorageDao;
import org.ktm.domain.party.Authen;
import org.ktm.domain.party.Party;
import org.ktm.domain.party.PartyRole;
import org.ktm.utils.Localizer;

public class PartyRoleDaoHibernate extends AbstractHibernateStorageDao implements PartyRoleDao {

    private static final long serialVersionUID = 1L;

    @Override
    public Class<?> getFeaturedClass() {
        return Authen.class;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public PartyRole findByRoleName(Party party, String roleName) {
        PartyRole result = null;

        try {
            String queryString = "select partyrole FROM PartyRole AS partyrole WHERE partyrole.name = :rolename AND partyrole.party.uniqueId=:partyid";

            Query query = getCurrentSession().createQuery(queryString);
            query.setParameter("partyid", party.getUniqueId());
            query.setParameter("rolename", roleName);

            query.setFirstResult(getFirstResult());

            if (getMaxResults() < QUERY_MAX_RESULTS_DEFAULT) {
                query.setMaxResults(getMaxResults());
            }

            for (Iterator objectIt = query.list().iterator(); objectIt.hasNext();) {
                Object object = objectIt.next();

                if (object instanceof PartyRole) {
                    result = (PartyRole) object;
                } else if (object instanceof Collection) {
                    Collection subList = (Collection) object;
                    for (Object listObject : subList) {
                        if (listObject instanceof PartyRole) {
                            result = (PartyRole) object;
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
    public Set<PartyRole> findByParty(Party party) {
        Set<PartyRole> result = new HashSet<PartyRole>();
        try {
            String queryString = "select role FROM PartyRole AS role WHERE role.party.uniqueId = :id";

            try {
                Query query = getCurrentSession().createQuery(queryString);
                query.setParameter("id", party.getUniqueId());

                query.setFirstResult(0);
                query.setMaxResults(Integer.MAX_VALUE);
                Iterator<?> objectIt = query.iterate();

                while (objectIt.hasNext()) {
                    Object object = objectIt.next();

                    if (object instanceof PartyRole) {
                        result.add((PartyRole) object);
                    } else if (object instanceof Collection) {
                        Collection<?> subList = (Collection<?>) object;
                        for (Object subObject : subList) {
                            if (subObject instanceof PartyRole) {
                                result.add((PartyRole) subObject);
                            }
                        }
                    }
                }
            } catch (HibernateException e) {
                e.printStackTrace();
            }

        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return result;
    }

    @Override
    public Vector<String> findByPartyString(Party party) {
        Vector<String> v = new Vector<String>();
        Set<PartyRole> lst = findByParty(party);
        if (lst.size() > 0) {
            for (PartyRole pr : lst) {
                String roleName = Localizer.getClassName(pr.getClass());
                if (roleName != null) {
                    if (!v.contains(roleName)) {
                        v.add(roleName);
                    }
                }
            }
        }
        return v;
    }
}
