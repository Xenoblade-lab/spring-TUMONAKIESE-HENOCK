package edu.upc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import edu.upc.models.Emprunt;
import edu.upc.models.dtos.EmpruntDto;
import edu.upc.repositories.EmpruntRepoCustom;
import edu.upc.utils.Mapper;
import edu.upc.utils.MyLibraryUtil;
import edu.upc.utils.exceptions.NotFoundException;

@Service
public class EmpruntServiceImpl implements EmpruntService {

	@Autowired
	private EmpruntRepoCustom repo;

	@Autowired
	private Mapper mapper;

	@Autowired
	private MyLibraryUtil libraryUtil;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Emprunt create(EmpruntDto dto, BindingResult result) {
		libraryUtil.validate(result);
		Emprunt row = mapper.mapToEmprunt(dto);
		long id = repo.create(row);
		return repo.getById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<Emprunt> delete(long id) {
		requireExists(id);
		repo.delete(id);
		return repo.get();
	}

	@Override
	public Emprunt getById(long id) {
		Emprunt row = repo.getById(id);
		if (row == null) {
			throw new NotFoundException("Emprunt introuvable");
		}
		return row;
	}

	@Override
	public List<Emprunt> get() {
		return repo.get();
	}

	private void requireExists(long id) {
		if (repo.getById(id) == null) {
			throw new NotFoundException("Emprunt introuvable");
		}
	}

}
