package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.beans.Articolo;
import model.DAO.ArticoloDAO;
import model.DAO.RecensioneDAO;  
import java.util.List;            
import model.beans.Recensione;    

@WebServlet("/prodotto")
public class prodottoServlet extends HttpServlet {

    private ArticoloDAO articoloDAO = new ArticoloDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID prodotto mancante");
            return;
        }

        int idProdotto;
        try {
            idProdotto = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID prodotto non valido");
            return;
        }

        try {
            Articolo prodotto = articoloDAO.doRetrieveByKey(idProdotto);

            if (prodotto == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Prodotto non trovato");
                return;
            }

            //  AGGIUNTA RECENSIONI (UNICA COSA CHE MANCAVA)
            RecensioneDAO rdao = new RecensioneDAO();
            List<Recensione> recensioni = rdao.doRetrieveByArticolo(idProdotto);
            request.setAttribute("recensioni", recensioni);

            
            // Passiamo alla JSP la lista completa dei prodotti
            request.setAttribute("tuttiProdotti", articoloDAO.doRetrieveAll(null));

            // Prodotto corrente
            request.setAttribute("prodotto", prodotto);

            RequestDispatcher dispatcher =
                    request.getRequestDispatcher("/prodotto.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
