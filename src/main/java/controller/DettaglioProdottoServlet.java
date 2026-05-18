package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DAO.ArticoloDAO;
import model.beans.Articolo;

@WebServlet("/dettaglioProdotto")
public class DettaglioProdottoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    RequestDispatcher dispatcher;

    public DettaglioProdottoServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        dispatcher = getServletContext().getRequestDispatcher("/jsp/prodotti/dettaglio.jsp");

        try {
            // 1) Leggo l'ID del prodotto
            String idParam = request.getParameter("id");

            if (idParam == null || idParam.isEmpty()) {
                dispatcher.forward(request, response);
                return;
            }

            int id = Integer.parseInt(idParam);

            // 2) Recupero il prodotto dal DB
            ArticoloDAO model = new ArticoloDAO();
            Articolo prodotto = model.doRetrieveByKey(id);

            // 3) Se non esiste → torno al catalogo
            if (prodotto == null) {
                dispatcher = getServletContext().getRequestDispatcher("/catalogo");
                dispatcher.forward(request, response);
                return;
            }

            // ⭐ 4) Calcolo IVA scorporata
            BigDecimal prezzo = prodotto.getPrezzoListino();

            BigDecimal iva = prezzo
                    .multiply(new BigDecimal("22"))
                    .divide(new BigDecimal("122"), 2, RoundingMode.HALF_UP);

            BigDecimal imponibile = prezzo.subtract(iva);

            // ⭐ 5) Metto tutto nella request
            request.setAttribute("prodotto", prodotto);
            request.setAttribute("iva", iva);
            request.setAttribute("imponibile", imponibile);

            // 6) Forward alla JSP
            dispatcher.forward(request, response);

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
