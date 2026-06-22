package controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.beans.Utente;
import model.DAO.UtenteDAO;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("tentativo", false);
        boolean cookieAccepted = false;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("cookieConsent".equals(c.getName())) {
                    cookieAccepted = true;
                    break;
                }
            }
        }
        request.setAttribute("cookieAccepted", cookieAccepted);
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UtenteDAO model = new UtenteDAO();
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String remember = request.getParameter("remember");
            if (email != null) {
                email = email.trim().toLowerCase();
            }
            if (email == null || !EMAIL_REGEX.matcher(email).matches()) {
                request.setAttribute("errore", "Inserisci un'email valida.");
                request.setAttribute("tentativo", true);
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }
            Utente utente = model.doRetrieveByEmail(email);
            if (utente == null) {
                request.setAttribute("errore", "Email non registrata.");
                request.setAttribute("tentativo", true);
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }
            if (!checkCredentials(email, password, utente)) {
                request.setAttribute("errore", "Credenziali errate. Riprova.");
                request.setAttribute("tentativo", true);
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }
            HttpSession oldSession = request.getSession(false);
            Object carrello = null;
            Object cartCount = null;
            String redirect = null;
            Object infoMessage = null;
            if (oldSession != null) {
                carrello = oldSession.getAttribute("carrello");
                cartCount = oldSession.getAttribute("cartCount");
                redirect = (String) oldSession.getAttribute("redirectAfterLogin");
                infoMessage = oldSession.getAttribute("infoMessage");
            }
            if (oldSession != null) {
                oldSession.invalidate();
            }
            HttpSession newSession = request.getSession(true);
            boolean adminFlag = "admin".equalsIgnoreCase(utente.getRuolo());
            newSession.setAttribute("utente", utente);
            newSession.setAttribute("id", utente.getIdUtente());
            newSession.setAttribute("isAdmin", adminFlag);
            if (carrello != null) newSession.setAttribute("carrello", carrello);
            if (cartCount != null) newSession.setAttribute("cartCount", cartCount);
            if (infoMessage != null) newSession.setAttribute("infoMessage", infoMessage);
            if ("on".equals(remember)) {
                Cookie rememberCookie = new Cookie("rememberEmail", utente.getEmail());
                rememberCookie.setMaxAge(60 * 60 * 24 * 30);
                rememberCookie.setHttpOnly(true);
                rememberCookie.setPath("/");
                response.addCookie(rememberCookie);
            }
            Cookie consent = new Cookie("cookieConsent", "true");
            consent.setMaxAge(60 * 60 * 24 * 365);
            consent.setPath("/");
            response.addCookie(consent);
            if (redirect != null) {
                if (!redirect.startsWith(request.getContextPath())) {
                    if (redirect.startsWith("/")) {
                        redirect = request.getContextPath() + redirect;
                    } else {
                        redirect = request.getContextPath() + "/" + redirect;
                    }
                }
                newSession.removeAttribute("redirectAfterLogin");
                response.sendRedirect(redirect);
                return;
            }
            if (adminFlag) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                response.sendRedirect(request.getContextPath() + "/home");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error/error");
        }
    }

    private boolean checkCredentials(String mail, String pwd, Utente check) {
        return mail.equalsIgnoreCase(check.getEmail()) &&
               hashPassword(pwd).equals(check.getPasswordHash());
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
