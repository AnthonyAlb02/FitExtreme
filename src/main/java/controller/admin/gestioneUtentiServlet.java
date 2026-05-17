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

import model.DAO.UtenteDAO;
import model.beans.Utente;

@WebServlet("/admin/utenti")
public class gestioneUtentiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    RequestDispatcher dispatcher;

    public gestioneUtentiServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Indico la JSP che mostra la lista degli utenti
        dispatcher = getServletContext().getRequestDispatcher("/jsp/admin/utenti/utenti.jsp");

        // Recupero la sessione
        HttpSession sessione = request.getSession(false);

        // Se non c'è sessione → non è loggato
        if (sessione == null) {
            response.sendRedirect("../login");
            return;
        }

        // Controllo che l'utente sia admin
        String ruolo = (String) sessione.getAttribute("ruolo");
        if (ruolo == null || !ruolo.equals("admin")) {
            response.sendRedirect("../errorePermessi.jsp");
            return;
        }

        try {
            // DAO per recuperare gli utenti
            UtenteDAO utenteDAO = new UtenteDAO();

            // Recupero tutti gli utenti dal DB
            Collection<Utente> utenti = utenteDAO.doRetrieveAll("ID_Utente");

            // Passo la lista alla JSP
            request.setAttribute("utenti", utenti);

            // Mostro la pagina
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            dispatcher.forward(request, response);
        }
    }
}
