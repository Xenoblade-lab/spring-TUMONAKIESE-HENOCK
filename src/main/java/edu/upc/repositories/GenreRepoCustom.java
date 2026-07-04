package edu.upc.repositories;

import java.util.List;

import edu.upc.models.Genre;

public interface GenreRepoCustom {

	long create(Genre entity);

	void update(long id, Genre entity);

	void delete(long id);

	Genre getById(long id);

	List<Genre> get();

}
