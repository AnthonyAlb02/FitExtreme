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

@WebServlet("/admin/modProdotto")
public class modProdottoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    RequestDispatcher dispatcher;

    public modProdottoServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Mostro la pagina con il form di modifica prodotto
        dispatcher = getServletContext().getRequestDispatcher("/jsp/admin/articoli/modProdotto.jsp");

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

        // Recupero l'ID del prodotto da modificare
        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect("prodottiAdmin");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);

            // Recupero il prodotto dal DB
            ArticoloDAO dao = new ArticoloDAO();
            Articolo prodotto = dao.doRetrieveByKey(id);

            // Se non esiste → torno alla lista
            if (prodotto == null) {
                response.sendRedirect("prodottiAdmin");
                return;
            }

            // Passo il prodotto alla JSP per precompilare il form
            request.setAttribute("prodotto", prodotto);

            dispatcher.forward(request, response);

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("prodottiAdmin");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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

        // Recupero i dati dal form
        String id = request.getParameter("id");
        String nome = request.getParameter("nome");
        String descrizione = request.getParameter("descrizione");
        String prezzo = request.getParameter("prezzo");
        String quantita = request.getParameter("quantita");
        String categoria = request.getParameter("categoria");

        try {
            ArticoloDAO dao = new ArticoloDAO();

            // Recupero il prodotto esistente
            Articolo a = dao.doRetrieveByKey(Integer.parseInt(id));

            if (a == null) {
                response.sendRedirect("prodottiAdmin");
                return;
            }

            // Aggiorno i campi del prodotto
            a.setNomeArticolo(nome);
            a.setDescrizione(descrizione);
            a.setPrezzoListino(new BigDecimal(prezzo));
            a.setQtaDisponibile(Integer.parseInt(quantita));
            a.setIdCategoria(Integer.parseInt(categoria));

            // Salvo le modifiche nel DB
            dao.doUpdate(a);

            // Torno alla lista prodotti admin
            response.sendRedirect("prodottiAdmin");

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();

            // In caso di errore rimando al form
            request.setAttribute("errore", "Errore durante la modifica del prodotto.");
            dispatcher = getServletContext().getRequestDispatcher("/jsp/admin/articoli/modProdotto.jsp");
            dispatcher.forward(request, response);
        }
    }
}
