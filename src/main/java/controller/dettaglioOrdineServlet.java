package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DAO.OrdineDAO;
import model.DAO.DettaglioOrdineDAO;
import model.beans.Ordine;
import model.beans.DettaglioOrdine;

@WebServlet("/dettaglioOrdine")
public class dettaglioOrdineServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public dettaglioOrdineServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/dettaglioOrdine.jsp");

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect("storicoOrdini");
            return;
        }

        try {
            int idOrdine = Integer.parseInt(idParam);

            OrdineDAO ordineDAO = new OrdineDAO();
            DettaglioOrdineDAO dettaglioDAO = new DettaglioOrdineDAO();

            // Recupero ordine
            Ordine ordine = ordineDAO.doRetrieveByKey(idOrdine);

            if (ordine == null) {
                response.sendRedirect("storicoOrdini");
                return;
            }

            // Recupero dettagli ordine 
            List<DettaglioOrdine> dettagli = dettaglioDAO.doRetrieveByOrdine(idOrdine);

            // Calcolo totale
            BigDecimal totale = BigDecimal.ZERO;
            for (DettaglioOrdine d : dettagli) {
                totale = totale.add(d.getSubtotale());
            }

            // Passo i dati alla JSP
            request.setAttribute("ordine", ordine);
            request.setAttribute("dettagli", dettagli);
            request.setAttribute("totale", totale);

            dispatcher.forward(request, response);

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect("storicoOrdini");
        }
    }
}
