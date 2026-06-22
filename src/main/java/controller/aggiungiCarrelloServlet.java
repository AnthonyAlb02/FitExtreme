package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DAO.ArticoloDAO;
import model.beans.Articolo;

@WebServlet("/addToCart")
public class aggiungiCarrelloServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessione = request.getSession(true);

        String idParam = request.getParameter("id");
        if (idParam == null) return;

        try {
            int idArticolo = Integer.parseInt(idParam);

            ArticoloDAO model = new ArticoloDAO();
            Articolo articolo = model.doRetrieveByKey(idArticolo);

            if (articolo == null) return;

            int stock = articolo.getQtaDisponibile();

            Map<Integer, Integer> carrello =
                    (Map<Integer, Integer>) sessione.getAttribute("carrello");

            if (carrello == null) {
                carrello = new HashMap<>();
                sessione.setAttribute("carrello", carrello);
            }

            int qtaNelCarrello = carrello.getOrDefault(idArticolo, 0);

            // ❗ BLOCCO SE SUPERA LO STOCK
            if (qtaNelCarrello >= stock) {
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"stock_esaurito\"}");
                return;
            }

            // Aggiunta consentita
            carrello.put(idArticolo, qtaNelCarrello + 1);

            int totale = carrello.values().stream().mapToInt(Integer::intValue).sum();
            sessione.setAttribute("cartCount", totale);

            response.setContentType("application/json");
            response.getWriter().write("{\"cartCount\": " + totale + "}");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
