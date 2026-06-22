package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DAO.ArticoloDAO;
import model.DAO.OrdineDAO;
import model.DAO.DettaglioOrdineDAO;
import model.beans.Articolo;
import model.beans.Ordine;
import model.beans.DettaglioOrdine;
import model.beans.Utente;
import utilities.MailSender;

@WebServlet("/confermaOrdine")
public class confermaOrdineServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/confermaOrdine.jsp")
               .forward(request, response);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessione = request.getSession(false);

        if (sessione == null || sessione.getAttribute("utente") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Utente utente = (Utente) sessione.getAttribute("utente");
        int idUtente = utente.getIdUtente();

        Map<Integer, Integer> carrello =
                (Map<Integer, Integer>) sessione.getAttribute("carrello");

        if (carrello == null || carrello.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/carrello");
            return;
        }

        // Validazione carta
        String nomeCarta   = request.getParameter("nomeCarta");
        String numeroCarta = request.getParameter("numeroCarta");
        String scadenza    = request.getParameter("scadenza");
        String cvv         = request.getParameter("cvv");

        if (nomeCarta   == null || nomeCarta.isBlank()                        ||
            numeroCarta == null || !numeroCarta.matches("[0-9]{16}")           ||
            scadenza    == null || !scadenza.matches("(0[1-9]|1[0-2])/[0-9]{2}") ||
            cvv         == null || !cvv.matches("[0-9]{3}")) {

            response.sendRedirect(request.getContextPath() + "/confermaOrdine?errore=campi");
            return;
        }

        try {
            ArticoloDAO articoloDAO = new ArticoloDAO();
            OrdineDAO ordineDAO = new OrdineDAO();
            DettaglioOrdineDAO dettaglioDAO = new DettaglioOrdineDAO();

            // Calcolo totale
            BigDecimal totale = BigDecimal.ZERO;

            for (Map.Entry<Integer, Integer> entry : carrello.entrySet()) {
                Articolo a = articoloDAO.doRetrieveByKey(entry.getKey());
                int qta = entry.getValue();
                totale = totale.add(a.getPrezzoListino().multiply(new BigDecimal(qta)));
            }

            // Calcolo imponibile e IVA
            BigDecimal iva = totale.multiply(new BigDecimal("22"))
                                   .divide(new BigDecimal("122"), 2, RoundingMode.HALF_UP);

            BigDecimal imponibile = totale.subtract(iva);

            // Data formattata
            String dataFormattata = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

            // Salvo ordine
            Ordine ordine = new Ordine();
            ordine.setIdUtente(idUtente);
            ordine.setIdAmministratore(null);
            ordine.setDataOrdine(LocalDate.now());
            ordine.setStatoAvanzamento("In elaborazione");
            ordine.setImportoTotale(totale);

            int idOrdine = ordineDAO.doSaveAndReturnKey(ordine);
            ordine.setIdOrdine(idOrdine);

            // Salvo dettagli e aggiorno quantità
            for (Map.Entry<Integer, Integer> entry : carrello.entrySet()) {
                int idArticolo = entry.getKey();
                int qta = entry.getValue();
                Articolo a = articoloDAO.doRetrieveByKey(idArticolo);

                DettaglioOrdine dett = new DettaglioOrdine();
                dett.setIdOrdine(idOrdine);
                dett.setIdArticolo(idArticolo);
                dett.setQuantita(qta);
                dett.setPrezzoAcquisto(a.getPrezzoListino());
                dett.setSubtotale(a.getPrezzoListino().multiply(new BigDecimal(qta)));
                dett.setNomeArticolo(a.getNomeArticolo());
                dett.setImmagine(a.getImmagine());

                dettaglioDAO.doSave(dett);

                a.setQtaDisponibile(a.getQtaDisponibile() - qta);
                articoloDAO.doUpdate(a);
            }

            // Svuoto carrello
            sessione.removeAttribute("carrello");
            sessione.setAttribute("cartCount", 0);

            // Recupero dettagli ordine per la mail
            List<DettaglioOrdine> dettagliMail = dettaglioDAO.doRetrieveByOrdine(idOrdine);

            // 🔥 PASSO TUTTO ALLA REQUEST PER LA JSP EMAIL
            request.setAttribute("ordine", ordine);
            request.setAttribute("utente", utente);
            request.setAttribute("dettagli", dettagliMail);

            request.setAttribute("totale", totale);
            request.setAttribute("iva", iva);
            request.setAttribute("imponibile", imponibile);
            request.setAttribute("dataFormattata", dataFormattata);

            // Invio email
            try {
                MailSender.inviaConfermaOrdine(request, response, utente, ordine, dettagliMail);
            } catch (Exception mailEx) {
                mailEx.printStackTrace();
            }

            // Redirect finale
            response.sendRedirect(request.getContextPath() + "/ordineCompletato?id=" + idOrdine);

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/plain");
            response.getWriter().println("ERRORE:");
            response.getWriter().println(e.getClass().getName());
            response.getWriter().println(e.getMessage());
        }
    }
}
