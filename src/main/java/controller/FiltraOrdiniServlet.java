package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.DAO.OrdineDAO;
import model.beans.Ordine;
import model.beans.Utente;

@WebServlet("/filtraOrdini")
public class FiltraOrdiniServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessione = request.getSession(false);
        if (sessione == null) {
            response.setContentType("application/json");
            response.getWriter().write("[]");
            return;
        }

        Utente utente = (Utente) sessione.getAttribute("utente");
        if (utente == null) {
            response.setContentType("application/json");
            response.getWriter().write("[]");
            return;
        }

        String dataStr = request.getParameter("data");

        LocalDate dataFiltro = null;
        if (dataStr != null && !dataStr.isEmpty()) {
            dataFiltro = LocalDate.parse(dataStr);
        }

        OrdineDAO dao = new OrdineDAO();
        List<Ordine> ordini;

        try {
            // Recupero TUTTI gli ordini dell’utente
            ordini = (List<Ordine>) dao.doRetrieveByUser(utente.getIdUtente());

            // Filtro in memoria
            if (dataFiltro != null) {
                LocalDate finalDataFiltro = dataFiltro; // necessario per la lambda
                ordini = ordini.stream()
                        .filter(o -> o.getDataOrdine().equals(finalDataFiltro))
                        .collect(Collectors.toList());
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write("[]");
            return;
        }

        // Risposta JSON
        response.setContentType("application/json;charset=UTF-8");

        StringBuilder json = new StringBuilder();
        json.append("[");

        for (int i = 0; i < ordini.size(); i++) {
            Ordine o = ordini.get(i);

            json.append("{")
                .append("\"id\":").append(o.getIdOrdine()).append(",")
                .append("\"data\":\"").append(o.getDataOrdine().toString()).append("\",")
                .append("\"totale\":\"").append(o.getImportoTotale().toString()).append("\"")
                .append("}");

            if (i < ordini.size() - 1) json.append(",");
        }

        json.append("]");

        response.getWriter().write(json.toString());
    }
}
