package org.ktm.dao.article;

import java.util.Collection;
import org.ktm.dao.Dao;
import org.ktm.domain.KTMEntity;

public interface ArticleTypeDao extends Dao {

    public Collection<KTMEntity> findAll();
}
