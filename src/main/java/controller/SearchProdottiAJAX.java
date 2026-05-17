package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DAO.ArticoloDAO;
import model.beans.Articolo;

@WebServlet("/searchProdotti")
public class SearchProdottiAJAX extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SearchProdottiAJAX() {
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
            String keyword = request.getParameter("keyword");

            if (keyword == null || keyword.trim().isEmpty()) {
                out.print("[]");
                return;
            }

            keyword = keyword.trim();

            ArticoloDAO model = new ArticoloDAO();
            Collection<Articolo> risultati = model.doSearch(keyword);

            // Costruisco JSON manualmente (semplice e compatibile)
            StringBuilder json = new StringBuilder("[");
            boolean first = true;

            for (Articolo a : risultati) {
                if (!first) json.append(",");
                first = false;

                json.append("{")
                    .append("\"id\":").append(a.getIdArticolo()).append(",")
                    .append("\"nome\":\"").append(a.getNomeArticolo().replace("\"", "\\\"")).append("\",")
                    .append("\"prezzo\":").append(a.getPrezzoListino()).append(",")
                    .append("\"qta\":").append(a.getQtaDisponibile())
                    .append("}");
            }

            json.append("]");

            out.print(json.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            out.print("[]");
        }
    }
}
