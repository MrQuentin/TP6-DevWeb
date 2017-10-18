package hei.devweb.javacinee.dao.impl;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import hei.devweb.javacinee.dao.FilmDao;
import hei.devweb.javacinee.entities.Film;
import hei.devweb.javacinee.entities.Genre;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class FilmDaoTestCase {
	private FilmDao filmDao = new FilmDoaImpl();

	@Before
	public void initDb() throws Exception {
		try (Connection connection = DataSourceProvider.getDataSource().getConnection();
			 Statement stmt = connection.createStatement()) {
			stmt.executeUpdate("DELETE FROM film");
			stmt.executeUpdate("DELETE FROM genre");
			stmt.executeUpdate("INSERT INTO `genre`(`genre_id`,`name`) VALUES (1,'Drama')");
			stmt.executeUpdate("INSERT INTO `genre`(`genre_id`,`name`) VALUES (2,'Comedy')");
			stmt.executeUpdate(
					"INSERT INTO `film`(`film_id`,`title`, release_date, genre_id, duration, director, summary) "
							+ "VALUES (1, 'my title 1', '2014-11-26', 1, 120, 'director #1', 'summary')");
			stmt.executeUpdate(
					"INSERT INTO `film`(`film_id`,`title`, release_date, genre_id, duration, director, summary) "
							+ "VALUES (2, 'my title 2', '2014-10-26', 2, 165, 'director #2', 'summary')");
		}
	}

	@Test
	public void shouldListFilm() {
		// WHEN
		List<Film> films = filmDao.listFilms();
		// THEN
		Assertions.assertThat(films).hasSize(2);
		Assertions.assertThat(films).extracting("id", "title", "releaseDate", "genre.id", "genre.name", "duration", "director", "summary").containsOnly(
				Assertions.tuple(1, "my title 1", LocalDate.of(2014, 11, 26), 1, "Drama", 120, "director #1", "summary"),
				Assertions.tuple(2, "my title 2", LocalDate.of(2014, 10, 26), 2, "Comedy", 165, "director #2", "summary")
		);

	}

	@Test
	public void shouldGetFilm() {
		// WHEN
		Film film = filmDao.getFilm(1);
		// THEN
		Assertions.assertThat(film).isNotNull();
		Assertions.assertThat(film.getId()).isEqualTo(1);
		Assertions.assertThat(film.getTitle()).isEqualTo("my title 1");
		Assertions.assertThat(film.getReleaseDate()).isEqualTo(LocalDate.of(2014, 11, 26));
		Assertions.assertThat(film.getGenre().getId()).isEqualTo(1);
		Assertions.assertThat(film.getGenre().getName()).isEqualTo("Drama");
		Assertions.assertThat(film.getDuration()).isEqualTo(120);
		Assertions.assertThat(film.getDirector()).isEqualTo("director #1");
		Assertions.assertThat(film.getSummary()).isEqualTo("summary");
	}

	@Test
	public void shouldNotGetFilm() {
		// WHEN
		Film film = filmDao.getFilm(0);
		// THEN
		Assertions.assertThat(film).isNull();
	}

	@Test
	public void shouldAddFilm() throws Exception {
		// GIVEN
		Film filmToAdd = new Film(null, "New title", LocalDate.of(2016, 11, 16), new Genre(1, "Drama"), 123, "New director", "New summary");
		// WHEN
		Film filmAdded = filmDao.addFilm(filmToAdd);
		// THEN
		Assertions.assertThat(filmAdded).isNotNull();
		Assertions.assertThat(filmAdded.getId()).isNotNull();
		Assertions.assertThat(filmAdded.getTitle()).isEqualTo("New title");
		Assertions.assertThat(filmAdded.getReleaseDate()).isEqualTo(LocalDate.of(2016, 11, 16));
		Assertions.assertThat(filmAdded.getGenre().getId()).isEqualTo(1);
		Assertions.assertThat(filmAdded.getGenre().getName()).isEqualTo("Drama");
		Assertions.assertThat(filmAdded.getDuration()).isEqualTo(123);
		Assertions.assertThat(filmAdded.getDirector()).isEqualTo("New director");
		Assertions.assertThat(filmAdded.getSummary()).isEqualTo("New summary");

		try (Connection connection = DataSourceProvider.getDataSource().getConnection();
			 PreparedStatement stmt = connection.prepareStatement("SELECT * FROM film WHERE film_id = ?")) {
			stmt.setInt(1, filmAdded.getId());
			try (ResultSet rs = stmt.executeQuery()) {
				assertThat(rs.next()).isTrue();
				assertThat(rs.getInt("film_id")).isEqualTo(filmAdded.getId());
				assertThat(rs.getString("title")).isEqualTo("New title");
				assertThat(rs.getDate("release_date").toLocalDate()).isEqualTo(LocalDate.of(2016, 11, 16));
				assertThat(rs.getInt("genre_id")).isEqualTo(1);
				assertThat(rs.getInt("duration")).isEqualTo(123);
				assertThat(rs.getString("director")).isEqualTo("New director");
				assertThat(rs.getString("summary")).isEqualTo("New summary");
				assertThat(rs.next()).isFalse();
			}
		}

	}
}
