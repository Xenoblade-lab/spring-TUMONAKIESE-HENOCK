package edu.upc.repositories;

import java.util.List;

import edu.upc.models.Article;
import edu.upc.models.Auteur;

public interface AuteurRepoCustom {

	long create(Auteur entity);

	void update(long id, Auteur entity);

	void delete(long id);

	Auteur getById(long id);

	List<Auteur> getWithCategorie();

	Auteur getWithCategorieById(long id);

	List<Article> getArticles(long auteurId);

	List<Auteur> search(String keyword);

}
