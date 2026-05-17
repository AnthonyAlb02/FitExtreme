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
import javax.servlet.http.HttpSession;

import model.DAO.OrdineDAO;
import model.beans.Ordine;

@WebServlet("/storicoOrdini")
public class storicoOrdiniServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    RequestDispatcher dispatcher;

    public storicoOrdiniServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Indico la JSP che mostrerà lo storico degli ordini
        dispatcher = getServletContext().getRequestDispatcher("/jsp/ordine/storicoOrdini.jsp");

        // Recupero la sessione dell'utente
        HttpSession sessione = request.getSession(false);

        // Se non c'è sessione → l'utente non è loggato
        if (sessione == null) {
            response.sendRedirect("login");
            return;
        }

        // Recupero l'ID dell'utente loggato
        Integer idUtente = (Integer) sessione.getAttribute("idUtente");

        // Se non è loggato → lo mando al login
        if (idUtente == null) {
            response.sendRedirect("login");
            return;
        }

        try {
            // DAO per recuperare gli ordini
            OrdineDAO ordineDAO = new OrdineDAO();

            // Recupero tutti gli ordini dell'utente
            // (uso una query filtrata che aggiungeremo nel DAO)
            Collection<Ordine> ordini = ordineDAO.doRetrieveByUser(idUtente);

            // Passo la lista ordini alla JSP
            request.setAttribute("ordini", ordini);

            // Mostro la pagina dello storico
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            dispatcher.forward(request, response);
        }
    }
}
