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

@WebServlet("/prodottiAjax") // Mappa la servlet all’endpoint chiamato via AJAX
public class prodottiAJAX extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public prodottiAJAX() {
        super(); // Costruttore standard
    }

    // Reindirizza le richieste GET verso POST (comportamento identico)
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    // Gestione della richiesta AJAX
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Risposta JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Lettura parametri dal client
            String idParam = request.getParameter("id");   // categoria
            String minP    = request.getParameter("min");  // prezzo minimo
            String maxP    = request.getParameter("max");  // prezzo massimo
            String order   = request.getParameter("order"); // ordinamento

            // Conversione parametri in tipi adeguati
            Integer categoria = (idParam != null && !idParam.isEmpty()) 
                                ? Integer.parseInt(idParam) : null;

            BigDecimal min = (minP != null && !minP.isEmpty()) 
                             ? new BigDecimal(minP) : null;

            BigDecimal max = (maxP != null && !maxP.isEmpty()) 
                             ? new BigDecimal(maxP) : null;

            // DAO per accedere ai prodotti
            ArticoloDAO model = new ArticoloDAO();
            Collection<Articolo> prodotti;

            // Se è presente un ordine, usa il metodo con ordinamento
            if (order != null && !order.isEmpty()) {
                prodotti = model.doFilterOrder(categoria, min, max, order);
            } else {
                // Altrimenti usa il filtro base
                prodotti = model.doFilter(categoria, min, max);
            }

            // Costruzione manuale del JSON
            StringBuilder json = new StringBuilder("[");
            boolean first = true;

            for (Articolo a : prodotti) {
                if (!first) json.append(",");
                first = false;

                json.append("{")
                    .append("\"idArticolo\":").append(a.getIdArticolo()).append(",")
                    .append("\"nomeArticolo\":\"")
                        // Escape delle virgolette per evitare errori JSON
                        .append(a.getNomeArticolo().replace("\"", "\\\"")).append("\",")
                    .append("\"prezzoListino\":").append(a.getPrezzoListino()).append(",")
                    .append("\"immagine\":\"")
                        // Se immagine è null, restituisce stringa vuota
                        .append(a.getImmagine() != null ? a.getImmagine() : "").append("\",")
                    .append("\"qtaDisponibile\":").append(a.getQtaDisponibile()) // quantità disponibile
                    .append("}");
            }

            json.append("]");

            // Invio risposta al client
            out.print(json.toString());

        } catch (SQLException | NumberFormatException e) {
            // Errori DB o conversione numerica → ritorna array vuoto
            e.printStackTrace();
            out.print("[]");
        }
    }
}
