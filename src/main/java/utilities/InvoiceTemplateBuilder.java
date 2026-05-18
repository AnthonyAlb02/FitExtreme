package utilities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.List;

import model.beans.DettaglioOrdine;
import model.beans.Ordine;
import model.beans.Utente;

public class InvoiceTemplateBuilder {

    public static String buildInvoiceHTML(Ordine ordine, List<DettaglioOrdine> dettagli, Utente utente) {

        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>");
        html.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");

        html.append("<head>");
        html.append("<meta charset=\"UTF-8\" />");

        html.append("<style>");

        // SFONDO CON LOGO TRASPARENTE
        html.append("body { ");
        html.append("font-family: Arial, sans-serif; padding: 30px; color: #333; ");
        html.append("background-image: url('utilities/logo.png'); ");
        html.append("background-repeat: no-repeat; ");
        html.append("background-position: center center; ");
        html.append("background-size: 40%; ");
        html.append("opacity: 0.98; ");
        html.append("} ");

        html.append("h1 { color: #FFC266; text-align: center; margin-bottom: 10px; }");
        html.append("h3 { margin-top: 30px; color: #444; }");
        html.append("table { width: 100%; border-collapse: collapse; margin-top: 15px; background: rgba(255,255,255,0.85); }");
        html.append("table, th, td { border: 1px solid #ccc; }");
        html.append("th, td { padding: 10px; text-align: left; }");
        html.append("th { background: #f7f7f7; }");
        html.append(".totale { text-align: right; font-size: 18px; margin-top: 20px; font-weight: bold; background: rgba(255,255,255,0.85); padding: 10px; }");

        html.append("</style>");

        html.append("</head>");
        html.append("<body>");

        // TITOLO
        html.append("<h1>Fattura n. ").append(ordine.getIdOrdine()).append("</h1>");

        // DATI CLIENTE
        html.append("<h3>Dati cliente</h3>");
        html.append("<p>")
            .append(utente.getNome()).append(" ").append(utente.getCognome()).append("<br />")
            .append("Email: ").append(utente.getEmail()).append("<br />")
            .append("Data ordine: ").append(ordine.getDataOrdine().format(df))
            .append("</p>");

        // DETTAGLI ORDINE
        html.append("<h3>Dettagli ordine</h3>");

        html.append("<table>");
        html.append("<tr>");
        html.append("<th>Articolo</th>");
        html.append("<th>Quantità</th>");
        html.append("<th>Prezzo</th>");
        html.append("<th>Subtotale</th>");
        html.append("</tr>");

        BigDecimal totale = BigDecimal.ZERO;

        for (DettaglioOrdine d : dettagli) {
            BigDecimal subtotale = d.getSubtotale();
            totale = totale.add(subtotale);

            html.append("<tr>");
            html.append("<td>").append(d.getNomeArticolo()).append("</td>");
            html.append("<td>").append(d.getQuantita()).append("</td>");
            html.append("<td>").append(d.getPrezzoAcquisto()).append(" €</td>");
            html.append("<td>").append(subtotale).append(" €</td>");
            html.append("</tr>");
        }

        html.append("</table>");

        // ⭐ CALCOLO IVA SCORPORATA
        BigDecimal iva = totale
                .multiply(new BigDecimal("22"))
                .divide(new BigDecimal("122"), 2, RoundingMode.HALF_UP);

        BigDecimal imponibile = totale.subtract(iva);

        // RIEPILOGO FINALE
        html.append("<p class=\"totale\">Imponibile: ").append(imponibile).append(" €</p>");
        html.append("<p class=\"totale\">IVA (22%): ").append(iva).append(" €</p>");
        html.append("<p class=\"totale\">Totale (IVA inclusa): ").append(totale).append(" €</p>");

        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }
}
