package org.ktm.dao.article;

import java.util.Collection;
import org.ktm.dao.AbstractHibernateStorageDao;
import org.ktm.domain.KTMEntity;
import org.ktm.domain.article.Article;

public class ArticleDaoHibernate extends AbstractHibernateStorageDao implements ArticleDao {

    private static final long serialVersionUID = 1L;

    @Override
    public Class<?> getFeaturedClass() {
        return Article.class;
    }

    @SuppressWarnings("unchecked")
    public Collection<KTMEntity> findAll() {
        return (Collection<KTMEntity>) super.findAll();
    }
}
