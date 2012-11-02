package org.ktm.dao.party;

import java.util.List;
import org.ktm.domain.party.Employee;

public class EmployeeDaoHibernate extends PartyRoleDaoHibernate implements EmployeeDao {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unchecked")
    public List<Employee> findAll() {
        return (List<Employee>) super.findAll();
    }

}
