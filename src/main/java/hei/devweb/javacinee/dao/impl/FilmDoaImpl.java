package hei.devweb.javacinee.dao.impl;

import hei.devweb.javacinee.dao.FilmDao;
import hei.devweb.javacinee.entities.Film;
import hei.devweb.javacinee.entities.Genre;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static hei.devweb.javacinee.dao.impl.DataSourceProvider.getDataSource;

public class FilmDoaImpl implements FilmDao{
    @Override
    public List<Film> listFilms() {
        DataSource dataSource= getDataSource();
        List<Film> films=new ArrayList<>();
        try(Connection connection=getDataSource().getConnection()){
            try(Statement statement = connection.createStatement()){
                try(ResultSet results = statement.executeQuery("SELECT * FROM film JOIN genre ON film.genre_id = genre.genre_id\n" +
                        "ORDER BY title;")){
                    while(results.next()){
                        Genre genre = new Genre(results.getInt("genre_id"), results.getString("name"));
                        Film film =new Film(results.getInt("film_id"),results.getString("title"),results.getDate("release_date").toLocalDate(),genre,results.getInt("duration"),results.getString("director"),results.getString("summary"));
                        films.add(film);

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return films;
    }

    @Override
    public Film getFilm(Integer id) {
        try(Connection connection = getDataSource().getConnection()){
            try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM film JOIN genre ON film.genre_id = genre.genre_id WHERE film_id=?")){
                statement.setInt(1,id);
                try(ResultSet results = statement.executeQuery()){
                    if (results.next()){
                        Genre genre = new Genre(results.getInt("genre_id"),results.getString("name"));
                        Film film=  new Film(
                                results.getInt("film_id"),
                                results.getString("title"),
                                results.getDate("release_date").toLocalDate(),
                                genre,
                                results.getInt("duration"),
                                results.getString("director"),
                                results.getString("summary"));
                        return film;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }

    @Override
    public Film randomFilm() {
        String querry = "select * from film JOIN genre ON film.genre_id = genre.genre_id ORDER BY rand() LIMIT 1";
        try(Connection connection = getDataSource().getConnection()){
            try(Statement statement = connection.createStatement()){
                try(ResultSet results = statement.executeQuery(querry)){
                    if (results.next()){
                        Genre genre = new Genre(results.getInt("genre_id"),results.getString("name"));
                        Film film=  new Film(
                                results.getInt("film_id"),
                                results.getString("title"),
                                results.getDate("release_date").toLocalDate(),
                                genre,
                                results.getInt("duration"),
                                results.getString("director"),
                                results.getString("summary"));
                        return film;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }

    @Override
    public Film addFilm(Film film) {
        try (Connection connection = getDataSource().getConnection()) {
            String sqlQuery="insert into film(title,release_date,genre_id,duration,director,summary) VALUES (?,?,?,?,?,?)";
            try(PreparedStatement statement = connection.prepareStatement(sqlQuery,Statement.RETURN_GENERATED_KEYS)){
                statement.setString(1,film.getTitle());
                statement.setDate(2,Date.valueOf(film.getReleaseDate()));
                statement.setInt(3,film.getGenre().getId());
                statement.setInt(4,film.getDuration());
                statement.setString(5,film.getDirector());
                statement.setString(6,film.getSummary());
                statement.executeUpdate();

                ResultSet rez = statement.getGeneratedKeys();
                if (rez.next()){
                    return new Film(rez.getInt(1),film.getTitle(),film.getReleaseDate(),film.getGenre(),film.getDuration(),film.getDirector(),film.getSummary());
                }


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
