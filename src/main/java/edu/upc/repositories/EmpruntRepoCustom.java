package edu.upc.repositories;

import java.util.List;

import edu.upc.models.Emprunt;

public interface EmpruntRepoCustom {

	long create(Emprunt entity);

	void delete(long id);

	Emprunt getById(long id);

	List<Emprunt> get();

}
