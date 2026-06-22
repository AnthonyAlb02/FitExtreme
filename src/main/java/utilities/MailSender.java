package utilities;

import model.beans.DettaglioOrdine;
import model.beans.Ordine;
import model.beans.Utente;

import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class MailSender {

    private static final String HOST = "smtp.gmail.com";
    private static final int PORT = 587;
    private static final String USERNAME = "fitextreme.noreply@gmail.com";
    private static final String PASSWORD = "lnlqrvkiriejhqrg";

    /**
     * Renderizza una JSP in una stringa HTML
     */
    private static String renderJSP(HttpServletRequest request, HttpServletResponse response, String jspPath)
            throws Exception {

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        HttpServletResponseWrapper fakeResponse = new HttpServletResponseWrapper(response) {
            @Override
            public PrintWriter getWriter() {
                return writer;
            }
        };

        RequestDispatcher dispatcher = request.getRequestDispatcher(jspPath);
        dispatcher.include(request, fakeResponse);

        writer.flush();
        return stringWriter.toString();
    }

    /**
     * Invia la mail di conferma ordine usando la JSP come template
     */
    public static void inviaConfermaOrdine(
            HttpServletRequest request,
            HttpServletResponse response,
            Utente utente,
            Ordine ordine,
            List<DettaglioOrdine> dettagli)
            throws Exception {

        // La servlet DEVE aver già messo questi attributi:
        // ordine, dettagli, utente, totale, iva, imponibile

        // Renderizzo la JSP fattura_email.jsp (versione email-safe)
    	String htmlFattura = renderJSP(request, response, "/fattura_email.jsp");

        // Configurazione SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", String.valueOf(PORT));

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        // Corpo email responsive e pulito
        String corpoMail =
                "<!DOCTYPE html>" +
                "<html><head><meta charset='UTF-8'></head>" +
                "<body style='font-family:Arial; background:#f5f5f5; padding:20px;'>" +

                "<table width='100%' cellpadding='0' cellspacing='0'>" +
                "<tr><td align='center'>" +

                "<table width='600' cellpadding='0' cellspacing='0' style='background:#fff; border-radius:8px;'>" +

                "<tr><td style='background:#111; color:#fff; padding:20px; text-align:center;'>" +
                "<h1 style='margin:0;'>FitExtreme</h1>" +
                "</td></tr>" +

                "<tr><td style='padding:20px;'>" +
                "<h2 style='color:#222;'>Grazie per il tuo ordine!</h2>" +
                "<p>Ecco la tua fattura:</p>" +
                htmlFattura +
                "</td></tr>" +

                "<tr><td style='background:#f3f3f3; padding:15px; text-align:center; font-size:12px; color:#777;'>" +
                "© FitExtreme - Tutti i diritti riservati" +
                "</td></tr>" +

                "</table>" +
                "</td></tr></table>" +

                "</body></html>";

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(USERNAME, "FitExtreme"));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(utente.getEmail()));
        msg.setSubject("Conferma ordine #" + ordine.getIdOrdine());
        msg.setSentDate(new Date());
        msg.setContent(corpoMail, "text/html; charset=UTF-8");

        Transport.send(msg);
    }
}
