package hei.devweb.javacinee.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import hei.devweb.javacinee.dao.GenreDao;
import hei.devweb.javacinee.dao.mock.impl.GenreDaoMockImpl;
import hei.devweb.javacinee.entities.Genre;

public class GenreDaoTestCase {

	private GenreDao genreDao = new GenreDaoMockImpl();

	@Before
	public void initDb() throws Exception {
		try (Connection connection = DataSourceProvider.getDataSource().getConnection();
				Statement stmt = connection.createStatement()) {
			stmt.executeUpdate("DELETE FROM film");
			stmt.executeUpdate("DELETE FROM genre");
			stmt.executeUpdate("INSERT INTO `genre`(`genre_id`,`name`) VALUES (1,'Drama')");
			stmt.executeUpdate("INSERT INTO `genre`(`genre_id`,`name`) VALUES (2,'Comedy')");
			stmt.executeUpdate("INSERT INTO `genre`(`genre_id`,`name`) VALUES (3,'Action')");
		}
	}

	@Test
	public void shouldListGenres() {
		// WHEN
		List<Genre> genres = genreDao.listGenres();
		// THEN
		assertThat(genres).hasSize(3);
		assertThat(genres).extracting("id", "name").containsOnly(tuple(3, "Action"), tuple(2, "Comedy"),
				tuple(1, "Drama"));
	}

	@Test
	public void shouldGetGenre() {
		// WHEN
		Genre genre = genreDao.getGenre(1);
		// THEN
		assertThat(genre).isNotNull();
		assertThat(genre.getId()).isEqualTo(1);
		assertThat(genre.getName()).isEqualTo("Drama");
	}

	@Test
	public void shouldNotGetUnknownGenre() {
		// WHEN
		Genre genre = genreDao.getGenre(-1);
		// THEN
		assertThat(genre).isNull();
	}

	@Test
	public void shouldAddGenre() throws Exception {
		// WHEN
		genreDao.addGenre("test");
		// THEN
		try (Connection connection = DataSourceProvider.getDataSource().getConnection();
				Statement stmt = connection.createStatement()) {
			try (ResultSet rs = stmt.executeQuery("SELECT * FROM genre WHERE name = 'test'")) {
				assertThat(rs.next()).isTrue();
				assertThat(rs.getInt("genre_id")).isGreaterThan(0);
				assertThat(rs.getString("name")).isEqualTo("test");
				assertThat(rs.next()).isFalse();
			}
		}
	}

}
