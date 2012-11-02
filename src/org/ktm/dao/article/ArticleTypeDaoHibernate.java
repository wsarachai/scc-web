package org.ktm.dao.article;

import java.util.Collection;
import org.ktm.dao.AbstractHibernateStorageDao;
import org.ktm.domain.KTMEntity;
import org.ktm.domain.article.ArticleType;

public class ArticleTypeDaoHibernate extends AbstractHibernateStorageDao implements ArticleTypeDao {

    private static final long serialVersionUID = 1L;

    @Override
    public Class<?> getFeaturedClass() {
        return ArticleType.class;
    }

    @SuppressWarnings("unchecked")
    public Collection<KTMEntity> findAll() {
        return (Collection<KTMEntity>) super.findAll();
    }
}
