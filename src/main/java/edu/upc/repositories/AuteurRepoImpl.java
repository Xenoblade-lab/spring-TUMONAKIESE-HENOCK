package edu.upc.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import edu.upc.models.Article;
import edu.upc.models.Auteur;
import edu.upc.utils.JdbcSfmHelper;

@Repository
public class AuteurRepoImpl implements AuteurRepoCustom {

	private static final String SQL_INSERT = """
			INSERT INTO auteurs (categorie_pk, noms, ville, email, experience)
			VALUES (?, ?, ?, ?, ?)
			""";

	private static final String SQL_UPDATE = """
			UPDATE auteurs
			SET categorie_pk=?, noms=?, ville=?, email=?, experience=?
			WHERE id=?
			""";

	private static final String SQL_DELETE = "DELETE FROM auteurs WHERE id=?";

	private static final String SQL_BY_ID = """
			SELECT c.id, c.categorie_pk, c.noms, c.ville, c.email, c.experience,
			       cat.id categorie_id, cat.libelle categorie_libelle
			FROM auteurs c
			INNER JOIN categories cat ON c.categorie_pk = cat.id
			WHERE c.id = ?
			""";

	private static final String SQL_WITH_CATEGORIE = """
			SELECT c.id, c.categorie_pk, c.noms, c.ville, c.email, c.experience,
			       cat.id categorie_id, cat.libelle categorie_libelle
			FROM auteurs c
			INNER JOIN categories cat ON c.categorie_pk = cat.id
			ORDER BY c.noms
			""";

	private static final String SQL_SEARCH = """
			SELECT c.id, c.categorie_pk, c.noms, c.ville, c.email, c.experience,
			       cat.id categorie_id, cat.libelle categorie_libelle
			FROM auteurs c
			INNER JOIN categories cat ON c.categorie_pk = cat.id
			WHERE c.noms LIKE ?
			ORDER BY c.noms
			""";

	private static final String SQL_ARTICLES = """
			SELECT b.id, b.titre, b.vues
			FROM articles b
			INNER JOIN commentaires v ON v.article_pk = b.id
			WHERE v.auteur_pk = ?
			ORDER BY b.titre
			""";

	@Autowired
	private JdbcClient jdbcClient;

	@Autowired
	private JdbcSfmHelper sfmHelper;

	@Override
	public long create(Auteur entity) {
		Object[] params = new Object[] {
				entity.getCategoriePk(),
				entity.getNoms(),
				entity.getVille(),
				entity.getEmail(),
				entity.getExperience()
		};
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcClient.sql(SQL_INSERT).params(params).update(holder, "id");
		return holder.getKey().longValue();
	}

	@Override
	public void update(long id, Auteur entity) {
		jdbcClient.sql(SQL_UPDATE).params(
				entity.getCategoriePk(),
				entity.getNoms(),
				entity.getVille(),
				entity.getEmail(),
				entity.getExperience(),
				id).update();
	}

	@Override
	public void delete(long id) {
		jdbcClient.sql(SQL_DELETE).params(id).update();
	}

	@Override
	public Auteur getById(long id) {
		return getWithCategorieById(id);
	}

	@Override
	public List<Auteur> getWithCategorie() {
		return sfmHelper.queryJoin(SQL_WITH_CATEGORIE, Auteur.class, "id", "categorie_id");
	}

	@Override
	public Auteur getWithCategorieById(long id) {
		Auteur row = sfmHelper.queryJoinOne(SQL_BY_ID, Auteur.class, new Object[] { id }, "id", "categorie_id");
		if (row != null) {
			row.setArticles(getArticles(id));
		}
		return row;
	}

	@Override
	public List<Article> getArticles(long auteurId) {
		return sfmHelper.query(SQL_ARTICLES, Article.class, new Object[] { auteurId });
	}

	@Override
	public List<Auteur> search(String keyword) {
		if (keyword == null || keyword.isBlank()) {
			return getWithCategorie();
		}
		return sfmHelper.queryJoin(SQL_SEARCH, Auteur.class, new Object[] { "%" + keyword.trim() + "%" }, "id",
				"categorie_id");
	}

}
