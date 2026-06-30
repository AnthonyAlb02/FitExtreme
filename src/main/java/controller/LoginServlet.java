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

@WebServlet("/login") // Mappa la servlet all’URL /login
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Regex per validare l’email lato server
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Flag per mostrare o meno il messaggio di errore
        request.setAttribute("tentativo", false);

        // Controllo se l’utente ha già accettato i cookie
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

        // Passo l’informazione alla JSP
        request.setAttribute("cookieAccepted", cookieAccepted);

        // Mostro la pagina di login
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UtenteDAO model = new UtenteDAO();

        try {
            // Recupero parametri dal form
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String remember = request.getParameter("remember");

            // Normalizzazione email
            if (email != null) {
                email = email.trim().toLowerCase();
            }

            // Validazione email lato server
            if (email == null || !EMAIL_REGEX.matcher(email).matches()) {
                request.setAttribute("errore", "Inserisci un'email valida.");
                request.setAttribute("tentativo", true);
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }

            // Recupero utente dal DB
            Utente utente = model.doRetrieveByEmail(email);

            // Email non trovata
            if (utente == null) {
                request.setAttribute("errore", "Email non registrata.");
                request.setAttribute("tentativo", true);
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }

            // Controllo credenziali
            if (!checkCredentials(email, password, utente)) {
                request.setAttribute("errore", "Credenziali errate. Riprova.");
                request.setAttribute("tentativo", true);
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }

            // Recupero eventuali dati dalla sessione precedente
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

            // Invalido la vecchia sessione per sicurezza
            if (oldSession != null) {
                oldSession.invalidate();
            }

            // Creo una nuova sessione
            HttpSession newSession = request.getSession(true);

            // Flag admin
            boolean adminFlag = "admin".equalsIgnoreCase(utente.getRuolo());

            // Salvo i dati utente in sessione
            newSession.setAttribute("utente", utente);
            newSession.setAttribute("id", utente.getIdUtente());
            newSession.setAttribute("isAdmin", adminFlag);

            // Ripristino eventuali dati precedenti
            if (carrello != null) newSession.setAttribute("carrello", carrello);
            if (cartCount != null) newSession.setAttribute("cartCount", cartCount);
            if (infoMessage != null) newSession.setAttribute("infoMessage", infoMessage);

            // Gestione cookie "remember me"
            if ("on".equals(remember)) {
                Cookie rememberCookie = new Cookie("rememberEmail", utente.getEmail());
                rememberCookie.setMaxAge(60 * 60 * 24 * 30); // 30 giorni
                rememberCookie.setHttpOnly(true);
                rememberCookie.setPath("/");
                response.addCookie(rememberCookie);
            }

            

            // Redirect dopo login (se l’utente era stato reindirizzato)
            if (redirect != null) {

                // Normalizzazione path
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

            // Redirect in base al ruolo
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

    // Controllo credenziali: email + hash password
    private boolean checkCredentials(String mail, String pwd, Utente check) {
        return mail.equalsIgnoreCase(check.getEmail()) &&
               hashPassword(pwd).equals(check.getPasswordHash());
    }

    // Hash SHA-256 della password
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
