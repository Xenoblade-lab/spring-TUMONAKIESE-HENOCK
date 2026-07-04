package edu.upc.services;

import java.util.List;

import org.springframework.validation.BindingResult;

import edu.upc.models.Adherent;
import edu.upc.models.dtos.AdherentDto;

public interface AdherentService {

	Adherent create(AdherentDto dto, BindingResult result);

	Adherent update(long id, AdherentDto dto, BindingResult result);

	List<Adherent> delete(long id);

	Adherent getById(long id);

	List<Adherent> get();

	List<Adherent> search(String keyword);

}
