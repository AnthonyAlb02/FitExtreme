package controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/removeFromCart")
public class rimuoviCarrelloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public rimuoviCarrelloServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessione = request.getSession(false);

        if (sessione == null) {
            response.sendRedirect("viewCart");
            return;
        }

        // Recupero ID articolo
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect("viewCart");
            return;
        }

        try {
            int idArticolo = Integer.parseInt(idParam);

            // Recupero carrello
            Map<Integer, Integer> carrello =
                    (Map<Integer, Integer>) sessione.getAttribute("carrello");

            if (carrello != null && carrello.containsKey(idArticolo)) {
                carrello.remove(idArticolo);
            }

            // Redirect al carrello aggiornato
            response.sendRedirect("viewCart");

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("viewCart");
        }
    }
}
