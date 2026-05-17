package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DAO.UtenteDAO;
import model.beans.Utente;

@WebServlet("/checkEmail")
public class CheckEmailAJAX extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CheckEmailAJAX() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            String email = request.getParameter("email");

            if (email == null || email.trim().isEmpty()) {
                out.print("{\"exists\": false}");
                return;
            }

            email = email.trim().toLowerCase();

            UtenteDAO model = new UtenteDAO();
            Utente u = model.doRetrieveByEmail(email);

            if (u != null) {
                out.print("{\"exists\": true}");
            } else {
                out.print("{\"exists\": false}");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            out.print("{\"exists\": false}");
        }
    }
}
