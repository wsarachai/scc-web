package org.ktm.scc.bean;

import java.text.ParseException;
import java.util.Collection;
import org.ktm.dao.KTMEMDaoFactory;
import org.ktm.dao.party.AuthenDao;
import org.ktm.domain.KTMEntity;
import org.ktm.domain.party.Authen;
import org.ktm.domain.party.ISOGender;
import org.ktm.domain.party.PartyIdentifier;
import org.ktm.domain.party.Person;
import org.ktm.utils.DateUtils;

public class PersonBean extends PartyBean {

	private String		citizenId;
	private String		prename;
	private String		firstname;
	private String		lastname;
	private String		birthDay;
	private ISOGender	gender;

	private String		loginuser;
	private String		loginpassword;

	@Override
	public void reset() {
		super.reset();
		setGender( ISOGender.MALE );
	}

	@Override
	public void loadToForm( KTMEntity entity ) {
		if ( entity != null && entity instanceof Person ) {
			super.loadToForm( entity );
			Person person = (Person) entity;
			this.setUniqueId( String.valueOf( person.getUniqueId() ) );
			this.setPrename( person.getPrename() );
			this.setFirstname( person.getFirstname() );
			this.setLastname( person.getLastname() );
			String identifier = "";
			if ( person.getIdentifier() != null ) {
				identifier = person.getIdentifier().getIdentifier();
			}
			this.setCitizenId( identifier );
			try {
				this.setBirthDay( DateUtils.formatDate( person.getBirthDay() ) );
			}
			catch ( ParseException e ) {
				e.printStackTrace();
			}
			this.setGender( person.getGender() );

			AuthenDao authenDao = KTMEMDaoFactory.getInstance().getAuthenDao();
			Authen authen = authenDao.findByPartyId( person.getUniqueId() );
			if ( authen != null ) {
				this.setLoginuser( authen.getUsername() );
				this.setLoginpassword( authen.getPassword() );
			}
		}
	}

	@Override
	public void syncToEntity( KTMEntity entity ) {
		if ( entity != null && entity instanceof Person ) {
			super.syncToEntity( entity );

			Person person = (Person) entity;
			person.setGender( this.getGender() );
			person.setPrename( this.getPrename() );
			person.setFirstname( this.getFirstname() );
			person.setLastname( this.getLastname() );
			PartyIdentifier identifier = person.getIdentifier();
			if ( identifier == null ) {
				identifier = new PartyIdentifier();
				person.setIdentifier( identifier );
			}
			identifier.setIdentifier( this.getCitizenId() );
			person.setBirthDay( this.getBirthDay() );

			// Authen is not need to sync
		}
	}

	@Override
	public void loadFormCollection( Collection<?> entitys ) {

		getFormCollection().clear();

		if ( entitys != null && entitys.size() > 0 ) {
			for ( Object entity : entitys ) {
				if ( entity instanceof Person ) {
					PersonBean bean = new PersonBean();
					bean.loadToForm( (Person) entity );
					getFormCollection().add( bean );
				}
			}
		}
	}

	public String getPrename() {
		return prename;
	}

	public void setPrename( String prename ) {
		this.prename = prename;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname( String firstname ) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname( String lastname ) {
		this.lastname = lastname;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay( String birthDay ) {
		this.birthDay = birthDay;
	}

	public ISOGender getGender() {
		return gender;
	}

	public void setGender( ISOGender gender ) {
		this.gender = gender;
	}

	public String getCitizenId() {
		return citizenId;
	}

	public void setCitizenId( String citizenId ) {
		this.citizenId = citizenId;
	}

	public String getLoginuser() {
		return loginuser;
	}

	public void setLoginuser( String loginuser ) {
		this.loginuser = loginuser;
	}

	public String getLoginpassword() {
		return loginpassword;
	}

	public void setLoginpassword( String loginpassword ) {
		this.loginpassword = loginpassword;
	}

}
