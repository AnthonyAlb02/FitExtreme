package utilities;

import model.beans.DettaglioOrdine;
import model.beans.Ordine;
import model.beans.Utente;

import javax.mail.*;
import javax.mail.internet.*;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class MailSender {

    private static final String HOST = "smtp.gmail.com";
    private static final int PORT = 587;
    private static final String USERNAME = "fitextreme.noreply@gmail.com";
    private static final String PASSWORD = "lnlqrvkiriejhqrg";

    public static void inviaConfermaOrdine(
            Utente utente,
            Ordine ordine,
            List<DettaglioOrdine> dettagli)
            throws MessagingException, UnsupportedEncodingException {

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

        String nomeUtente = (utente.getNome() != null && !utente.getNome().isBlank())
                ? utente.getNome()
                : utente.getEmail();

        String riepilogoOrdine =
                InvoiceTemplateBuilder.buildInvoiceHTML(
                        ordine,
                        dettagli,
                        utente);

        String corpoMail =
                "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "</head>" +
                "<body style='font-family: Arial, Helvetica, sans-serif; background-color:#f5f5f5; margin:0; padding:20px;'>" +

                "<table width='100%' cellpadding='0' cellspacing='0'>" +
                "<tr>" +
                "<td align='center'>" +

                "<table width='700' cellpadding='0' cellspacing='0' " +
                "style='background:#ffffff; border-radius:8px; overflow:hidden;'>" +

                "<tr>" +
                "<td style='background:#111111; color:white; padding:25px; text-align:center;'>" +
                "<h1 style='margin:0;'>FitExtreme</h1>" +
                "</td>" +
                "</tr>" +

                "<tr>" +
                "<td style='padding:30px;'>" +

                "<h2 style='color:#222;'>Ordine ricevuto con successo!</h2>" +

                "<p>Ciao <strong>" + nomeUtente + "</strong>,</p>" +

                "<p>Grazie per aver scelto FitExtreme.</p>" +

                "<p>Abbiamo ricevuto correttamente il tuo ordine <strong>#"
                + ordine.getIdOrdine()
                + "</strong> e il pagamento è stato registrato.</p>" +

                "<p>Il nostro team inizierà a preparare la spedizione nel più breve tempo possibile.</p>" +

                "<p>Riceverai ulteriori aggiornamenti non appena l'ordine verrà elaborato e spedito.</p>" +

                "<hr style='margin:30px 0; border:none; border-top:1px solid #dddddd;'/>" +

                riepilogoOrdine +

                "<hr style='margin:30px 0; border:none; border-top:1px solid #dddddd;'/>" +

                "<p style='font-size:14px; color:#666666;'>" +
                "Per qualsiasi informazione o assistenza puoi rispondere a questa email." +
                "</p>" +

                "<p style='font-size:14px; color:#666666;'>" +
                "Grazie per aver acquistato su FitExtreme." +
                "</p>" +

                "</td>" +
                "</tr>" +

                "<tr>" +
                "<td style='background:#f3f3f3; padding:20px; text-align:center; font-size:12px; color:#777777;'>" +
                "© FitExtreme - Tutti i diritti riservati" +
                "</td>" +
                "</tr>" +

                "</table>" +

                "</td>" +
                "</tr>" +
                "</table>" +

                "</body>" +
                "</html>";

        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(USERNAME, "FitExtreme"));

        msg.setRecipient(
                Message.RecipientType.TO,
                new InternetAddress(utente.getEmail()));

        msg.setSubject(
                "Conferma ordine #" + ordine.getIdOrdine() + " - FitExtreme");

        msg.setSentDate(new Date());

        msg.setContent(corpoMail, "text/html; charset=UTF-8");

        Transport.send(msg);
    }
}