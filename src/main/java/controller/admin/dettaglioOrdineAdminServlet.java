package controller.admin;

import model.DAO.DettaglioOrdineDAO;
import model.DAO.OrdineDAO;
import model.beans.DettaglioOrdine;
import model.beans.Ordine;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/dettaglioOrdine")
public class dettaglioOrdineAdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int idOrdine = Integer.parseInt(request.getParameter("id"));

            OrdineDAO ordineDAO = new OrdineDAO();
            DettaglioOrdineDAO dettaglioDAO = new DettaglioOrdineDAO();

            Ordine ordine = ordineDAO.doRetrieveByKey(idOrdine);

            // SE L’ORDINE NON ESISTE → TORNA ALLA LISTA
            if (ordine == null) {
                response.sendRedirect(request.getContextPath() + "/admin/ordini");
                return;
            }

            List<DettaglioOrdine> dettagli = dettaglioDAO.doRetrieveByOrdine(idOrdine);

            request.setAttribute("ordine", ordine);
            request.setAttribute("dettagli", dettagli);

            RequestDispatcher dispatcher =
                getServletContext().getRequestDispatcher("/admin/dettaglioOrdine.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/ordini");
        }
    }
}
