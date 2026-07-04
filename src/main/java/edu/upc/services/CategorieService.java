package edu.upc.services;

import java.util.List;

import org.springframework.validation.BindingResult;

import edu.upc.models.Categorie;
import edu.upc.models.dtos.CategorieDto;

public interface CategorieService {

	Categorie create(CategorieDto dto, BindingResult result);

	Categorie update(long id, CategorieDto dto, BindingResult result);

	List<Categorie> delete(long id);

	Categorie getById(long id);

	List<Categorie> get();

}
