package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DAO.ArticoloDAO;
import model.DAO.OrdineDAO;
import model.DAO.DettaglioOrdineDAO;
import model.beans.Articolo;
import model.beans.Ordine;
import model.beans.DettaglioOrdine;

@WebServlet("/dettaglioOrdine")
public class dettaglioOrdineServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    RequestDispatcher dispatcher;

    public dettaglioOrdineServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Indico la JSP che mostrerà il dettaglio dell'ordine
        dispatcher = getServletContext().getRequestDispatcher("/dettaglioOrdine.jsp");

        // Recupero l'ID dell'ordine passato come parametro
        String idParam = request.getParameter("id");

        // Se non c'è ID → non posso mostrare nulla
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect("storicoOrdini");
            return;
        }

        try {
            int idOrdine = Integer.parseInt(idParam);

            // DAO necessari per recuperare ordine e dettagli
            OrdineDAO ordineDAO = new OrdineDAO();
            DettaglioOrdineDAO dettaglioDAO = new DettaglioOrdineDAO();
            ArticoloDAO articoloDAO = new ArticoloDAO();

            // Recupero l'ordine dal DB
            Ordine ordine = ordineDAO.doRetrieveByKey(idOrdine);

            // Se l'ordine non esiste → torno allo storico
            if (ordine == null) {
                response.sendRedirect("storicoOrdini");
                return;
            }

            // Recupero tutti i dettagli dell'ordine
            List<DettaglioOrdine> dettagli = dettaglioDAO.doRetrieveByOrdine(idOrdine);

            // Lista degli articoli completi (per mostrare nome, prezzo, ecc.)
            List<Articolo> articoli = new ArrayList<>();

            // Calcolo totale (anche se già presente nell'ordine, lo ricalcolo per sicurezza)
            BigDecimal totale = BigDecimal.ZERO;

            for (DettaglioOrdine d : dettagli) {
                // Recupero l'articolo collegato al dettaglio
                Articolo a = articoloDAO.doRetrieveByKey(d.getIdArticolo());
                articoli.add(a);

                // Aggiungo il subtotale al totale generale
                totale = totale.add(d.getSubtotale());
            }

            // Passo tutto alla JSP
            request.setAttribute("ordine", ordine);
            request.setAttribute("dettagli", dettagli);
            request.setAttribute("articoli", articoli);
            request.setAttribute("totale", totale);

            // Mostro la pagina del dettaglio ordine
            dispatcher.forward(request, response);

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect("storicoOrdini");
        }
    }
}
