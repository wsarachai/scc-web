package org.ktm.dao.party;

import java.util.List;
import org.ktm.domain.party.Employee;

public interface EmployeeDao extends PartyRoleDao {

    public List<Employee> findAll();
}
