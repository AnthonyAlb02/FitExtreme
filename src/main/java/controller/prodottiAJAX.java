package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DAO.ArticoloDAO;
import model.beans.Articolo;

@WebServlet("/prodottiAjax")
public class prodottiAJAX extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public prodottiAJAX() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            // Parametri (NOME CORRETTO!)
            String idParam = request.getParameter("id");   // <--- FIX
            String minP = request.getParameter("min");
            String maxP = request.getParameter("max");
            String order = request.getParameter("order");

            Integer categoria = (idParam != null && !idParam.isEmpty()) ? Integer.parseInt(idParam) : null;
            BigDecimal min = (minP != null && !minP.isEmpty()) ? new BigDecimal(minP) : null;
            BigDecimal max = (maxP != null && !maxP.isEmpty()) ? new BigDecimal(maxP) : null;

            ArticoloDAO model = new ArticoloDAO();

            Collection<Articolo> prodotti;

            // Se c'è ordinamento → usa doFilterOrder
            if (order != null && !order.isEmpty()) {
                prodotti = model.doFilterOrder(categoria, min, max, order);
            } 
            // Altrimenti → usa doFilter
            else {
                prodotti = model.doFilter(categoria, min, max);
            }

            // JSON manuale CORRETTO per il tuo JS
            StringBuilder json = new StringBuilder("[");
            boolean first = true;

            for (Articolo a : prodotti) {
                if (!first) json.append(",");
                first = false;

                json.append("{")
                    .append("\"idArticolo\":").append(a.getIdArticolo()).append(",")
                    .append("\"nomeArticolo\":\"").append(a.getNomeArticolo().replace("\"", "\\\"")).append("\",")
                    .append("\"prezzoListino\":").append(a.getPrezzoListino()).append(",")
                    .append("\"immagine\":\"").append(a.getImmagine()).append("\"")
                    .append("}");
            }

            json.append("]");

            out.print(json.toString());

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            out.print("[]");
        }
    }
}
