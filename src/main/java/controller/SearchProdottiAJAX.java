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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {
            String keyword = request.getParameter("keyword");

            if (keyword == null || keyword.trim().isEmpty()) {
                out.print("[]");
                out.flush();
                return;
            }

            keyword = keyword.trim();

            ArticoloDAO model = new ArticoloDAO();
            Collection<Articolo> risultati = model.doSearch(keyword);

            if (risultati == null || risultati.isEmpty()) {
                out.print("[]");
                out.flush();
                return;
            }

            StringBuilder json = new StringBuilder();
            json.append("[");

            boolean first = true;

            for (Articolo a : risultati) {

                if (a == null) continue;

                if (!first) {
                    json.append(",");
                }
                first = false;

                String nome = a.getNomeArticolo() != null
                        ? a.getNomeArticolo().replace("\"", "\\\"")
                        : "";

                json.append("{")
                    .append("\"id\":").append(a.getIdArticolo()).append(",")
                    .append("\"nome\":\"").append(nome).append("\",")
                    .append("\"prezzo\":").append(a.getPrezzoListino()).append(",")
                    .append("\"qta\":").append(a.getQtaDisponibile())
                    .append("}");
            }

            json.append("]");

            out.print(json.toString());
            out.flush();

        } catch (SQLException e) {
            e.printStackTrace();

            // fallback sicuro per AJAX
            out.print("[]");
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();

            out.print("[]");
            out.flush();
        }
    }
}