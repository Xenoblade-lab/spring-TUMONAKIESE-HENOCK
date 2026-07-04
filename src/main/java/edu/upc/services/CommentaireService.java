package edu.upc.services;

import java.util.List;

import org.springframework.validation.BindingResult;

import edu.upc.models.Commentaire;
import edu.upc.models.dtos.CommentaireDto;

public interface CommentaireService {

	Commentaire create(CommentaireDto dto, BindingResult result);

	List<Commentaire> delete(long id);

	Commentaire getById(long id);

	List<Commentaire> get();

}
