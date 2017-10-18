package hei.devweb.javacinee.servlets;

import hei.devweb.javacinee.entities.Film;
import hei.devweb.javacinee.entities.Genre;
import hei.devweb.javacinee.managers.FilmLibrary;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/newfilm")
public class AddFilmServlet extends GenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        List<Genre> genres = FilmLibrary.getInstance().listGenres();
        context.setVariable("genresList", genres);

        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("newfilm", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // GET PARAMETERS
        String title = req.getParameter("title");
        String director = req.getParameter("director");
        String summary = req.getParameter("summary");
        Integer duration = Integer.parseInt(req.getParameter("duration"));
        Genre genre = FilmLibrary.getInstance().getGenre(Integer.parseInt(req.getParameter("genre")));

        String releaseDateAsString = req.getParameter("releaseDate");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate releaseDate = LocalDate.parse(releaseDateAsString, dateFormat);

        // CREATE FILM
        Film newFilm = new Film(null, title, releaseDate, genre, duration, director, summary);
        Film createdFilm = FilmLibrary.getInstance().addFilm(newFilm);

        // REDIRECT TO DETAIL FILM
        resp.sendRedirect(String.format("film?id=%d", createdFilm.getId()));
    }
}
