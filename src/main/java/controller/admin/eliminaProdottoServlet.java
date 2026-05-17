package controller.admin;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DAO.ArticoloDAO;

@WebServlet("/admin/CancellaProdotto")
public class eliminaProdottoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public eliminaProdottoServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recupero la sessione dell'utente
        HttpSession sessione = request.getSession(false);

        // Se non c'è sessione → non è loggato
        if (sessione == null) {
            response.sendRedirect("../login");
            return;
        }

        // Controllo che l'utente sia admin
        String ruolo = (String) sessione.getAttribute("ruolo");
        if (ruolo == null || !ruolo.equals("admin")) {
            response.sendRedirect("../errorePermessi.jsp");
            return;
        }

        // Recupero l'ID del prodotto da eliminare
        String idParam = request.getParameter("id");

        // Se non c'è ID → torno alla lista
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect("prodottiAdmin");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);

            // DAO per eliminare il prodotto
            ArticoloDAO dao = new ArticoloDAO();

            // Elimino il prodotto dal DB
            dao.doDelete(id);

            // Dopo l'eliminazione torno alla lista prodotti admin
            response.sendRedirect("prodottiAdmin");

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();

            // In caso di errore torno comunque alla lista
            response.sendRedirect("prodottiAdmin");
        }
    }
}
