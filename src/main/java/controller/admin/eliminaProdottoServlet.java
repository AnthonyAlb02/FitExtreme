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

@WebServlet("/admin/elimina-prodotto")
public class eliminaProdottoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public eliminaProdottoServlet() {
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

        HttpSession sessione = request.getSession(false);

        // Utente non loggato
        if (sessione == null) {
            response.sendRedirect("../login");
            return;
        }

        // Controllo ruolo admin
        Boolean isAdmin = (Boolean) sessione.getAttribute("isAdmin");

        if (isAdmin == null || !isAdmin) {
            response.sendRedirect("../errorePermessi");
            return;
        }


        // Recupero ID prodotto
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect("prodotti");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);

            ArticoloDAO dao = new ArticoloDAO();

            // Eliminazione reale = ON DELETE SET NULL gli ordini restano intatti
            boolean eliminato = dao.doDelete(id);

            if (eliminato) {
                sessione.setAttribute("messaggioSuccesso", "Prodotto eliminato correttamente.");
            } else {
                sessione.setAttribute("messaggioErrore", "Impossibile eliminare il prodotto.");
            }

            response.sendRedirect("prodotti");

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            sessione.setAttribute("messaggioErrore", "Errore durante l'eliminazione del prodotto.");
            response.sendRedirect("prodotti");
        }
    }
}
