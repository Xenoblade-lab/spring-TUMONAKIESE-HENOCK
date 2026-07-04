package edu.upc.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import edu.upc.models.Article;
import edu.upc.utils.JdbcSfmHelper;

@Repository
public class ArticleRepoImpl implements ArticleRepoCustom {

	private static final String SQL_INSERT = "INSERT INTO articles (titre, vues) VALUES (?, ?)";
	private static final String SQL_UPDATE = "UPDATE articles SET titre=?, vues=? WHERE id=?";
	private static final String SQL_DELETE = "DELETE FROM articles WHERE id=?";
	private static final String SQL_BY_ID = "SELECT id, titre, vues FROM articles WHERE id=?";
	private static final String SQL_ALL = "SELECT id, titre, vues FROM articles ORDER BY titre";

	@Autowired
	private JdbcClient jdbcClient;

	@Autowired
	private JdbcSfmHelper sfmHelper;

	@Override
	public long create(Article entity) {
		Object[] params = new Object[] { entity.getTitre(), entity.getVues() };
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcClient.sql(SQL_INSERT).params(params).update(holder, "id");
		return holder.getKey().longValue();
	}

	@Override
	public void update(long id, Article entity) {
		jdbcClient.sql(SQL_UPDATE).params(entity.getTitre(), entity.getVues(), id).update();
	}

	@Override
	public void delete(long id) {
		jdbcClient.sql(SQL_DELETE).params(id).update();
	}

	@Override
	public Article getById(long id) {
		return sfmHelper.queryOne(SQL_BY_ID, Article.class, new Object[] { id });
	}

	@Override
	public List<Article> get() {
		return sfmHelper.query(SQL_ALL, Article.class);
	}

}
