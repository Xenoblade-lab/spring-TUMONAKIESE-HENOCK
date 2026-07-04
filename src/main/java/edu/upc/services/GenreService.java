package edu.upc.services;

import java.util.List;

import org.springframework.validation.BindingResult;

import edu.upc.models.Genre;
import edu.upc.models.dtos.GenreDto;

public interface GenreService {

	Genre create(GenreDto dto, BindingResult result);

	Genre update(long id, GenreDto dto, BindingResult result);

	List<Genre> delete(long id);

	Genre getById(long id);

	List<Genre> get();

}
