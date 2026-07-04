package edu.upc.repositories;

import java.util.List;

import edu.upc.models.Article;

public interface ArticleRepoCustom {

	long create(Article entity);

	void update(long id, Article entity);

	void delete(long id);

	Article getById(long id);

	List<Article> get();

}
