package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DAO.OrdineDAO;
import model.beans.Ordine;
import model.beans.Utente;

@WebServlet("/ordini")
public class OrdiniUtenteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public OrdiniUtenteServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessione = request.getSession(false);

        // Utente non loggato = redirect login
        if (sessione == null || sessione.getAttribute("utente") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Utente utente = (Utente) sessione.getAttribute("utente");
        Integer idUtente = (Integer) sessione.getAttribute("id");

        OrdineDAO ordineDAO = new OrdineDAO();

        try {
            // Recupero SOLO gli ordini dell’utente loggato
            List<Ordine> ordini = (List<Ordine>) ordineDAO.doRetrieveByUser(idUtente);

            // Passo la lista alla JSP
            request.setAttribute("ordini", ordini);

            // Forward alla tua JSP
            request.getRequestDispatcher("/ordini.jsp")
                   .forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/profilo");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
