package controller;

import model.DAO.ArticoloDAO;
import model.beans.Articolo;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Collection;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArticoloDAO dao = new ArticoloDAO();
        System.out.println("DAO caricato correttamente");


        try {
            Collection<Articolo> prodotti = dao.doRetrieveAll("ID_Articolo LIMIT 4");
            request.setAttribute("prodotti", prodotti);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
        dispatcher.forward(request, response);
       

    }
    
}
