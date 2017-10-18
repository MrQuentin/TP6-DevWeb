package hei.devweb.javacinee.dao;

import java.util.List;

import hei.devweb.javacinee.entities.Genre;

public interface GenreDao {
	public List<Genre> listGenres();

	public Genre getGenre(Integer id);

	public void addGenre(String nom);
}
