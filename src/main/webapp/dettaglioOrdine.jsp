<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="model.beans.Ordine" %>
<%@ page import="model.beans.DettaglioOrdine" %>
<%@ page import="model.DAO.ArticoloDAO" %>

<%
    Ordine ordine = (Ordine) request.getAttribute("ordine");
    List<DettaglioOrdine> dettagli = (List<DettaglioOrdine>) request.getAttribute("dettagli");
    BigDecimal totale = (BigDecimal) request.getAttribute("totale");

    BigDecimal iva = totale
            .multiply(new BigDecimal("22"))
            .divide(new BigDecimal("122"), 2, java.math.RoundingMode.HALF_UP);

    BigDecimal imponibile = totale.subtract(iva);

    ArticoloDAO daoCheck = new ArticoloDAO();
%>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<title>Dettaglio Ordine #<%= ordine.getIdOrdine() %> | FitExtreme</title>

<link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/base.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/header.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/footer.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/ordineDett.css">

<style>
    .removed-alert {
        display: inline-block;
        margin-top: 6px;
        padding: 4px 8px;
        background: #ffe0e0;
        color: #b30000;
        border-radius: 6px;
        font-size: 13px;
        font-weight: 600;
    }
</style>

</head>

<body>

<jsp:include page="/header.jsp" />

<div class="order-detail-wrapper">

    <h1>Dettaglio Ordine #<%= ordine.getIdOrdine() %></h1>

    <!-- INFO ORDINE -->
    <div class="order-info-box fade-in">
        <div class="order-info-row">
            <span>Data ordine:</span>
            <strong>
                <%= ordine.getDataOrdine().format(
                        java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")
                ) %>
            </strong>
        </div>

        <div class="order-info-row">
            <span>Stato:</span>
            <strong class="status"><%= ordine.getStatoAvanzamento() %></strong>
        </div>

        <div class="order-info-row">
            <span>Imponibile:</span>
            <strong>€ <%= imponibile %></strong>
        </div>

        <div class="order-info-row">
            <span>IVA (22%):</span>
            <strong>€ <%= iva %></strong>
        </div>

        <div class="order-info-row">
            <span>Totale registrato (IVA inclusa):</span>
            <strong>€ <%= totale %></strong>
        </div>
    </div>

    <!-- ARTICOLI -->
    <h2 class="section-title">Articoli acquistati</h2>

    <div class="items-list">

        <% for (DettaglioOrdine d : dettagli) { %>

        <%
            // ⭐ Controllo se l'articolo esiste ancora nel database
            boolean eliminato = (daoCheck.doRetrieveByKey(d.getIdArticolo()) == null);
        %>

        <div class="item-card fade-in">

            <!-- IMMAGINE ARTICOLO -->
            <img class="item-img"
                 src="<%= request.getContextPath() %>/utilities/immagini/<%= d.getImmagine() %>"
                 alt="<%= d.getNomeArticolo() %>">

            <!-- INFO ARTICOLO -->
            <div class="item-info">
                <span class="item-name"><%= d.getNomeArticolo() %></span>
                <span class="item-qty">Quantità: <%= d.getQuantita() %></span>

                <% if (eliminato) { %>
                    <span class="removed-alert">⚠ Articolo rimosso dal catalogo</span>
                <% } %>
            </div>

            <!-- SUBTOTALE -->
            <div class="item-price">
                € <%= d.getSubtotale() %>
            </div>

        </div>

        <% } %>

    </div>

    <!-- TOTALE -->
    <div class="total-box fade-in">
        Totale ordine (IVA inclusa): € <%= totale %>
    </div>

    <!-- TORNA INDIETRO -->
    <a href="<%= request.getContextPath() %>/ordini" class="btn-back">
        Torna ai miei ordini
    </a>

</div>

<jsp:include page="/footer.jsp" />

</body>
</html>
