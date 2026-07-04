package edu.upc.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import edu.upc.models.Commentaire;
import edu.upc.utils.JdbcSfmHelper;

@Repository
public class CommentaireRepoImpl implements CommentaireRepoCustom {

	private static final String SQL_INSERT = """
			INSERT INTO commentaires (auteur_pk, article_pk, texte) VALUES (?, ?, ?)
			""";

	private static final String SQL_DELETE = "DELETE FROM commentaires WHERE id=?";

	private static final String SQL_BY_ID = """
			SELECT v.id, v.auteur_pk, v.article_pk, v.texte,
			       c.id auteur_id, c.noms auteur_noms,
			       b.id article_id, b.titre article_titre, b.vues article_vues
			FROM commentaires v
			INNER JOIN auteurs c ON v.auteur_pk = c.id
			INNER JOIN articles b ON v.article_pk = b.id
			WHERE v.id = ?
			""";

	private static final String SQL_ALL = """
			SELECT v.id, v.auteur_pk, v.article_pk, v.texte,
			       c.id auteur_id, c.noms auteur_noms,
			       b.id article_id, b.titre article_titre, b.vues article_vues
			FROM commentaires v
			INNER JOIN auteurs c ON v.auteur_pk = c.id
			INNER JOIN articles b ON v.article_pk = b.id
			ORDER BY c.noms, b.titre
			""";

	@Autowired
	private JdbcClient jdbcClient;

	@Autowired
	private JdbcSfmHelper sfmHelper;

	@Override
	public long create(Commentaire entity) {
		Object[] params = new Object[] { entity.getAuteurPk(), entity.getArticlePk(), entity.getTexte() };
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcClient.sql(SQL_INSERT).params(params).update(holder, "id");
		return holder.getKey().longValue();
	}

	@Override
	public void delete(long id) {
		jdbcClient.sql(SQL_DELETE).params(id).update();
	}

	@Override
	public Commentaire getById(long id) {
		return sfmHelper.queryJoinOne(SQL_BY_ID, Commentaire.class, new Object[] { id }, "id", "auteur_id", "article_id");
	}

	@Override
	public List<Commentaire> get() {
		return sfmHelper.queryJoin(SQL_ALL, Commentaire.class, "id", "auteur_id", "article_id");
	}

}
