package org.ktm.dao.article;

import java.util.Collection;
import org.ktm.dao.Dao;
import org.ktm.domain.KTMEntity;
import org.ktm.domain.article.Article;

public interface ArticleDao extends Dao {

    public Collection<KTMEntity> findAll();

    public Article findByIdentifier(String identifier);
}
