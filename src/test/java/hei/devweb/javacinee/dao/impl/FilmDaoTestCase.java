package hei.devweb.javacinee.dao.impl;

import java.sql.Connection;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import hei.devweb.javacinee.dao.FilmDao;
import hei.devweb.javacinee.dao.mock.impl.FilmDaoMockImpl;

import static org.assertj.core.api.Assertions.fail;

public class FilmDaoTestCase {
	private FilmDao filmDao = new FilmDaoMockImpl();

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
		fail("Not yet implemented");

	}

	@Test
	public void shouldGetFilm() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldAddFilm() throws Exception {
		fail("Not yet implemented");
	}
}
