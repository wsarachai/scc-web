package org.ktm.dao.party;

import java.util.List;
import org.ktm.domain.party.Division;

public class DivisionDaoHibernate extends PartyRoleDaoHibernate implements DivisionDao {

    private static final long serialVersionUID = 1L;

    @Override
    public Class<?> getFeaturedClass() {
        return Division.class;
    }

    @SuppressWarnings("unchecked")
    public List<Division> findAll() {
        return (List<Division>) super.findAll();
    }
}
