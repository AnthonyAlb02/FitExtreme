package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DAO.ArticoloDAO;
import model.beans.Articolo;

@WebServlet("/carrello")
public class carrelloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    RequestDispatcher dispatcher;

    public carrelloServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        dispatcher = getServletContext().getRequestDispatcher("/viewCart.jsp");

        HttpSession sessione = request.getSession(false);

        if (sessione == null) {
            dispatcher.forward(request, response);
            return;
        }

        Map<Integer, Integer> carrello =
                (Map<Integer, Integer>) sessione.getAttribute("carrello");

        if (carrello == null || carrello.isEmpty()) {
            request.setAttribute("prodotti", new ArrayList<Articolo>());
            request.setAttribute("totale", BigDecimal.ZERO);
            dispatcher.forward(request, response);
            return;
        }

        try {
            ArticoloDAO model = new ArticoloDAO();
            List<Articolo> prodotti = new ArrayList<>();
            BigDecimal totale = BigDecimal.ZERO;

            // Recupero dettagli articoli + calcolo totale
            for (Map.Entry<Integer, Integer> entry : carrello.entrySet()) {
                int idArticolo = entry.getKey();
                int qta = entry.getValue();

                Articolo a = model.doRetrieveByKey(idArticolo);
                if (a != null) {
                    prodotti.add(a);

                    BigDecimal subtotale = a.getPrezzoListino().multiply(new BigDecimal(qta));
                    totale = totale.add(subtotale);
                }
            }

            request.setAttribute("prodotti", prodotti);
            request.setAttribute("quantita", carrello);
            request.setAttribute("totale", totale);

            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            dispatcher.forward(request, response);
        }
    }
}
