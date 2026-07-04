package edu.upc.repositories;

import java.util.List;

import edu.upc.models.Livre;
import edu.upc.models.Adherent;

public interface AdherentRepoCustom {

	long create(Adherent entity);

	void update(long id, Adherent entity);

	void delete(long id);

	Adherent getById(long id);

	List<Adherent> getWithGenre();

	Adherent getWithGenreById(long id);

	List<Livre> getLivres(long adherentId);

	List<Adherent> search(String keyword);

}
