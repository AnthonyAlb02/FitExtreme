package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DAO.UtenteDAO;
import model.beans.Utente;

@WebServlet("/profilo")
public class ProfiloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    RequestDispatcher dispatcher;

    public ProfiloServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessione = request.getSession(false);
        dispatcher = getServletContext().getRequestDispatcher("/profilo.jsp");

        // 1) Se non c’è sessione → login
        if (sessione == null || sessione.getAttribute("id") == null) {
            dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            // 2) Recupero ID utente dalla sessione
            int idUtente = (int) sessione.getAttribute("id");

            // 3) Recupero utente aggiornato dal DB
            UtenteDAO model = new UtenteDAO();
            Utente utente = model.doRetrieveByKey(idUtente);

            // 4) Metto l’utente nella request
            request.setAttribute("utente", utente);

            // 5) Forward alla JSP del profilo
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
