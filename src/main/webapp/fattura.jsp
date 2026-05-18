<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="model.beans.Ordine"%>
<%@ page import="model.beans.DettaglioOrdine"%>
<%@ page import="model.beans.Utente"%>

<%
    Ordine ordine = (Ordine) request.getAttribute("ordine");
    List<DettaglioOrdine> dettagli = (List<DettaglioOrdine>) request.getAttribute("dettagli");
    Utente utente = (Utente) request.getAttribute("utente");

    BigDecimal iva = (BigDecimal) request.getAttribute("iva");
    BigDecimal imponibile = ordine.getImportoTotale().subtract(iva);
%>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<title>Fattura #<%= ordine.getIdOrdine() %></title>
<link rel="stylesheet"
    href="<%= request.getContextPath() %>/utilities/css/fattura.css">
</head>

<body>

    <div class="invoice">

        <header class="invoice-header">
            <div>
                <h1>FitExtreme</h1>
                <p>Via Roma 10, Salerno</p>
                <p>P.IVA: 12345678901</p>
            </div>

            <div class="invoice-info">
                <p><strong>Fattura n°:</strong> 2026-<%= ordine.getIdOrdine() %></p>
                <p><strong>Data:</strong> <%= ordine.getDataOrdine() %></p>
            </div>
        </header>

        <section class="customer">
            <h2>Dati Cliente</h2>
            <p><strong><%= utente.getNome() %> <%= utente.getCognome() %></strong></p>
            <p><%= utente.getEmail() %></p>
            <p><%= utente.getIndirizzoSpedizione() != null ? utente.getIndirizzoSpedizione() : "—" %></p>
        </section>

        <section class="items">
            <h2>Dettaglio Ordine</h2>

            <table>
                <tr>
                    <th>Articolo</th>
                    <th>Quantità</th>
                    <th>Prezzo</th>
                    <th>Subtotale</th>
                </tr>

                <% for (DettaglioOrdine d : dettagli) { %>
                <tr>
                    <td><%= d.getNomeArticolo() %></td>
                    <td><%= d.getQuantita() %></td>
                    <td>€ <%= d.getPrezzoAcquisto() %></td>
                    <td>€ <%= d.getSubtotale() %></td>
                </tr>
                <% } %>
            </table>
        </section>

        <!-- RIEPILOGO FINALE -->
        <section class="totals">

            <h2 class="total">Imponibile: € <%= imponibile %></h2>

            <h2 class="total">IVA (22%): € <%= iva %></h2>

            <h2 class="total">Totale (IVA inclusa): € <%= ordine.getImportoTotale() %></h2>

        </section>

        <footer class="invoice-footer">
            <p>Grazie per aver acquistato su FitExtreme!</p>
        </footer>

    </div>

</body>
</html>
