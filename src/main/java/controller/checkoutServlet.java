package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DAO.ArticoloDAO;
import model.beans.Articolo;

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

        // Utente non loggato
        if (sessione == null || sessione.getAttribute("utente") == null) {
            sessione = request.getSession(true);
            sessione.setAttribute("redirectAfterLogin", request.getContextPath() + "/checkout");
            sessione.setAttribute("infoMessage", "Effettua il login per completare il pagamento");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Carrello vuoto
        Map<Integer, Integer> carrello =
                (Map<Integer, Integer>) sessione.getAttribute("carrello");

        if (carrello == null || carrello.isEmpty()) {
            request.setAttribute("prodotti", new ArrayList<Articolo>());
            request.setAttribute("totale", BigDecimal.ZERO);
            getServletContext()
                    .getRequestDispatcher("/viewCart.jsp")
                    .forward(request, response);
            return;
        }

        try {
            ArticoloDAO dao = new ArticoloDAO();
            List<Articolo> prodotti = new ArrayList<>();
            BigDecimal totaleIvaInclusa = BigDecimal.ZERO;

            for (Map.Entry<Integer, Integer> entry : carrello.entrySet()) {
                Articolo a = dao.doRetrieveByKey(entry.getKey());
                if (a != null) {
                    prodotti.add(a);
                    totaleIvaInclusa = totaleIvaInclusa.add(
                        a.getPrezzoListino().multiply(new BigDecimal(entry.getValue()))
                    );
                }
            }

            
            BigDecimal imponibile = totaleIvaInclusa
                    .divide(new BigDecimal("1.22"), 2, RoundingMode.HALF_UP);

            BigDecimal iva = totaleIvaInclusa
                    .subtract(imponibile)
                    .setScale(2, RoundingMode.HALF_UP);

         
         // Attributi request 
            request.setAttribute("prodotti", prodotti);
            request.setAttribute("quantita", carrello);
            request.setAttribute("totale", imponibile);          
            request.setAttribute("iva", iva);
            request.setAttribute("totaleConIva", totaleIvaInclusa.setScale(2, RoundingMode.HALF_UP));  // era "totaleIvaInclusa"

           
            sessione.setAttribute("totalePagamento", totaleIvaInclusa.setScale(2, RoundingMode.HALF_UP));
            sessione.setAttribute("prodottiPagamento", prodotti);
            sessione.setAttribute("quantitaPagamento", carrello);

            getServletContext()
                    .getRequestDispatcher("/checkout.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/errore.jsp");
        }
    }
}