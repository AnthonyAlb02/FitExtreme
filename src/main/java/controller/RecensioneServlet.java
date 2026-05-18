package controller;

import model.DAO.RecensioneDAO;
import model.beans.Recensione;
import model.beans.Utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/addReview")
public class RecensioneServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // 1) Controllo login
        Utente utente = (session != null) ? (Utente) session.getAttribute("utente") : null;
        if (utente == null) {
            session = request.getSession();
            session.setAttribute("errore", "Devi essere loggato per lasciare una recensione.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            // 2) Leggo parametri
            int idArticolo = Integer.parseInt(request.getParameter("idArticolo"));
            int voto = Integer.parseInt(request.getParameter("voto"));
            String commento = request.getParameter("commento");

            RecensioneDAO dao = new RecensioneDAO();

            // 3) Controllo se l’utente ha già recensito questo articolo
            if (dao.hasUserReviewed(utente.getIdUtente(), idArticolo)) {
                session.setAttribute("errore", "Hai già recensito questo prodotto.");
                response.sendRedirect(request.getContextPath() + "/prodotto?id=" + idArticolo);
                return;
            }

            // 4) Creo la recensione
            Recensione r = new Recensione();
            r.setIdUtente(utente.getIdUtente());
            r.setIdArticolo(idArticolo);
            r.setVoto(voto);
            r.setCommento(commento);
            r.setDataRecensione(LocalDate.now());

            // 5) Salvo nel DB
            dao.doSave(r);

            // 6) Messaggio di successo
            session.setAttribute("successo", "Recensione aggiunta con successo!");

            // 7) Redirect alla pagina prodotto
            response.sendRedirect(request.getContextPath() + "/prodotto?id=" + idArticolo);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }
}
