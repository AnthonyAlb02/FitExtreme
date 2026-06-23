package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DAO.UtenteDAO;
import model.beans.Utente;

@WebServlet("/modUtente")
public class ModUtenteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    RequestDispatcher dispatcher;

    public ModUtenteServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessione = request.getSession(false);
        dispatcher = getServletContext().getRequestDispatcher("/jsp/utente/profilo.jsp");

        //  Controllo sessione
        if (sessione == null || sessione.getAttribute("id") == null) {
            dispatcher = getServletContext().getRequestDispatcher("/jsp/utente/login.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            //  Recupero ID utente dalla sessione
            int idUtente = (int) sessione.getAttribute("id");

            // Recupero utente dal DB
            UtenteDAO model = new UtenteDAO();
            Utente utente = model.doRetrieveByKey(idUtente);

            if (utente == null) {
                dispatcher.forward(request, response);
                return;
            }

            //  Leggo i nuovi dati dal form
            String nome = request.getParameter("nome");
            String cognome = request.getParameter("cognome");
            String telefono = request.getParameter("telefono");
            String indirizzo = request.getParameter("indirizzo");

            // Aggiorno i campi
            utente.setNome(nome);
            utente.setCognome(cognome);
            utente.setTelefono(telefono);
            utente.setIndirizzoSpedizione(indirizzo);

            // Salvo nel DB
            model.doUpdate(utente);

            // Aggiorno la request
            request.setAttribute("utente", utente);
            request.setAttribute("successo", "Dati aggiornati correttamente");

            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
