package edu.upc.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import edu.upc.models.Livre;
import edu.upc.models.Adherent;
import edu.upc.utils.JdbcSfmHelper;

@Repository
public class AdherentRepoImpl implements AdherentRepoCustom {

	private static final String SQL_INSERT = """
			INSERT INTO adherents (genre_pk, noms, quartier, telephone, quota_max)
			VALUES (?, ?, ?, ?, ?)
			""";

	private static final String SQL_UPDATE = """
			UPDATE adherents
			SET genre_pk=?, noms=?, quartier=?, telephone=?, quota_max=?
			WHERE id=?
			""";

	private static final String SQL_DELETE = "DELETE FROM adherents WHERE id=?";

	private static final String SQL_BY_ID = """
			SELECT c.id, c.genre_pk, c.noms, c.quartier, c.telephone, c.quota_max,
			       cat.id genre_id, cat.libelle genre_libelle
			FROM adherents c
			INNER JOIN genres cat ON c.genre_pk = cat.id
			WHERE c.id = ?
			""";

	private static final String SQL_WITH_CATEGORIE = """
			SELECT c.id, c.genre_pk, c.noms, c.quartier, c.telephone, c.quota_max,
			       cat.id genre_id, cat.libelle genre_libelle
			FROM adherents c
			INNER JOIN genres cat ON c.genre_pk = cat.id
			ORDER BY c.noms
			""";

	private static final String SQL_SEARCH = """
			SELECT c.id, c.genre_pk, c.noms, c.quartier, c.telephone, c.quota_max,
			       cat.id genre_id, cat.libelle genre_libelle
			FROM adherents c
			INNER JOIN genres cat ON c.genre_pk = cat.id
			WHERE c.noms LIKE ?
			ORDER BY c.noms
			""";

	private static final String SQL_BIENS = """
			SELECT b.id, b.titre, b.pages
			FROM livres b
			INNER JOIN emprunts v ON v.livre_pk = b.id
			WHERE v.adherent_pk = ?
			ORDER BY b.titre
			""";

	@Autowired
	private JdbcClient jdbcClient;

	@Autowired
	private JdbcSfmHelper sfmHelper;

	@Override
	public long create(Adherent entity) {
		Object[] params = new Object[] {
				entity.getGenrePk(),
				entity.getNoms(),
				entity.getQuartier(),
				entity.getTelephone(),
				entity.getQuotaMax()
		};
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcClient.sql(SQL_INSERT).params(params).update(holder, "id");
		return holder.getKey().longValue();
	}

	@Override
	public void update(long id, Adherent entity) {
		jdbcClient.sql(SQL_UPDATE).params(
				entity.getGenrePk(),
				entity.getNoms(),
				entity.getQuartier(),
				entity.getTelephone(),
				entity.getQuotaMax(),
				id).update();
	}

	@Override
	public void delete(long id) {
		jdbcClient.sql(SQL_DELETE).params(id).update();
	}

	@Override
	public Adherent getById(long id) {
		return getWithGenreById(id);
	}

	@Override
	public List<Adherent> getWithGenre() {
		return sfmHelper.queryJoin(SQL_WITH_CATEGORIE, Adherent.class, "id", "genre_id");
	}

	@Override
	public Adherent getWithGenreById(long id) {
		Adherent row = sfmHelper.queryJoinOne(SQL_BY_ID, Adherent.class, new Object[] { id }, "id", "genre_id");
		if (row != null) {
			row.setLivres(getLivres(id));
		}
		return row;
	}

	@Override
	public List<Livre> getLivres(long adherentId) {
		return sfmHelper.query(SQL_BIENS, Livre.class, new Object[] { adherentId });
	}

	@Override
	public List<Adherent> search(String keyword) {
		if (keyword == null || keyword.isBlank()) {
			return getWithGenre();
		}
		return sfmHelper.queryJoin(SQL_SEARCH, Adherent.class, new Object[] { "%" + keyword.trim() + "%" }, "id",
				"genre_id");
	}

}
