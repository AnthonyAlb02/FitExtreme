package controller.admin;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DAO.ArticoloDAO;
import model.DAO.OrdineDAO;
import model.DAO.UtenteDAO;

@WebServlet("/admin/dashboard")
public class adminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Il filtro AdminFilter ha già verificato sessione e ruolo,
        // qui non serve ricontrollare

        ArticoloDAO articoloDAO = new ArticoloDAO();
		OrdineDAO ordineDAO    = new OrdineDAO();
		UtenteDAO utenteDAO    = new UtenteDAO();

		// Uso i metodi count dedicati invece di caricare tutte le liste
		int numeroArticoli = articoloDAO.countProducts();
		int numeroOrdini   = ordineDAO.countOrders();
		int numeroUtenti   = utenteDAO.countUsers();

		request.setAttribute("numeroArticoli", numeroArticoli);
		request.setAttribute("numeroOrdini",   numeroOrdini);
		request.setAttribute("numeroUtenti",   numeroUtenti);

		RequestDispatcher dispatcher =
		        getServletContext().getRequestDispatcher("/admin/dashboard.jsp");
		dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}