package controller.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DAO.ArticoloDAO;
import model.beans.Articolo;

@WebServlet("/admin/prodotti")
public class prodottiAdmin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    RequestDispatcher dispatcher;

    public prodottiAdmin() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Indico la JSP che mostra la lista degli articoli per l'admin
        dispatcher = getServletContext().getRequestDispatcher("/admin/prodotti.jsp");

        // Recupero la sessione dell'utente
        HttpSession sessione = request.getSession(false);

        // Se non c'è sessione → non è loggato
        if (sessione == null) {
            response.sendRedirect("../login");
            return;
        }

        // Controllo che l'utente sia admin
        Boolean isAdmin = (Boolean) sessione.getAttribute("isAdmin");

        if (isAdmin == null || !isAdmin) {
            response.sendRedirect("../errorePermessi");
            return;
        }


        try {
            // DAO per recuperare gli articoli
            ArticoloDAO articoloDAO = new ArticoloDAO();

            // Recupero tutti gli articoli dal DB
            Collection<Articolo> listaArticoli = articoloDAO.doRetrieveAll("ID_Articolo");

            // Passo la lista alla JSP
            request.setAttribute("listaArticoli", listaArticoli);

            // Mostro la pagina
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            dispatcher.forward(request, response);
        }
    }
}
