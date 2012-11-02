package org.ktm.dao.party;

import java.io.Serializable;
import java.util.Collection;
import org.ktm.dao.DaoImplMangoDB;
import org.ktm.domain.KTMEntity;
import org.ktm.domain.party.Authen;
import org.ktm.exception.CreateException;
import org.ktm.exception.DeleteException;
import org.ktm.exception.StorageException;
import org.ktm.exception.UpdateException;
import org.ktm.utils.Localizer;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class AuthenDaoMongoDB extends DaoImplMangoDB implements AuthenDao {

    private static final long serialVersionUID = 1L;

    @Override
    public Class<?> getFeaturedClass() {
        return Authen.class;
    }

    private String getClassName() {
        return Localizer.getClassName(getFeaturedClass());
    }

    private void setAuthenProperties(DBObject obj, Authen auth) {
        auth.setUniqueId((Integer) obj.get("uniqueId"));
        auth.setUsername((String) obj.get("username"));
        auth.setPassword((String) obj.get("password"));
    }

    private void getAuthenProperties(DBObject obj, Authen auth) {
        obj.put("uniqueId", auth.getUniqueId());
        obj.put("username", auth.getUsername());
        obj.put("password", auth.getPassword());
    }

    @Override
    public Authen get(Serializable id) {
        Authen auth = null;
        DBCollection colls = getCurrentDB().getCollection(getClassName());
        BasicDBObject query = new BasicDBObject();

        query.put("uniqueId", id);

        DBCursor cursor = colls.find(query);
        try {
            while (cursor.hasNext()) {
                DBObject obj = cursor.next();
                System.out.println(obj);
                auth = new Authen();
                setAuthenProperties(obj, auth);
                break;
            }
        } finally {
            cursor.close();
        }
        return auth;
    }

    @Override
    public Serializable createOrUpdate(KTMEntity object) throws CreateException {
        if (object == null) {
            throw new CreateException("Either given class or object was null");
        }

        try {
            Authen auth = (Authen) object;
            DBCollection colls = getCurrentDB().getCollection(getClassName());
            Authen authTmp = get(auth.getUniqueId());
            if (authTmp == null) {
                Integer count = (int) colls.getCount();
                auth.setUniqueId(count + 1);
            }
            BasicDBObject obj = new BasicDBObject();
            getAuthenProperties(obj, auth);
            colls.insert(obj);
        } catch (Exception e) {
            throw new CreateException(e);
        }

        return object.getUniqueId();
    }

    @Override
    public KTMEntity update(KTMEntity object) throws UpdateException {
        if (object == null) {
            throw new UpdateException("Cannot update null object.");
        }
        if (get(object.getClass(), object.getUniqueId()) == null) {
            throw new UpdateException("Object to update not found.");
        }

        try {
            // getCurrentSession().saveOrUpdate(object);
        } catch (Exception e) {
            throw new UpdateException(e);
        }
        return object;
    }

    @Override
    public Serializable merge(KTMEntity object) throws StorageException {
        if (object == null) {
            throw new StorageException("Cannot merge null object");
        }
        if (object.getUniqueId() == null || get(object.getClass(), object.getUniqueId()) == null) {
            return createOrUpdate(object);
        } else {
            try {
                // getCurrentSession().merge(object);
            } catch (Exception e) {
                throw new StorageException(e);
            }
        }
        return object.getUniqueId();
    }

    @Override
    public int delete(Serializable id) throws DeleteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int delete(KTMEntity object) throws DeleteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Collection<?> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Authen findByUsername(String username) {
        Authen auth = null;
        DBCollection colls = getCurrentDB().getCollection(getClassName());
        BasicDBObject query = new BasicDBObject();

        query.put("username", username);

        DBCursor cursor = colls.find(query);
        try {
            while (cursor.hasNext()) {
                DBObject obj = cursor.next();
                System.out.println(obj);
                auth = new Authen();
                setAuthenProperties(obj, auth);
                break;
            }
        } finally {
            cursor.close();
        }
        return auth;
    }

    @Override
    public Authen findByPartyId(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }
}
