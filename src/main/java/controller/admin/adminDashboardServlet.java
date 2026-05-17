package controller.admin;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DAO.ArticoloDAO;
import model.DAO.OrdineDAO;
import model.DAO.UtenteDAO;

@WebServlet("/admin/dashboard")
public class adminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    RequestDispatcher dispatcher;

    public adminDashboardServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Indico la JSP della dashboard admin
        dispatcher = getServletContext().getRequestDispatcher("/jsp/admin/dashboard.jsp");

        // Recupero la sessione
        HttpSession sessione = request.getSession(false);

        // Se non c'è sessione → non è loggato
        if (sessione == null) {
            response.sendRedirect("../login");
            return;
        }

        // Controllo che l'utente sia admin
        String ruolo = (String) sessione.getAttribute("ruolo");

        // Se non è admin → lo mando via
        if (ruolo == null || !ruolo.equals("admin")) {
            response.sendRedirect("../errorePermessi.jsp");
            return;
        }

        try {
            // DAO per recuperare statistiche
            ArticoloDAO articoloDAO = new ArticoloDAO();
            OrdineDAO ordineDAO = new OrdineDAO();
            UtenteDAO utenteDAO = new UtenteDAO();

            // Recupero il numero totale di articoli
            int numeroArticoli = articoloDAO.doRetrieveAll(null).size();

            // Recupero il numero totale di ordini
            int numeroOrdini = ordineDAO.doRetrieveAll(null).size();

            // Recupero il numero totale di utenti
            int numeroUtenti = utenteDAO.doRetrieveAll(null).size();

            // Passo i dati alla JSP
            request.setAttribute("numeroArticoli", numeroArticoli);
            request.setAttribute("numeroOrdini", numeroOrdini);
            request.setAttribute("numeroUtenti", numeroUtenti);

            // Mostro la dashboard
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            dispatcher.forward(request, response);
        }
    }
}
