package org.ktm.scc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ktm.authen.Authenticator;
import org.ktm.authen.AuthenticatorFactory;
import org.ktm.core.KTMContext;
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
import org.ktm.exception.AuthException;
import org.ktm.exception.CreateException;
import org.ktm.exception.DeleteException;
import org.ktm.exception.UpdateException;
import org.ktm.scc.bean.DivisionBean;
import org.ktm.scc.bean.MemberBean;
import org.ktm.scc.bean.PersonBean;
import org.ktm.scc.login.AuthenImpl;
import org.ktm.servlet.ActionForward;
import org.ktm.utils.Globals;
import org.ktm.web.bean.FormBean;
import org.ktm.web.tags.Functions;

@WebServlet( "/RGB7-backoffice-v4/CRUDMembers" )
public class CRUDMemberServlet extends CRUDSCCServlet {

	private static final long	serialVersionUID	= 1L;

	@Override
	public String getBeanClass() {
		return "org.ktm.scc.bean.MemberBean";
	}
	@SuppressWarnings( "unchecked" )
	public	ActionForward
			listMember( FormBean form,
						HttpServletRequest request,
						HttpServletResponse response )	throws ServletException,
														IOException {
		PersonBean bean = (PersonBean) form;
		ActionForward forward = null;

		try {
			Authenticator auth = AuthenticatorFactory.getAuthComponentNoCreate( request.getSession() );

			if ( auth.isInRoles( roles ) ) {
				List<Person> persons = new ArrayList<Person>();

				AuthenDao authenDao = KTMEMDaoFactory.getInstance()
						.getAuthenDao();
				List<Authen> authens = (List<Authen>) authenDao.find( bean.getPageNumber() + 1 );

				bean.setMaxPage( KTMContext.paging );
				bean.setMaxRows( (int) authenDao.getCount() );

				Iterator<Authen> it = authens.iterator();
				while ( it.hasNext() ) {
					Authen authen = it.next();
					if ( authen.getParty() != null ) {
						if ( authen.getParty() instanceof Person ) {
							persons.add( (Person) authen.getParty() );
						}
					}
				}

				bean.loadFormCollection( persons );

				forward = ActionForward.getUri( this,
						request,
						"ListMembers.jsp" );
			}
		}
		catch ( AuthException e ) {
			e.printStackTrace();
		}

		if ( forward == null ) {
			String forwardUri = request.getServletContext()
					.getInitParameter( Globals.MAIN_PAGE );

			if ( !isEmptyString( forwardUri ) ) {

				forward = ActionForward.getAction( this,
						request,
						forwardUri,
						true );
			}
		}

		return forward;
	}

	private void getDivisionCollection( MemberBean bean ) {
		DivisionDao divisionDao = KTMEMDaoFactory.getInstance()
				.getDivisionDao();
		bean.getDivisionCollection().clear();
		List<Division> divisonList = divisionDao.findAll();
		for ( Division division : divisonList ) {
			DivisionBean divisionBean = new DivisionBean();
			divisionBean.loadToForm( division );
			bean.getDivisionCollection().add( divisionBean );
		}
	}

	public	ActionForward
			editMember( FormBean form,
						HttpServletRequest request,
						HttpServletResponse response )	throws ServletException,
														IOException {
		MemberBean bean = (MemberBean) form;

		ActionForward forward = null;

		PersonDao personDao = KTMEMDaoFactory.getInstance().getPersonDao();

		try {
			Person person = null;
			Integer edit_userid = Integer.parseInt( bean.getUniqueId() );

			Authenticator auth = AuthenticatorFactory.getAuthComponentNoCreate( request.getSession() );

			if ( auth.isInRoles( roles ) ) {
				person = (Person) personDao.get( edit_userid );
			} else {
				Integer userid = (Integer) auth.getProperty( "userid" );
				if ( userid != null && userid == edit_userid ) {
					person = (Person) personDao.get( edit_userid );
				}
			}

			if ( person != null ) {
				bean.loadToForm( person );
				getDivisionCollection( bean );

				forward = ActionForward.getUri( this,
						request,
						"EditMembers.jsp" );
			}

		}
		catch ( AuthException e ) {
			e.printStackTrace();
		}

		if ( forward == null ) {
			String forwardUri = request.getServletContext()
					.getInitParameter( Globals.MAIN_PAGE );

			if ( !isEmptyString( forwardUri ) ) {

				forward = ActionForward.getAction( this,
						request,
						forwardUri,
						true );
			}
		}

		return forward;
	}

	public	ActionForward
			newMember(	FormBean form,
						HttpServletRequest request,
						HttpServletResponse response )	throws ServletException,
														IOException {
		MemberBean bean = (MemberBean) form;
		ActionForward forward = null;

		Authenticator auth;

		try {

			auth = AuthenticatorFactory.getAuthComponentNoCreate( request.getSession() );

			if ( auth.isInRoles( roles ) ) {
				getDivisionCollection( bean );
			}

			forward = ActionForward.getUri( this, request, "EditMembers.jsp" );
		}
		catch ( AuthException e ) {
			e.printStackTrace();
		}

		if ( forward == null ) {
			String forwardUri = request.getServletContext()
					.getInitParameter( Globals.MAIN_PAGE );

			if ( !isEmptyString( forwardUri ) ) {

				forward = ActionForward.getAction( this,
						request,
						forwardUri,
						true );
			}
		}

		return forward;
	}

	public	ActionForward
			saveMember( FormBean form,
						HttpServletRequest request,
						HttpServletResponse response )	throws ServletException,
														IOException {
		MemberBean bean = (MemberBean) form;
		ActionForward forward = null;

		Authenticator auth;

		try {
			Person person = null;

			PersonDao personDao = KTMEMDaoFactory.getInstance().getPersonDao();

			auth = AuthenticatorFactory.getAuthComponentNoCreate( request.getSession() );

			if ( auth.isInRoles( roles ) ) {
				if ( !Functions.isEmpty( bean.getUniqueId() ) ) {
					person = (Person) personDao.get( Integer.parseInt( bean.getUniqueId() ) );
				} else {
					person = new Person();
				}
			} else if ( !Functions.isEmpty( bean.getUniqueId() ) ) {
				Integer userid = (Integer) auth.getProperty( "userid" );
				Integer usersave_id = Integer.parseInt( bean.getUniqueId() );
				if ( userid == usersave_id ) {
					person = (Person) personDao.get( usersave_id );
				}
			}

			if ( person != null ) {
				bean.syncToEntity( person );
				Employee employee = bean.getEmployeeRole( person );
				if ( employee == null ) {
					employee = new Employee();
					person.getRoles().add( employee );
					employee.setParty( person );
				}

				AuthenDao authenDao = KTMEMDaoFactory.getInstance()
						.getAuthenDao();
				EmploymentDao empmDao = KTMEMDaoFactory.getInstance()
						.getEmploymentDao();
				DivisionDao divisionDao = KTMEMDaoFactory.getInstance()
						.getDivisionDao();

				personDao.createOrUpdate( person );
				if ( !Functions.isEmpty( bean.getLoginuser() ) && !Functions.isEmpty( bean.getLoginpassword() ) ) {
					Authen authen = authenDao.findByPartyId( person.getUniqueId() );
					if ( authen == null ) {
						authen = new Authen();
						authen.setParty( person );
					}
					authen.setUsername( bean.getLoginuser() );
					authen.setPassword( AuthenImpl.encode( bean.getLoginuser(),
							bean.getLoginpassword() ) );
					authenDao.createOrUpdate( authen );
				}
				Employment empm = empmDao.findByClient( employee.getUniqueId() );
				if ( empm == null ) {
					empm = new Employment();
					empm.setClient( employee );
				}
				if ( empm.getSupply() == null || empm.getSupply().getUniqueId() != bean.getDivisionId() ) {
					Division division = (Division) divisionDao.get( bean.getDivisionId() );
					if ( division != null ) {
						empm.setSupply( division );
					}
				}
				empmDao.createOrUpdate( empm );
				forward = ActionForward.getAction( this,
						request,
						"CRUDMembers?method=list&module=member&pageNumber=" + bean.getPageNumber(),
						true );
			}
		}
		catch ( AuthException e ) {
			e.printStackTrace();
		}
		catch ( CreateException e ) {
			e.printStackTrace();
		}

		if ( forward == null ) {
			String forwardUri = request.getServletContext()
					.getInitParameter( Globals.MAIN_PAGE );

			if ( !isEmptyString( forwardUri ) ) {

				forward = ActionForward.getAction( this,
						request,
						forwardUri,
						true );
			}
		}

		return forward;
	}
	public	ActionForward
			delMember(	FormBean form,
						HttpServletRequest request,
						HttpServletResponse response )	throws ServletException,
														IOException {
		MemberBean bean = (MemberBean) form;
		ActionForward forward = null;

		Authenticator auth;
		try {
			auth = AuthenticatorFactory.getAuthComponentNoCreate( request.getSession() );

			if ( auth.isInRoles( roles ) ) {
				AuthenDao authenDao = KTMEMDaoFactory.getInstance()
						.getAuthenDao();
				PersonDao personDao = KTMEMDaoFactory.getInstance()
						.getPersonDao();
				EmploymentDao empmDao = KTMEMDaoFactory.getInstance()
						.getEmploymentDao();
				Person person = (Person) personDao.get( Integer.parseInt( bean.getUniqueId() ) );
				if ( person != null ) {
					Authen authen = (Authen) authenDao.findByPartyId( person.getUniqueId() );
					if ( authen != null ) {
						authen.setParty( null );
						authenDao.update( authen );
						authenDao.delete( authen );
					}
					Employee employee = bean.getEmployeeRole( person );
					if ( employee != null ) {
						Employment empm = empmDao.findByClient( employee.getUniqueId() );
						if ( empm != null ) {
							empm.setClient( null );
							empm.setSupply( null );
							empm.setType( null );
							empmDao.update( empm );
							empmDao.delete( empm );
						}
					}
					personDao.delete( person );
				}
				forward = ActionForward.getAction( this,
						request,
						"CRUDMembers?method=list&module=member&pageNumber=" + bean.getPageNumber(),
						true );
			}
		}
		catch ( AuthException e ) {
			e.printStackTrace();
		}
		catch ( DeleteException e ) {
			e.printStackTrace();
		}
		catch ( UpdateException e ) {
			e.printStackTrace();
		}

		if ( forward == null ) {
			String forwardUri = request.getServletContext()
					.getInitParameter( Globals.MAIN_PAGE );

			if ( !isEmptyString( forwardUri ) ) {

				forward = ActionForward.getAction( this,
						request,
						forwardUri,
						true );
			}
		}

		return forward;
	}
}
