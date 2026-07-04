package edu.upc.services;

import java.util.List;

import org.springframework.validation.BindingResult;

import edu.upc.models.Emprunt;
import edu.upc.models.dtos.EmpruntDto;

public interface EmpruntService {

	Emprunt create(EmpruntDto dto, BindingResult result);

	List<Emprunt> delete(long id);

	Emprunt getById(long id);

	List<Emprunt> get();

}
