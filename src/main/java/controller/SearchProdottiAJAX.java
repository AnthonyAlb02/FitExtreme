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
// Servlet chiamata via AJAX per la ricerca prodotti in tempo reale
public class SearchProdottiAJAX extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SearchProdottiAJAX() {
        super(); // Costruttore standard
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Reindirizza GET su POST per avere un unico punto logico
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // La risposta sarà JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {
            // Recupero della keyword digitata dall’utente
            String keyword = request.getParameter("keyword");

            // Se la keyword è vuota → ritorno array vuoto
            if (keyword == null || keyword.trim().isEmpty()) {
                out.print("[]");
                out.flush();
                return;
            }

            keyword = keyword.trim(); // Normalizzazione input

            // DAO per accedere ai prodotti
            ArticoloDAO model = new ArticoloDAO();
            Collection<Articolo> risultati = model.doSearch(keyword);

            // Nessun risultato → ritorno array vuoto
            if (risultati == null || risultati.isEmpty()) {
                out.print("[]");
                out.flush();
                return;
            }

            // Costruzione manuale del JSON
            StringBuilder json = new StringBuilder();
            json.append("[");

            boolean first = true;

            for (Articolo a : risultati) {

                // Salto eventuali oggetti nulli
                if (a == null) continue;

                // Gestione virgole tra gli oggetti JSON
                if (!first) {
                    json.append(",");
                }
                first = false;

                // Escape delle virgolette nel nome
                String nome = a.getNomeArticolo() != null
                        ? a.getNomeArticolo().replace("\"", "\\\"")
                        : "";

                // Oggetto JSON del singolo prodotto
                json.append("{")
                    .append("\"id\":").append(a.getIdArticolo()).append(",")
                    .append("\"nome\":\"").append(nome).append("\",")
                    .append("\"prezzo\":").append(a.getPrezzoListino()).append(",")
                    .append("\"qta\":").append(a.getQtaDisponibile())
                    .append("}");
            }

            json.append("]");

            // Invio risposta al client
            out.print(json.toString());
            out.flush();

        } catch (SQLException e) {
            // Errori DB → ritorno array vuoto
            e.printStackTrace();
            out.print("[]");
            out.flush();

        } catch (Exception e) {
            // Qualsiasi altro errore → fallback sicuro
            e.printStackTrace();
            out.print("[]");
            out.flush();
        }
    }
}
