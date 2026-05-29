package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DAO.ArticoloDAO;
import model.beans.Articolo;

@WebServlet("/updateCart")
public class aggiornaCarrelloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public aggiornaCarrelloServlet() {
        super();
    }

    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessione = request.getSession(false);
        if (sessione == null) return;

        String idParam = request.getParameter("id");
        String action  = request.getParameter("action");
        if (idParam == null || action == null) return;

        try {
            int idArticolo = Integer.parseInt(idParam);

            Map<Integer, Integer> carrello =
                (Map<Integer, Integer>) sessione.getAttribute("carrello");
            if (carrello == null) return;

            Integer qtaObj = carrello.get(idArticolo);
            if (qtaObj == null) return;

            int qta = qtaObj;
            ArticoloDAO dao = new ArticoloDAO();

            if ("plus".equals(action)) {
                Articolo articolo = dao.doRetrieveByKey(idArticolo);
                int stock = articolo.getQtaDisponibile();

                if (qta >= stock) {
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"stock_esaurito\"}");
                    return;
                }

                qta++;
                carrello.put(idArticolo, qta);

            } else if ("minus".equals(action)) {
                if (qta > 1) {
                    qta--;
                    carrello.put(idArticolo, qta);
                }
            } else if ("remove".equals(action)) {
                carrello.remove(idArticolo);
            }

            // Calcolo subtotale
            BigDecimal subtotale = BigDecimal.ZERO;
            if (!"remove".equals(action)) {
                Articolo articolo = dao.doRetrieveByKey(idArticolo);
                subtotale = articolo.getPrezzoListino().multiply(new BigDecimal(qta));
            }

            // Calcolo totale carrello
            BigDecimal totale = BigDecimal.ZERO;
            for (Map.Entry<Integer, Integer> entry : carrello.entrySet()) {
                Articolo a = dao.doRetrieveByKey(entry.getKey());
                totale = totale.add(a.getPrezzoListino().multiply(new BigDecimal(entry.getValue())));
            }

            int cartCount = carrello.values().stream().mapToInt(Integer::intValue).sum();
            sessione.setAttribute("cartCount", cartCount);

            response.setContentType("application/json");
            response.getWriter().write(
                "{"
                + "\"qta\": " + (carrello.containsKey(idArticolo) ? qta : 0) + ","
                + "\"subtotale\": \"" + subtotale + "\","
                + "\"totale\": \"" + totale + "\","
                + "\"cartCount\": " + cartCount + ","
                + "\"removed\": " + ("remove".equals(action) ? "true" : "false")
                + "}"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}