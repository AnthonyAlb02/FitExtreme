package controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.beans.Utente;
import model.DAO.UtenteDAO;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    RequestDispatcher dispatcher;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Mostra semplicemente la pagina di registrazione
        dispatcher = getServletContext().getRequestDispatcher("/register.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UtenteDAO model = new UtenteDAO();

        try {
            // 1) Leggo i parametri
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String nome = request.getParameter("nome");
            String cognome = request.getParameter("cognome");
            String telefono = request.getParameter("telefono");
            String indirizzo = request.getParameter("indirizzo");

            // Controllo parametri null
            if (email == null || password == null || nome == null || cognome == null) {
                request.setAttribute("errore", "Compila tutti i campi obbligatori.");
                request.getRequestDispatcher("/jsp/utente/registrazione.jsp").forward(request, response);
                return;
            }

            email = email.trim().toLowerCase();
            password = password.trim();

            // 2) Controllo se l’email è già registrata
            Utente esistente = model.doRetrieveByEmail(email);
            if (esistente != null) {
                request.setAttribute("errore", "Email già registrata");
                request.getRequestDispatcher("/jsp/utente/registrazione.jsp").forward(request, response);
                return;
            }

            // 3) Creo nuovo utente
            Utente nuovo = new Utente();
            nuovo.setEmail(email);
            nuovo.setPasswordHash(hashPassword(password));
            nuovo.setNome(nome);
            nuovo.setCognome(cognome);
            nuovo.setTelefono(telefono);
            nuovo.setIndirizzoSpedizione(indirizzo);
            nuovo.setDataRegistrazione(LocalDate.now());
            nuovo.setRuolo("registrato");

            // 4) Salvo nel DB
            model.doSave(nuovo);

            // 5) LOGIN AUTOMATICO
            HttpSession sessione = request.getSession();
            sessione.setAttribute("utente", nuovo);
            sessione.setAttribute("id", nuovo.getIdUtente());
            sessione.setAttribute("admin", false);

            // Messaggio di benvenuto
            sessione.setAttribute("successo", "Registrazione completata! Benvenuto su FitExtreme.");

            // 6) Redirect alla home (PRG)
            response.sendRedirect(request.getContextPath() + "/home");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPassword) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
