package org.ktm.dao.gallery;

import org.ktm.dao.AbstractHibernateStorageDao;
import com.sun.medialib.mlib.Image;

public class ImageDaoHibernate extends AbstractHibernateStorageDao implements
	ImageDao {

	private static final long	serialVersionUID	= 1L;

	@Override
	public Class<?> getFeaturedClass() {
		return Image.class;
	}

}
