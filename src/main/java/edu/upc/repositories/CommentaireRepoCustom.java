package edu.upc.repositories;

import java.util.List;

import edu.upc.models.Commentaire;

public interface CommentaireRepoCustom {

	long create(Commentaire entity);

	void delete(long id);

	Commentaire getById(long id);

	List<Commentaire> get();

}
