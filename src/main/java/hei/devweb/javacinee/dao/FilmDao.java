package hei.devweb.javacinee.dao;

import java.util.List;

import hei.devweb.javacinee.entities.Film;

public interface FilmDao {
	public List<Film> listFilms();

	public Film getFilm(Integer id);

	public Film addFilm(Film film);
}
