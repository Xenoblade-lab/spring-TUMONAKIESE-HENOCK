package edu.upc.services;

import java.util.List;

import org.springframework.validation.BindingResult;

import edu.upc.models.Article;
import edu.upc.models.dtos.ArticleDto;

public interface ArticleService {

	Article create(ArticleDto dto, BindingResult result);

	Article update(long id, ArticleDto dto, BindingResult result);

	List<Article> delete(long id);

	Article getById(long id);

	List<Article> get();

}
