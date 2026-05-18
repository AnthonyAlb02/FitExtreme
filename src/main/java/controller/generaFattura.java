package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.DAO.DettaglioOrdineDAO;
import model.DAO.OrdineDAO;
import model.DAO.UtenteDAO;
import model.beans.DettaglioOrdine;
import model.beans.Ordine;
import model.beans.Utente;
import utilities.InvoiceService;

@WebServlet("/generaFattura")
public class generaFattura extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String id = request.getParameter("idOrdine");
        System.out.println("ID ORDINE RICEVUTO = " + id);

        if (id == null) {
            System.out.println("ERRORE: idOrdine è null");
            response.setStatus(400);
            return;
        }

        int idOrdine = Integer.parseInt(id);

        OrdineDAO ordineDAO = new OrdineDAO();
        DettaglioOrdineDAO dettaglioDAO = new DettaglioOrdineDAO();
        UtenteDAO utenteDAO = new UtenteDAO();

        try {
            Ordine ordine = ordineDAO.doRetrieveByKey(idOrdine);
            System.out.println("ORDINE = " + ordine);

            List<DettaglioOrdine> dettagli = dettaglioDAO.doRetrieveByOrdine(idOrdine);
            System.out.println("DETTAGLI = " + dettagli);

            // Recupero utente
            Utente utente = (Utente) request.getSession().getAttribute("utente");
            if (utente == null) {
                utente = utenteDAO.doRetrieveByKey(ordine.getIdUtente());
            }

            // ⭐ Calcolo IVA scorporata dal totale
            BigDecimal totale = ordine.getImportoTotale();
            BigDecimal iva = totale
                    .multiply(new BigDecimal("22"))
                    .divide(new BigDecimal("122"), 2, RoundingMode.HALF_UP);

            // ⭐ Passo l’IVA alla JSP della fattura
            request.setAttribute("iva", iva);

            // Genero PDF
            byte[] pdfBytes = InvoiceService.generateInvoicePDFBytes(ordine, dettagli, utente);
            System.out.println("PDF GENERATO, SIZE = " + pdfBytes.length);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=fattura_" + idOrdine + ".pdf");
            response.getOutputStream().write(pdfBytes);

        } catch (Exception e) {
            System.out.println("ERRORE DURANTE GENERAZIONE FATTURA:");
            e.printStackTrace();
            response.setStatus(500);
        }
    }
}
