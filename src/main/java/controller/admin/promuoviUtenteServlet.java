package controller.admin;

import model.DAO.UtenteDAO;
import model.beans.Utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/promuovi-utente")
public class promuoviUtenteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        UtenteDAO dao = new UtenteDAO();

        try {
            Utente utente = dao.doRetrieveByKey(id);

            if (utente != null) {
                utente.setRuolo("admin");
                dao.doUpdate(utente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/admin/utenti");
    }
}