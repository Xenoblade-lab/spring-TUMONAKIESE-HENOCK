package edu.upc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import edu.upc.models.Categorie;
import edu.upc.models.dtos.CategorieDto;
import edu.upc.repositories.CategorieRepoCustom;
import edu.upc.utils.Mapper;
import edu.upc.utils.MyLibraryUtil;
import edu.upc.utils.exceptions.NotFoundException;

@Service
public class CategorieServiceImpl implements CategorieService {

	@Autowired
	private CategorieRepoCustom repo;

	@Autowired
	private Mapper mapper;

	@Autowired
	private MyLibraryUtil libraryUtil;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Categorie create(CategorieDto dto, BindingResult result) {
		libraryUtil.validate(result);
		Categorie row = mapper.mapToCategorie(dto);
		long id = repo.create(row);
		return repo.getById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Categorie update(long id, CategorieDto dto, BindingResult result) {
		libraryUtil.validate(result);
		requireExists(id);
		Categorie row = mapper.mapToCategorie(dto);
		repo.update(id, row);
		return repo.getById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<Categorie> delete(long id) {
		requireExists(id);
		repo.delete(id);
		return repo.get();
	}

	@Override
	public Categorie getById(long id) {
		Categorie row = repo.getById(id);
		if (row == null) {
			throw new NotFoundException("Catégorie introuvable");
		}
		return row;
	}

	@Override
	public List<Categorie> get() {
		return repo.get();
	}

	private void requireExists(long id) {
		if (repo.getById(id) == null) {
			throw new NotFoundException("Catégorie introuvable");
		}
	}

}
