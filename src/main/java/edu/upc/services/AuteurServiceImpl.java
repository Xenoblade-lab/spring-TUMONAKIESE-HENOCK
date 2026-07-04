package edu.upc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import edu.upc.models.Auteur;
import edu.upc.models.dtos.AuteurDto;
import edu.upc.repositories.AuteurRepoCustom;
import edu.upc.utils.Mapper;
import edu.upc.utils.MyLibraryUtil;
import edu.upc.utils.exceptions.NotFoundException;

@Service
public class AuteurServiceImpl implements AuteurService {

	@Autowired
	private AuteurRepoCustom repo;

	@Autowired
	private Mapper mapper;

	@Autowired
	private MyLibraryUtil libraryUtil;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Auteur create(AuteurDto dto, BindingResult result) {
		libraryUtil.validate(result);
		Auteur row = mapper.mapToAuteur(dto);
		long id = repo.create(row);
		return repo.getWithCategorieById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Auteur update(long id, AuteurDto dto, BindingResult result) {
		libraryUtil.validate(result);
		requireExists(id);
		Auteur row = mapper.mapToAuteur(dto);
		repo.update(id, row);
		return repo.getWithCategorieById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<Auteur> delete(long id) {
		requireExists(id);
		repo.delete(id);
		return repo.getWithCategorie();
	}

	@Override
	public Auteur getById(long id) {
		Auteur row = repo.getWithCategorieById(id);
		if (row == null) {
			throw new NotFoundException("Auteur introuvable");
		}
		return row;
	}

	@Override
	public List<Auteur> get() {
		return repo.getWithCategorie();
	}

	@Override
	public List<Auteur> search(String keyword) {
		return repo.search(keyword);
	}

	private void requireExists(long id) {
		if (repo.getWithCategorieById(id) == null) {
			throw new NotFoundException("Auteur introuvable");
		}
	}

}
