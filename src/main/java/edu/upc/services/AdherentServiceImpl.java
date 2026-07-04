package edu.upc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import edu.upc.models.Adherent;
import edu.upc.models.dtos.AdherentDto;
import edu.upc.repositories.AdherentRepoCustom;
import edu.upc.utils.Mapper;
import edu.upc.utils.MyLibraryUtil;
import edu.upc.utils.exceptions.NotFoundException;

@Service
public class AdherentServiceImpl implements AdherentService {

	@Autowired
	private AdherentRepoCustom repo;

	@Autowired
	private Mapper mapper;

	@Autowired
	private MyLibraryUtil libraryUtil;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Adherent create(AdherentDto dto, BindingResult result) {
		libraryUtil.validate(result);
		Adherent row = mapper.mapToAdherent(dto);
		long id = repo.create(row);
		return repo.getWithGenreById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Adherent update(long id, AdherentDto dto, BindingResult result) {
		libraryUtil.validate(result);
		requireExists(id);
		Adherent row = mapper.mapToAdherent(dto);
		repo.update(id, row);
		return repo.getWithGenreById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<Adherent> delete(long id) {
		requireExists(id);
		repo.delete(id);
		return repo.getWithGenre();
	}

	@Override
	public Adherent getById(long id) {
		Adherent row = repo.getWithGenreById(id);
		if (row == null) {
			throw new NotFoundException("Adherent introuvable");
		}
		return row;
	}

	@Override
	public List<Adherent> get() {
		return repo.getWithGenre();
	}

	@Override
	public List<Adherent> search(String keyword) {
		return repo.search(keyword);
	}

	private void requireExists(long id) {
		if (repo.getWithGenreById(id) == null) {
			throw new NotFoundException("Adherent introuvable");
		}
	}

}
