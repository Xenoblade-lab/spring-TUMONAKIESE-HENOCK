package edu.upc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import edu.upc.models.Livre;
import edu.upc.models.dtos.LivreDto;
import edu.upc.repositories.LivreRepoCustom;
import edu.upc.utils.Mapper;
import edu.upc.utils.MyLibraryUtil;
import edu.upc.utils.exceptions.NotFoundException;

@Service
public class LivreServiceImpl implements LivreService {

	@Autowired
	private LivreRepoCustom repo;

	@Autowired
	private Mapper mapper;

	@Autowired
	private MyLibraryUtil libraryUtil;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Livre create(LivreDto dto, BindingResult result) {
		libraryUtil.validate(result);
		Livre row = mapper.mapToLivre(dto);
		long id = repo.create(row);
		return repo.getById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Livre update(long id, LivreDto dto, BindingResult result) {
		libraryUtil.validate(result);
		requireExists(id);
		Livre row = mapper.mapToLivre(dto);
		repo.update(id, row);
		return repo.getById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<Livre> delete(long id) {
		requireExists(id);
		repo.delete(id);
		return repo.get();
	}

	@Override
	public Livre getById(long id) {
		Livre row = repo.getById(id);
		if (row == null) {
			throw new NotFoundException("Livre introuvable");
		}
		return row;
	}

	@Override
	public List<Livre> get() {
		return repo.get();
	}

	private void requireExists(long id) {
		if (repo.getById(id) == null) {
			throw new NotFoundException("Livre introuvable");
		}
	}

}
