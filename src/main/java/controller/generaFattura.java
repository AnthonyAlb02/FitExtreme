package controller;

import java.io.IOException;
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
        UtenteDAO utenteDAO = new UtenteDAO();   // ⭐ MANCAVA!

        try {
            Ordine ordine = ordineDAO.doRetrieveByKey(idOrdine);
            System.out.println("ORDINE = " + ordine);

            List<DettaglioOrdine> dettagli = dettaglioDAO.doRetrieveByOrdine(idOrdine);
            System.out.println("DETTAGLI = " + dettagli);

            // ⭐ Recupero utente dalla sessione
            Utente utente = (Utente) request.getSession().getAttribute("utente");
            System.out.println("UTENTE (sessione) = " + utente);

            // ⭐ Se la sessione non contiene l’utente, lo recupero dal DB
            if (utente == null) {
                System.out.println("UTENTE NULL → recupero da ordine");
                utente = utenteDAO.doRetrieveByKey(ordine.getIdUtente());
                System.out.println("UTENTE (DB) = " + utente);
            }

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
