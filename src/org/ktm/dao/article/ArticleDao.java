package org.ktm.dao.article;

import java.util.Collection;
import org.ktm.dao.image_upload.ImageUploadDao;
import org.ktm.domain.KTMEntity;

public interface ArticleDao extends ImageUploadDao {

	public Collection<KTMEntity> findAll();
}
