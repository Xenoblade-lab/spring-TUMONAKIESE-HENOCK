package edu.upc.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import edu.upc.models.Categorie;
import edu.upc.utils.JdbcSfmHelper;

@Repository
public class CategorieRepoImpl implements CategorieRepoCustom {

	private static final String SQL_INSERT = "INSERT INTO categories (libelle) VALUES (?)";
	private static final String SQL_UPDATE = "UPDATE categories SET libelle=? WHERE id=?";
	private static final String SQL_DELETE = "DELETE FROM categories WHERE id=?";
	private static final String SQL_BY_ID = "SELECT id, libelle FROM categories WHERE id=?";
	private static final String SQL_ALL = "SELECT id, libelle FROM categories ORDER BY libelle";

	@Autowired
	private JdbcClient jdbcClient;

	@Autowired
	private JdbcSfmHelper sfmHelper;

	@Override
	public long create(Categorie entity) {
		Object[] params = new Object[] { entity.getLibelle() };
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcClient.sql(SQL_INSERT).params(params).update(holder, "id");
		return holder.getKey().longValue();
	}

	@Override
	public void update(long id, Categorie entity) {
		jdbcClient.sql(SQL_UPDATE).params(entity.getLibelle(), id).update();
	}

	@Override
	public void delete(long id) {
		jdbcClient.sql(SQL_DELETE).params(id).update();
	}

	@Override
	public Categorie getById(long id) {
		return sfmHelper.queryOne(SQL_BY_ID, Categorie.class, new Object[] { id });
	}

	@Override
	public List<Categorie> get() {
		return sfmHelper.query(SQL_ALL, Categorie.class);
	}

}
