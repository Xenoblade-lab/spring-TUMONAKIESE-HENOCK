package edu.upc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import edu.upc.models.Article;
import edu.upc.models.dtos.ArticleDto;
import edu.upc.repositories.ArticleRepoCustom;
import edu.upc.utils.Mapper;
import edu.upc.utils.MyLibraryUtil;
import edu.upc.utils.exceptions.NotFoundException;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleRepoCustom repo;

	@Autowired
	private Mapper mapper;

	@Autowired
	private MyLibraryUtil libraryUtil;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Article create(ArticleDto dto, BindingResult result) {
		libraryUtil.validate(result);
		Article row = mapper.mapToArticle(dto);
		long id = repo.create(row);
		return repo.getById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Article update(long id, ArticleDto dto, BindingResult result) {
		libraryUtil.validate(result);
		requireExists(id);
		Article row = mapper.mapToArticle(dto);
		repo.update(id, row);
		return repo.getById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<Article> delete(long id) {
		requireExists(id);
		repo.delete(id);
		return repo.get();
	}

	@Override
	public Article getById(long id) {
		Article row = repo.getById(id);
		if (row == null) {
			throw new NotFoundException("Article introuvable");
		}
		return row;
	}

	@Override
	public List<Article> get() {
		return repo.get();
	}

	private void requireExists(long id) {
		if (repo.getById(id) == null) {
			throw new NotFoundException("Article introuvable");
		}
	}

}
