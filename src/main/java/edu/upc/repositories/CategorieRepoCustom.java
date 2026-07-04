package edu.upc.repositories;

import java.util.List;

import edu.upc.models.Categorie;

public interface CategorieRepoCustom {

	long create(Categorie entity);

	void update(long id, Categorie entity);

	void delete(long id);

	Categorie getById(long id);

	List<Categorie> get();

}
