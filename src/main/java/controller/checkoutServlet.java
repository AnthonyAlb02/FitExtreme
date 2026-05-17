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
import model.beans.Utente;

@WebServlet("/checkout")
public class checkoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public checkoutServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessione = request.getSession(false);

        // 🔒 1) Utente non loggato → redirect al login
        if (sessione == null || sessione.getAttribute("utente") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // 🔒 2) Carrello mancante → redirect al carrello
        Map<Integer, Integer> carrello =
                (Map<Integer, Integer>) sessione.getAttribute("carrello");

        if (carrello == null || carrello.isEmpty()) {
            request.setAttribute("prodotti", new ArrayList<Articolo>());
            request.setAttribute("totale", BigDecimal.ZERO);

            RequestDispatcher dispatcher =
                    getServletContext().getRequestDispatcher("/viewCart.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            ArticoloDAO model = new ArticoloDAO();
            List<Articolo> prodotti = new ArrayList<>();
            BigDecimal totale = BigDecimal.ZERO;

            // 3) Recupero articoli e calcolo totale
            for (Map.Entry<Integer, Integer> entry : carrello.entrySet()) {
                int idArticolo = entry.getKey();
                int qta = entry.getValue();

                Articolo a = model.doRetrieveByKey(idArticolo);
                if (a != null) {
                    prodotti.add(a);

                    BigDecimal subtotale =
                            a.getPrezzoListino().multiply(new BigDecimal(qta));
                    totale = totale.add(subtotale);
                }
            }

            // 4) Passo i dati alla JSP
            request.setAttribute("prodotti", prodotti);
            request.setAttribute("quantita", carrello);
            request.setAttribute("totale", totale);

            RequestDispatcher dispatcher =
                    getServletContext().getRequestDispatcher("/checkout.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/errore.jsp");
        }
    }
}
