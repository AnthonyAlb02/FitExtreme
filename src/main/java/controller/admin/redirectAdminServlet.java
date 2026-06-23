package controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/admin/redirectAdmin")
public class redirectAdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public redirectAdminServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recupero la sessione dell'utente
        HttpSession sessione = request.getSession(false);

        // Se non c'è sessione → non è loggato
        if (sessione == null) {
            response.sendRedirect("../login");
            return;
        }

        // Controllo che l'utente sia admin
        String ruolo = (String) sessione.getAttribute("ruolo");

        // Se non è admin = lo mando alla pagina errore permessi
        if (ruolo == null || !ruolo.equals("admin")) {
            response.sendRedirect("../errorePermessi");
            return;
        }

        // Se è admin = lo mando alla dashboard
        response.sendRedirect("dashboard");
    }
}
