<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.*, java.math.BigDecimal, model.beans.Ordine, model.beans.DettaglioOrdine" %>

<%
    Ordine ordine = (Ordine) request.getAttribute("ordine");
    List<DettaglioOrdine> dettagli = (List<DettaglioOrdine>) request.getAttribute("dettagli");

    BigDecimal totale = ordine.getImportoTotale();
    BigDecimal iva = totale
        .multiply(new BigDecimal("22"))
        .divide(new BigDecimal("122"), 2, java.math.RoundingMode.HALF_UP);
    BigDecimal imponibile = totale.subtract(iva);
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Dettaglio Ordine #<%= ordine.getIdOrdine() %></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/utilities/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/utilities/css/admin.css">
</head>
<body>

<div class="admin-content">

    <a href="${pageContext.request.contextPath}/admin/ordini"
       class="btn btn-secondary"
       style="display:inline-block; margin-bottom: 24px;">
       ← Torna agli ordini
    </a>

    <h1 class="admin-title">Dettaglio Ordine #<%= ordine.getIdOrdine() %></h1>

    <!-- INFO ORDINE -->
    <div style="background: #fff; border-radius: 12px; padding: 24px;
                box-shadow: 0 4px 14px rgba(0,0,0,0.07); margin-bottom: 30px;">

        <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(180px, 1fr)); gap: 16px;">

            <div>
                <p style="font-size: 0.85rem; color: #888; margin: 0 0 4px;">ID Ordine</p>
                <p style="font-weight: 600; margin: 0;">#<%= ordine.getIdOrdine() %></p>
            </div>

            <div>
                <p style="font-size: 0.85rem; color: #888; margin: 0 0 4px;">ID Utente</p>
                <p style="font-weight: 600; margin: 0;"><%= ordine.getIdUtente() %></p>
            </div>

            <div>
                <p style="font-size: 0.85rem; color: #888; margin: 0 0 4px;">Data ordine</p>
                <p style="font-weight: 600; margin: 0;"><%= ordine.getDataOrdine().format(
        java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")
    )
%>
</p>
            </div>

            <div>
                <p style="font-size: 0.85rem; color: #888; margin: 0 0 4px;">Stato</p>
                <p style="font-weight: 600; margin: 0;"><%= ordine.getStatoAvanzamento() %></p>
            </div>

            <div>
                <p style="font-size: 0.85rem; color: #888; margin: 0 0 4px;">Imponibile</p>
                <p style="font-weight: 600; margin: 0;">€ <%= imponibile %></p>
            </div>

            <div>
                <p style="font-size: 0.85rem; color: #888; margin: 0 0 4px;">IVA (22%)</p>
                <p style="font-weight: 600; margin: 0;">€ <%= iva %></p>
            </div>

            <div>
                <p style="font-size: 0.85rem; color: #888; margin: 0 0 4px;">Totale (IVA inclusa)</p>
                <p style="font-weight: 600; color: #FFC266; font-size: 1.1rem; margin: 0;">€ <%= totale %></p>
            </div>

        </div>
    </div>

    <!-- ARTICOLI -->
    <h2 style="font-size: 1.3rem; font-weight: 600; margin-bottom: 16px;">Articoli acquistati</h2>

    <table class="admin-table">
        <thead>
            <tr>
                <th>Immagine</th>
                <th>Prodotto</th>
                <th>Quantità</th>
                <th>Prezzo unitario</th>
                <th>Subtotale</th>
            </tr>
        </thead>
        <tbody>
        <% for (DettaglioOrdine d : dettagli) { %>
        <tr>
            <td>
                <img src="${pageContext.request.contextPath}/utilities/immagini/<%= d.getImmagine() %>"
                     alt="<%= d.getNomeArticolo() %>"
                     style="width: 60px; height: 60px; object-fit: cover; border-radius: 6px;">
            </td>
            <td><%= d.getNomeArticolo() %></td>
            <td><%= d.getQuantita() %></td>
            <td>€ <%= d.getPrezzoAcquisto() %></td>
            <td>€ <%= d.getSubtotale() %></td>
        </tr>
        <% } %>
        </tbody>
    </table>

</div>

</body>
</html>