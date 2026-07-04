package edu.upc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import edu.upc.models.Commentaire;
import edu.upc.models.dtos.CommentaireDto;
import edu.upc.repositories.CommentaireRepoCustom;
import edu.upc.utils.Mapper;
import edu.upc.utils.MyLibraryUtil;
import edu.upc.utils.exceptions.NotFoundException;

@Service
public class CommentaireServiceImpl implements CommentaireService {

	@Autowired
	private CommentaireRepoCustom repo;

	@Autowired
	private Mapper mapper;

	@Autowired
	private MyLibraryUtil libraryUtil;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Commentaire create(CommentaireDto dto, BindingResult result) {
		libraryUtil.validate(result);
		Commentaire row = mapper.mapToCommentaire(dto);
		long id = repo.create(row);
		return repo.getById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<Commentaire> delete(long id) {
		requireExists(id);
		repo.delete(id);
		return repo.get();
	}

	@Override
	public Commentaire getById(long id) {
		Commentaire row = repo.getById(id);
		if (row == null) {
			throw new NotFoundException("Commentaire introuvable");
		}
		return row;
	}

	@Override
	public List<Commentaire> get() {
		return repo.get();
	}

	private void requireExists(long id) {
		if (repo.getById(id) == null) {
			throw new NotFoundException("Commentaire introuvable");
		}
	}

}
