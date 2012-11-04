package org.ktm.dao;

import org.ktm.dao.article.ArticleDao;
import org.ktm.dao.article.ArticleTypeDao;
import org.ktm.dao.gallery.ImageDao;
import org.ktm.dao.party.AuthenDao;
import org.ktm.dao.party.CustomerDao;
import org.ktm.dao.party.DivisionDao;
import org.ktm.dao.party.EmployeeDao;
import org.ktm.dao.party.EmploymentDao;
import org.ktm.dao.party.OrganizationDao;
import org.ktm.dao.party.PartyRoleDao;
import org.ktm.dao.party.PartyRoleTypeDao;
import org.ktm.dao.party.PersonDao;
import org.ktm.dao.party.SupplierDao;

public abstract class KTMEMDaoFactory {

    public static final KTMEMDaoFactory HIBERNATE = new KTMEMDaoFactoryHibernate();

    public static final KTMEMDaoFactory DEFAULT   = HIBERNATE;

    public static KTMEMDaoFactory getInstance() {
        return DEFAULT;
    }

    public abstract AuthenDao getAuthenDao();

    public abstract PersonDao getPersonDao();

    public abstract PartyRoleDao getPartyRoleDao();

    public abstract SupplierDao getSupplierDao();

    public abstract OrganizationDao getOrganizationDao();

    public abstract CustomerDao getCustomerDao();

    public abstract EmploymentDao getEmploymentDao();

    public abstract org.ktm.dao.party.PartyDao getPartyDao();

    public abstract PartyRoleTypeDao getPartyRoleTypeDao();

    public abstract DivisionDao getDivisionDao();

    public abstract EmployeeDao getEmployeeDao();

    public abstract ArticleDao getArticleDao();

    public abstract ArticleTypeDao getArticleTypeDao();

    public abstract ImageDao getImageDao();

}
