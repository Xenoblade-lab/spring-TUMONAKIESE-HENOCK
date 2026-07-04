package edu.upc.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import edu.upc.models.Livre;
import edu.upc.utils.JdbcSfmHelper;

@Repository
public class LivreRepoImpl implements LivreRepoCustom {

	private static final String SQL_INSERT = "INSERT INTO livres (titre, pages) VALUES (?, ?)";
	private static final String SQL_UPDATE = "UPDATE livres SET titre=?, pages=? WHERE id=?";
	private static final String SQL_DELETE = "DELETE FROM livres WHERE id=?";
	private static final String SQL_BY_ID = "SELECT id, titre, pages FROM livres WHERE id=?";
	private static final String SQL_ALL = "SELECT id, titre, pages FROM livres ORDER BY titre";

	@Autowired
	private JdbcClient jdbcClient;

	@Autowired
	private JdbcSfmHelper sfmHelper;

	@Override
	public long create(Livre entity) {
		Object[] params = new Object[] { entity.getTitre(), entity.getPages() };
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcClient.sql(SQL_INSERT).params(params).update(holder, "id");
		return holder.getKey().longValue();
	}

	@Override
	public void update(long id, Livre entity) {
		jdbcClient.sql(SQL_UPDATE).params(entity.getTitre(), entity.getPages(), id).update();
	}

	@Override
	public void delete(long id) {
		jdbcClient.sql(SQL_DELETE).params(id).update();
	}

	@Override
	public Livre getById(long id) {
		return sfmHelper.queryOne(SQL_BY_ID, Livre.class, new Object[] { id });
	}

	@Override
	public List<Livre> get() {
		return sfmHelper.query(SQL_ALL, Livre.class);
	}

}
