package controller.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DAO.OrdineDAO;
import model.DAO.UtenteDAO;
import model.beans.Ordine;
import model.beans.Utente;

@WebServlet("/admin/ordini")
public class ordiniAdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            OrdineDAO ordineDAO = new OrdineDAO();
            UtenteDAO utenteDAO = new UtenteDAO();

            Collection<Ordine> ordini = ordineDAO.doRetrieveAll("id_asc");

            // Mappa idUtente → nome completo
            Map<Integer, String> nomiUtenti = new HashMap<>();
            Collection<Utente> utenti = utenteDAO.doRetrieveAll(null);
            for (Utente u : utenti) {
                nomiUtenti.put(u.getIdUtente(), u.getNome() + " " + u.getCognome());
            }

            request.setAttribute("ordini", ordini);
            request.setAttribute("nomiUtenti", nomiUtenti);

            RequestDispatcher dispatcher =
                getServletContext().getRequestDispatcher("/admin/ordini.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error/error.jsp");
        }
    }
}