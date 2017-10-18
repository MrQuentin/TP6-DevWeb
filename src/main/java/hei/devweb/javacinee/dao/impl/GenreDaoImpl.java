package hei.devweb.javacinee.dao.impl;

import hei.devweb.javacinee.dao.GenreDao;
import hei.devweb.javacinee.entities.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreDaoImpl implements GenreDao {


    @Override
    public List<Genre> listGenres() {
        List<Genre> listOfGenres = new ArrayList<>();
        String querry = "SELECT * FROM genre ORDER BY name";
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet results =
                             statement.executeQuery(querry)) {
                    while (results.next()) {
                       Genre genre = new Genre(
                                results.getInt("genre_id"),
                                results.getString("name"));
                        listOfGenres.add(genre);
                    }
                }
            }
        } catch (SQLException e) {
            // Manage Exception
            e.printStackTrace();
        }
        return listOfGenres;
    }

    @Override
    public Genre getGenre(Integer id) {
        String querry = "SELECT * FROM genre WHERE genre_id=?";
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(querry)) {
                statement.setInt(1, id);
                try (ResultSet results = statement.executeQuery()) {
                    if(results.next()) {
                        return new Genre(results.getInt("genre_id"), results.getString("name"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addGenre(String nom) {

        try (Connection connection = DataSourceProvider.getDataSource().getConnection()){

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
