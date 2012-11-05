package org.ktm.scc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ktm.dao.KTMEMDaoFactory;
import org.ktm.dao.party.AuthenDao;
import org.ktm.dao.party.DivisionDao;
import org.ktm.dao.party.EmploymentDao;
import org.ktm.dao.party.PersonDao;
import org.ktm.domain.party.Authen;
import org.ktm.domain.party.Division;
import org.ktm.domain.party.Employee;
import org.ktm.domain.party.Employment;
import org.ktm.domain.party.Person;
import org.ktm.exception.KTMException;
import org.ktm.scc.bean.DivisionBean;
import org.ktm.scc.bean.MemberBean;
import org.ktm.scc.bean.PersonBean;
import org.ktm.scc.login.AuthenImpl;
import org.ktm.servlet.ActionForward;
import org.ktm.servlet.CRUDServlet;
import org.ktm.web.bean.FormBean;
import org.ktm.web.tags.Functions;

@WebServlet("/RGB7-backoffice-v4/CRUDMembers")
public class CRUDMemberServlet extends CRUDServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public String getBeanClass() {
        return "org.ktm.scc.bean.MemberBean";
    }

    @SuppressWarnings("unchecked")
    public ActionForward listMember(FormBean form, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AuthenDao authenDao = KTMEMDaoFactory.getInstance().getAuthenDao();
        List<Person> persons = new ArrayList<Person>();
        List<Authen> authens = (List<Authen>) authenDao.findAll();
        Iterator<Authen> it = authens.iterator();
        while (it.hasNext()) {
            Authen authen = it.next();
            if (authen.getParty() != null) {
                if (authen.getParty() instanceof Person) {
                    persons.add((Person) authen.getParty());
                }
            }
        }
        PersonBean bean = (PersonBean) form;
        bean.loadFormCollection(persons);
        return ActionForward.getUri(this, request, "ListMembers.jsp");
    }

    private void getDivisionCollection(MemberBean bean) {
        DivisionDao divisionDao = KTMEMDaoFactory.getInstance().getDivisionDao();
        bean.getDivisionCollection().clear();

        List<Division> divisonList = divisionDao.findAll();
        for (Division division : divisonList) {
            DivisionBean divisionBean = new DivisionBean();
            divisionBean.loadToForm(division);
            bean.getDivisionCollection().add(divisionBean);
        }
    }

    public ActionForward editMember(FormBean form, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MemberBean bean = (MemberBean) form;
        PersonDao personDao = KTMEMDaoFactory.getInstance().getPersonDao();
        Person person = (Person) personDao.get(Integer.parseInt(bean.getUniqueId()));
        if (person != null) {
            bean.loadToForm(person);
            getDivisionCollection(bean);
        }
        return ActionForward.getUri(this, request, "EditMembers.jsp");
    }

    public ActionForward newMember(FormBean form, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MemberBean bean = (MemberBean) form;
        getDivisionCollection(bean);
        return ActionForward.getUri(this, request, "EditMembers.jsp");
    }

    public ActionForward saveMember(FormBean form, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, KTMException {
        MemberBean bean = (MemberBean) form;

        AuthenDao authenDao = KTMEMDaoFactory.getInstance().getAuthenDao();
        PersonDao personDao = KTMEMDaoFactory.getInstance().getPersonDao();
        EmploymentDao empmDao = KTMEMDaoFactory.getInstance().getEmploymentDao();
        DivisionDao divisionDao = KTMEMDaoFactory.getInstance().getDivisionDao();

        Person person = null;

        if (!Functions.isEmpty(bean.getUniqueId())) {
            person = (Person) personDao.get(Integer.parseInt(bean.getUniqueId()));
        } else {
            person = new Person();
        }

        bean.syncToEntity(person);

        Employee employee = bean.getEmployeeRole(person);
        if (employee == null) {
            employee = new Employee();
            person.getRoles().add(employee);
            employee.setParty(person);
        }

        personDao.createOrUpdate(person);

        if (!Functions.isEmpty(bean.getLoginuser()) && !Functions.isEmpty(bean.getLoginpassword())) {
            Authen authen = authenDao.findByPartyId(person.getUniqueId());
            if (authen == null) {
                authen = new Authen();
                authen.setParty(person);
            }
            authen.setUsername(bean.getLoginuser());
            authen.setPassword(AuthenImpl.encode(bean.getLoginuser(), bean.getLoginpassword()));
            authenDao.createOrUpdate(authen);
        }

        Employment empm = empmDao.findByClient(employee.getUniqueId());
        if (empm == null) {
            empm = new Employment();
            empm.setClient(employee);
        }
        if (empm.getSupply() == null || empm.getSupply().getUniqueId() != bean.getDivisionId()) {
            Division division = (Division) divisionDao.get(bean.getDivisionId());
            if (division != null) {
                empm.setSupply(division);
            } else {
                throw new KTMException();
            }
        }
        empmDao.createOrUpdate(empm);

        return ActionForward.getAction(this, request, "CRUDMembers?method=list", true);
    }

    public ActionForward delMember(FormBean form, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, KTMException {
        MemberBean bean = (MemberBean) form;
        AuthenDao authenDao = KTMEMDaoFactory.getInstance().getAuthenDao();
        PersonDao personDao = KTMEMDaoFactory.getInstance().getPersonDao();
        EmploymentDao empmDao = KTMEMDaoFactory.getInstance().getEmploymentDao();
        Person person = (Person) personDao.get(Integer.parseInt(bean.getUniqueId()));
        if (person != null) {
            Authen authen = (Authen) authenDao.findByPartyId(person.getUniqueId());
            if (authen != null) {
                authen.setParty(null);
                authenDao.update(authen);
                authenDao.delete(authen);
            }

            Employee employee = bean.getEmployeeRole(person);
            if (employee != null) {
                Employment empm = empmDao.findByClient(employee.getUniqueId());
                if (empm != null) {
                    empm.setClient(null);
                    empm.setSupply(null);
                    empm.setType(null);
                    empmDao.update(empm);
                    empmDao.delete(empm);
                }
            }

            personDao.delete(person);
        }

        return ActionForward.getAction(this, request, "CRUDMembers?method=list", true);
    }

    @Override
    protected boolean prepareRequest(HttpServletRequest request) throws ServletException, IOException {
        super.prepareRequest(request);
        return true;
    }

}
