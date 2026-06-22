package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.DAO.DettaglioOrdineDAO;
import model.DAO.OrdineDAO;
import model.beans.DettaglioOrdine;
import model.beans.Ordine;
import model.beans.Utente;


@WebServlet("/ordineCompletato")
public class ordineCompletatoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        if (id == null) {
            response.sendRedirect("home");
            return;
        }

        // Passa solo l'id alla JSP, nient'altro
        request.setAttribute("idOrdine", id);

        RequestDispatcher dispatcher =
                getServletContext().getRequestDispatcher("/ordineCompletato.jsp");
        dispatcher.forward(request, response);
    }
}