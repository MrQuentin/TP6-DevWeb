package hei.devweb.javacinee.servlets;

import hei.devweb.javacinee.entities.Film;
import hei.devweb.javacinee.managers.FilmLibrary;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/film")
public class DetailFilmServlet extends GenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());

        String filmId = req.getParameter("id");
        Film film = FilmLibrary.getInstance().getFilm(Integer.parseInt(filmId));
        context.setVariable("film", film);

        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("film", context, resp.getWriter());
    }
}
