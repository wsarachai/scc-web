package org.ktm.scc.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.ktm.dao.KTMEMDaoFactory;
import org.ktm.dao.party.DivisionDao;
import org.ktm.dao.party.EmploymentDao;
import org.ktm.domain.KTMEntity;
import org.ktm.domain.party.Division;
import org.ktm.domain.party.Employee;
import org.ktm.domain.party.Employment;
import org.ktm.domain.party.Organization;
import org.ktm.domain.party.Party;
import org.ktm.domain.party.PartyRole;
import org.ktm.domain.party.Person;

public class MemberBean extends PersonBean {

	private Integer				divisionId;
	private String				divisionName;
	private List<DivisionBean>	divisionCollection	= new ArrayList<DivisionBean>();

	public Integer getDivisionId() {
		return divisionId;
	}

	public void setDivisionId( Integer divisionId ) {
		this.divisionId = divisionId;
	}

	public Employee getEmployeeRole( Party party ) {
		if ( party != null ) {
			Set<PartyRole> roles = party.getRoles();
			if ( roles != null && roles.size() > 0 ) {
				for ( PartyRole role : roles ) {
					if ( role instanceof Employee ) {
						return (Employee) role;
					}
				}
			}
		}
		return null;
	}

	@Override
	public void loadFormCollection( Collection<?> entitys ) {
		if ( entitys != null ) {
			getFormCollection().clear();

			for ( Object entity : entitys ) {
				if ( entity instanceof Person ) {
					MemberBean bean = new MemberBean();
					bean.loadToForm( (Person) entity );
					getFormCollection().add( bean );
				}
			}
		}
	}

	@Override
	public void loadToForm( KTMEntity entity ) {
		if ( entity != null && entity instanceof Person ) {
			super.loadToForm( entity );
			DivisionDao divisionDao = KTMEMDaoFactory.getInstance()
					.getDivisionDao();
			EmploymentDao empmDao = KTMEMDaoFactory.getInstance()
					.getEmploymentDao();
			Employee employee = getEmployeeRole( (Party) entity );
			if ( employee != null ) {
				Employment empm = empmDao.findByClient( employee.getUniqueId() );
				if ( empm != null ) {
					PartyRole role = empm.getSupply();
					if ( role instanceof Division ) {
						Division supply = (Division) role;
						this.setDivisionId( supply.getUniqueId() );
						Division division = (Division) divisionDao.get( supply.getUniqueId() );
						if ( division != null ) {
							if ( division.getParty() != null && division.getParty() instanceof Organization ) {
								Organization org = (Organization) division.getParty();
								this.setDivisionName( org.getThaiName() );
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void syncToEntity( KTMEntity entity ) {
		if ( entity != null && entity instanceof Person ) {
			super.syncToEntity( entity );
			Employee employee = getEmployeeRole( (Party) entity );
			if ( employee != null ) {
				EmploymentDao empmDao = KTMEMDaoFactory.getInstance()
						.getEmploymentDao();
				Employment empm = empmDao.findByClient( employee.getUniqueId() );
				if ( empm != null ) {
					PartyRole role = empm.getSupply();
					if ( role instanceof Division ) {
						Division supply = (Division) role;
						if ( supply.getUniqueId() != divisionId ) {
							DivisionDao divisionDao = KTMEMDaoFactory.getInstance()
									.getDivisionDao();
							supply = (Division) divisionDao.get( divisionId );
							empm.setSupply( supply );
						}
					}
				}
			}
		}
	}

	public List<DivisionBean> getDivisionCollection() {
		return divisionCollection;
	}

	public void setDivisionCollection( List<DivisionBean> divisionCollection ) {
		this.divisionCollection = divisionCollection;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName( String divisionName ) {
		this.divisionName = divisionName;
	}
}
