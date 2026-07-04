package edu.upc.repositories;

import java.util.List;

import edu.upc.models.Livre;

public interface LivreRepoCustom {

	long create(Livre entity);

	void update(long id, Livre entity);

	void delete(long id);

	Livre getById(long id);

	List<Livre> get();

}
