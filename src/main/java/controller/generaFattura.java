package controller;

import model.DAO.OrdineDAO;
import model.DAO.DettaglioOrdineDAO;
import model.DAO.UtenteDAO;
import model.beans.Ordine;
import model.beans.DettaglioOrdine;
import model.beans.Utente;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@WebServlet("/generaFattura")
public class generaFattura extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Controllo sessione
        if (session == null || session.getAttribute("utente") == null) {
            response.sendRedirect("login");
            return;
        }

        // Recupero idOrdine dal parametro o dalla sessione
        String idOrdineParam = request.getParameter("idOrdine");

        if (idOrdineParam == null) {
            Object last = session.getAttribute("lastOrderId");
            if (last != null) {
                idOrdineParam = String.valueOf(last);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }

        try {
            int idOrdine = Integer.parseInt(idOrdineParam);

            // Salvo l'ultimo ordine per permettere il refresh
            session.setAttribute("lastOrderId", idOrdine);

            OrdineDAO ordineDAO = new OrdineDAO();
            DettaglioOrdineDAO dettaglioDAO = new DettaglioOrdineDAO();
            UtenteDAO utenteDAO = new UtenteDAO();

            Ordine ordine = ordineDAO.doRetrieveByKey(idOrdine);
            List<DettaglioOrdine> dettagli = dettaglioDAO.doRetrieveByOrdine(idOrdine);
            Utente utente = (Utente) session.getAttribute("utente");

            // Calcolo totali
            BigDecimal totale = BigDecimal.ZERO;
            for (DettaglioOrdine d : dettagli) {
                totale = totale.add(d.getSubtotale());
            }

            BigDecimal iva = totale
                    .multiply(new BigDecimal("22"))
                    .divide(new BigDecimal("122"), 2, RoundingMode.HALF_UP);

            BigDecimal imponibile = totale.subtract(iva);

            // Passo i dati alla JSP
            request.setAttribute("ordine", ordine);
            request.setAttribute("dettagli", dettagli);
            request.setAttribute("utente", utente);
            request.setAttribute("totale", totale);
            request.setAttribute("iva", iva);
            request.setAttribute("imponibile", imponibile);

            RequestDispatcher rd = request.getRequestDispatcher("fattura.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
