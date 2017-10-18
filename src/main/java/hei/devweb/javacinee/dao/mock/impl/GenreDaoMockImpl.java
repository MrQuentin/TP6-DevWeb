package hei.devweb.javacinee.dao.mock.impl;

import hei.devweb.javacinee.dao.GenreDao;
import hei.devweb.javacinee.entities.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class GenreDaoMockImpl implements GenreDao {

	private TreeMap<Integer, Genre> genresList;

	public GenreDaoMockImpl() {
		genresList = new TreeMap<>();
		genresList.put(1, new Genre(1, "Drama"));
		genresList.put(2, new Genre(2, "Science fiction"));
		genresList.put(3, new Genre(3, "Horror"));
		genresList.put(4, new Genre(4, "Comedy"));
		genresList.put(5, new Genre(5, "Thriller"));
	}

	public List<Genre> listGenres() {
		return new ArrayList<>(genresList.values());
	}

	public Genre getGenre(Integer id) {
		return genresList.get(id);
	}

	public void addGenre(String nom) {
		Integer id = genresList.lastKey() + 1;
		Genre genre = new Genre(id, nom);
		genresList.put(id, genre);
	}
}
