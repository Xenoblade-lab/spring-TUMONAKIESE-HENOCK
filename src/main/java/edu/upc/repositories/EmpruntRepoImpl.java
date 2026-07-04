package edu.upc.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import edu.upc.models.Emprunt;
import edu.upc.utils.JdbcSfmHelper;

@Repository
public class EmpruntRepoImpl implements EmpruntRepoCustom {

	private static final String SQL_INSERT = """
			INSERT INTO emprunts (adherent_pk, livre_pk) VALUES (?, ?)
			""";

	private static final String SQL_DELETE = "DELETE FROM emprunts WHERE id=?";

	private static final String SQL_BY_ID = """
			SELECT v.id, v.adherent_pk, v.livre_pk,
			       c.id adherent_id, c.noms adherent_noms,
			       b.id livre_id, b.titre livre_titre, b.pages livre_pages
			FROM emprunts v
			INNER JOIN adherents c ON v.adherent_pk = c.id
			INNER JOIN livres b ON v.livre_pk = b.id
			WHERE v.id = ?
			""";

	private static final String SQL_ALL = """
			SELECT v.id, v.adherent_pk, v.livre_pk,
			       c.id adherent_id, c.noms adherent_noms,
			       b.id livre_id, b.titre livre_titre, b.pages livre_pages
			FROM emprunts v
			INNER JOIN adherents c ON v.adherent_pk = c.id
			INNER JOIN livres b ON v.livre_pk = b.id
			ORDER BY c.noms, b.titre
			""";

	@Autowired
	private JdbcClient jdbcClient;

	@Autowired
	private JdbcSfmHelper sfmHelper;

	@Override
	public long create(Emprunt entity) {
		Object[] params = new Object[] { entity.getAdherentPk(), entity.getLivrePk() };
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcClient.sql(SQL_INSERT).params(params).update(holder, "id");
		return holder.getKey().longValue();
	}

	@Override
	public void delete(long id) {
		jdbcClient.sql(SQL_DELETE).params(id).update();
	}

	@Override
	public Emprunt getById(long id) {
		return sfmHelper.queryJoinOne(SQL_BY_ID, Emprunt.class, new Object[] { id }, "id", "adherent_id", "livre_id");
	}

	@Override
	public List<Emprunt> get() {
		return sfmHelper.queryJoin(SQL_ALL, Emprunt.class, "id", "adherent_id", "livre_id");
	}

}
