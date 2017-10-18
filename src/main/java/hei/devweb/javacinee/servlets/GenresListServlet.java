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
import java.util.List;

@WebServlet("/genres")
public class GenresListServlet extends GenericServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        List<Genre> genres = FilmLibrary.getInstance().listGenres();
        context.setVariable("genresList", genres);

        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("genres", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        FilmLibrary.getInstance().addGenre(name);
        resp.sendRedirect("genres");
    }
}
