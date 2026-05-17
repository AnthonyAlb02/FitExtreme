package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DAO.ArticoloDAO;
import model.DAO.CategoriaDAO;
import model.beans.Articolo;
import model.beans.Categoria;

@WebServlet("/catalogo")
public class CatalogoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CatalogoServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            ArticoloDAO articoloDAO = new ArticoloDAO();

            // 1) Carico tutte le categorie
            Collection<Categoria> categorie = categoriaDAO.doRetrieveAll("Nome");
            request.setAttribute("categorie", categorie);

            // 2) Parametri
            String idParam = request.getParameter("id");
            String q = request.getParameter("q");

            Collection<Articolo> prodotti;

            // ⭐ 3) Se c'è una ricerca → PRIORITÀ
            if (q != null && !q.trim().isEmpty()) {

                prodotti = articoloDAO.doSearch(q.trim());
                request.setAttribute("prodotti", prodotti);
                request.setAttribute("categoriaSelezionata", null);
                request.setAttribute("searchQuery", q);

            }
            // ⭐ 4) Se c'è una categoria → filtro per categoria
            else if (idParam != null && !idParam.isEmpty()) {

                int idCategoria = Integer.parseInt(idParam);
                prodotti = articoloDAO.doRetrieveByCategoria(idCategoria);

                request.setAttribute("prodotti", prodotti);
                request.setAttribute("categoriaSelezionata", idCategoria);

            }
            // ⭐ 5) Nessun filtro → mostra tutto
            else {

                prodotti = articoloDAO.doRetrieveAll("Nome_Articolo");

                request.setAttribute("prodotti", prodotti);
                request.setAttribute("categoriaSelezionata", null);
            }

            // 6) Forward alla JSP
            RequestDispatcher dispatcher =
                    getServletContext().getRequestDispatcher("/catalogo.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Errore nel caricamento del catalogo");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
