package org.ktm.dao;

import org.ktm.dao.article.ArticleDao;
import org.ktm.dao.article.ArticleDaoHibernate;
import org.ktm.dao.article.ArticleTypeDao;
import org.ktm.dao.article.ArticleTypeDaoHibernate;
import org.ktm.dao.gallery.ImageDao;
import org.ktm.dao.gallery.ImageDaoHibernate;
import org.ktm.dao.party.AuthenDao;
import org.ktm.dao.party.AuthenDaoHibernate;
import org.ktm.dao.party.CustomerDao;
import org.ktm.dao.party.CustomerDaoHibernate;
import org.ktm.dao.party.DivisionDao;
import org.ktm.dao.party.DivisionDaoHibernate;
import org.ktm.dao.party.EmployeeDao;
import org.ktm.dao.party.EmployeeDaoHibernate;
import org.ktm.dao.party.EmploymentDao;
import org.ktm.dao.party.EmploymentDaoHibernate;
import org.ktm.dao.party.OrganizationDao;
import org.ktm.dao.party.OrganizationDaoHibernate;
import org.ktm.dao.party.PartyDao;
import org.ktm.dao.party.PartyDaoHibernate;
import org.ktm.dao.party.PartyRoleDao;
import org.ktm.dao.party.PartyRoleDaoHibernate;
import org.ktm.dao.party.PartyRoleTypeDao;
import org.ktm.dao.party.PartyRoleTypeDaoHibernate;
import org.ktm.dao.party.PersonDao;
import org.ktm.dao.party.PersonDaoHibernate;
import org.ktm.dao.party.SupplierDao;
import org.ktm.dao.party.SupplierDaoHibernate;

public class KTMEMDaoFactoryHibernate extends KTMEMDaoFactory {

    @Override
    public AuthenDao getAuthenDao() {
        return new AuthenDaoHibernate();
    }

    @Override
    public PersonDao getPersonDao() {
        return new PersonDaoHibernate();
    }

    @Override
    public PartyRoleDao getPartyRoleDao() {
        return new PartyRoleDaoHibernate();
    }

    @Override
    public SupplierDao getSupplierDao() {
        return new SupplierDaoHibernate();
    }

    @Override
    public OrganizationDao getOrganizationDao() {
        return new OrganizationDaoHibernate();
    }

    @Override
    public CustomerDao getCustomerDao() {
        return new CustomerDaoHibernate();
    }

    @Override
    public EmploymentDao getEmploymentDao() {
        return new EmploymentDaoHibernate();
    }

    @Override
    public PartyDao getPartyDao() {
        return new PartyDaoHibernate();
    }

    @Override
    public PartyRoleTypeDao getPartyRoleTypeDao() {
        return new PartyRoleTypeDaoHibernate();
    }

    @Override
    public DivisionDao getDivisionDao() {
        return new DivisionDaoHibernate();
    }

    @Override
    public EmployeeDao getEmployeeDao() {
        return new EmployeeDaoHibernate();
    }

    @Override
    public ArticleDao getArticleDao() {
        return new ArticleDaoHibernate();
    }

    @Override
    public ArticleTypeDao getArticleTypeDao() {
        return new ArticleTypeDaoHibernate();
    }

    @Override
    public ImageDao getImageDao() {
        return new ImageDaoHibernate();
    }

}
