package controller.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DAO.OrdineDAO;
import model.beans.Ordine;

@WebServlet("/admin/ordiniAdmin")
public class ordiniAdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    RequestDispatcher dispatcher;

    public ordiniAdminServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Indico la JSP che mostra la lista degli ordini per l'admin
        dispatcher = getServletContext().getRequestDispatcher("/jsp/admin/ordini/ordiniAdmin.jsp");

        // Recupero la sessione
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

        try {
            // DAO per recuperare gli ordini
            OrdineDAO ordineDAO = new OrdineDAO();

            // Recupero tutti gli ordini dal DB
            Collection<Ordine> ordini = ordineDAO.doRetrieveAll("Data_Ordine DESC");

            // Passo la lista alla JSP
            request.setAttribute("ordini", ordini);

            // Mostro la pagina
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            dispatcher.forward(request, response);
        }
    }
}
