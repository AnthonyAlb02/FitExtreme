package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.DAO.DettaglioOrdineDAO;
import model.DAO.OrdineDAO;
import model.beans.DettaglioOrdine;
import model.beans.Ordine;
import model.beans.Utente;
import utilities.InvoiceService;

@WebServlet("/ordineCompletato")
public class ordineCompletatoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        if (id == null) {
            response.sendRedirect("home");
            return;
        }

        int idOrdine = Integer.parseInt(id);

        // Recupero dati ordine
        OrdineDAO ordineDAO = new OrdineDAO();
        DettaglioOrdineDAO dettaglioDAO = new DettaglioOrdineDAO();

        Ordine ordine = null;
		try {
			ordine = ordineDAO.doRetrieveByKey(idOrdine);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        List<DettaglioOrdine> dettagli = null;
		try {
			dettagli = dettaglioDAO.doRetrieveByOrdine(idOrdine);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        Utente utente = (Utente) request.getSession().getAttribute("utente");

        // ⭐ GENERAZIONE FATTURA QUI (punto giusto)
     // ⭐ CHIAMO LA SERVLET CHE GENERA LA FATTURA
        request.setAttribute("ordine", ordine);
        request.setAttribute("dettagli", dettagli);
        request.setAttribute("utente", utente);

        // Chiamata interna alla servlet generaFattura
        request.getRequestDispatcher("/generaFattura?idOrdine=" + idOrdine)
               .include(request, response);


        request.setAttribute("idOrdine", idOrdine);

        RequestDispatcher dispatcher =
                getServletContext().getRequestDispatcher("/ordineCompletato.jsp");
        dispatcher.forward(request, response);
    }
}

