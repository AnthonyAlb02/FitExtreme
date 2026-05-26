	package controller.admin;
	
	import model.DAO.UtenteDAO;
	import model.beans.Utente;
	
	import javax.servlet.ServletException;
	import javax.servlet.annotation.WebServlet;
	import javax.servlet.http.*;
	import java.io.IOException;
	import java.sql.SQLException;
	import java.util.Collection;
	
	@WebServlet("/admin/utenti")
	public class utentiServlet extends HttpServlet {
	
	    @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	
	        UtenteDAO dao = new UtenteDAO();
	        Collection<Utente> utenti = null;
	
	        try {
	            utenti = dao.doRetrieveAll(null);
	        } catch (SQLException e) {
	            throw new ServletException("Errore nel recupero utenti", e);
	        }
	
	        request.setAttribute("utenti", utenti);
	
	        request.getRequestDispatcher("/admin/utenti.jsp").forward(request, response);
	    }
	}
