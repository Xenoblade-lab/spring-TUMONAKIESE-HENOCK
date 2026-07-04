package edu.upc.services;

import java.util.List;

import org.springframework.validation.BindingResult;

import edu.upc.models.Livre;
import edu.upc.models.dtos.LivreDto;

public interface LivreService {

	Livre create(LivreDto dto, BindingResult result);

	Livre update(long id, LivreDto dto, BindingResult result);

	List<Livre> delete(long id);

	Livre getById(long id);

	List<Livre> get();

}
