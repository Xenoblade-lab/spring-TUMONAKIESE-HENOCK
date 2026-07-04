package edu.upc.services;

import java.util.List;

import org.springframework.validation.BindingResult;

import edu.upc.models.Auteur;
import edu.upc.models.dtos.AuteurDto;

public interface AuteurService {

	Auteur create(AuteurDto dto, BindingResult result);

	Auteur update(long id, AuteurDto dto, BindingResult result);

	List<Auteur> delete(long id);

	Auteur getById(long id);

	List<Auteur> get();

	List<Auteur> search(String keyword);

}
