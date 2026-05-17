package controller.admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DAO.ArticoloDAO;
import model.beans.Articolo;

@WebServlet("/admin/insProdotto")
public class insProdottoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    RequestDispatcher dispatcher;

    public insProdottoServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Mostro la pagina con il form di inserimento prodotto
        dispatcher = getServletContext().getRequestDispatcher("/jsp/admin/articoli/insProdotto.jsp");

        HttpSession sessione = request.getSession(false);

        // Controllo che l'utente sia loggato
        if (sessione == null) {
            response.sendRedirect("../login");
            return;
        }

        // Controllo che sia admin
        String ruolo = (String) sessione.getAttribute("ruolo");
        if (ruolo == null || !ruolo.equals("admin")) {
            response.sendRedirect("../errorePermessi.jsp");
            return;
        }

        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recupero i dati inviati dal form
        String nome = request.getParameter("nome");
        String descrizione = request.getParameter("descrizione");
        String prezzo = request.getParameter("prezzo");
        String quantita = request.getParameter("quantita");
        String categoria = request.getParameter("categoria");

        HttpSession sessione = request.getSession(false);

        // Controllo login
        if (sessione == null) {
            response.sendRedirect("../login");
            return;
        }

        // Controllo ruolo admin
        String ruolo = (String) sessione.getAttribute("ruolo");
        if (ruolo == null || !ruolo.equals("admin")) {
            response.sendRedirect("../errorePermessi.jsp");
            return;
        }

        try {
            // Creo un nuovo oggetto Articolo e setto i campi
            Articolo a = new Articolo();

            a.setNomeArticolo(nome);
            a.setDescrizione(descrizione);
            a.setPrezzoListino(new BigDecimal(prezzo));
            a.setQtaDisponibile(Integer.parseInt(quantita));
            a.setIdCategoria(Integer.parseInt(categoria));

            // Salvo l'articolo nel DB
            ArticoloDAO dao = new ArticoloDAO();
            dao.doSave(a);

            // Dopo l'inserimento torno alla lista prodotti admin
            response.sendRedirect("prodottiAdmin");

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();

            // In caso di errore rimando al form
            request.setAttribute("errore", "Errore durante l'inserimento del prodotto.");
            dispatcher = getServletContext().getRequestDispatcher("/jsp/admin/articoli/insProdotto.jsp");
            dispatcher.forward(request, response);
        }
    }
}
