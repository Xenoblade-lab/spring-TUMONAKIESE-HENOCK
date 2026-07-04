package edu.upc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import edu.upc.models.Genre;
import edu.upc.models.dtos.GenreDto;
import edu.upc.repositories.GenreRepoCustom;
import edu.upc.utils.Mapper;
import edu.upc.utils.MyLibraryUtil;
import edu.upc.utils.exceptions.NotFoundException;

@Service
public class GenreServiceImpl implements GenreService {

	@Autowired
	private GenreRepoCustom repo;

	@Autowired
	private Mapper mapper;

	@Autowired
	private MyLibraryUtil libraryUtil;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Genre create(GenreDto dto, BindingResult result) {
		libraryUtil.validate(result);
		Genre row = mapper.mapToGenre(dto);
		long id = repo.create(row);
		return repo.getById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Genre update(long id, GenreDto dto, BindingResult result) {
		libraryUtil.validate(result);
		requireExists(id);
		Genre row = mapper.mapToGenre(dto);
		repo.update(id, row);
		return repo.getById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<Genre> delete(long id) {
		requireExists(id);
		repo.delete(id);
		return repo.get();
	}

	@Override
	public Genre getById(long id) {
		Genre row = repo.getById(id);
		if (row == null) {
			throw new NotFoundException("Catégorie introuvable");
		}
		return row;
	}

	@Override
	public List<Genre> get() {
		return repo.get();
	}

	private void requireExists(long id) {
		if (repo.getById(id) == null) {
			throw new NotFoundException("Catégorie introuvable");
		}
	}

}
