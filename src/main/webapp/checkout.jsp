<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*, java.math.BigDecimal"%>
<%@ page import="model.beans.Articolo"%>

<%
    List<Articolo> prodotti = (List<Articolo>) request.getAttribute("prodotti");
    Map<Integer, Integer> quantita = (Map<Integer, Integer>) request.getAttribute("quantita");
    BigDecimal totale = (BigDecimal) request.getAttribute("totale");
    BigDecimal iva = (BigDecimal) request.getAttribute("iva");
    BigDecimal totaleConIva = (BigDecimal) request.getAttribute("totaleConIva");
%>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<title>Checkout | FitExtreme</title>

<link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/base.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/header.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/footer.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/checkout.css">
</head>

<body>

<jsp:include page="/header.jsp" />

<section class="checkout-container fade-in">

    <h1>Checkout</h1>

    <% if (prodotti == null || prodotti.isEmpty()) { %>

        <p class="empty-cart">Il tuo carrello è vuoto.</p>
        <a href="<%= request.getContextPath() %>/catalogo" class="btn-primary">Torna al catalogo</a>

    <% } else { %>

    <div class="checkout-grid">

        <!-- RIEPILOGO PRODOTTI -->
        <div class="checkout-products">

            <% for (Articolo a : prodotti) { %>

            <div class="checkout-item">

                <img src="<%= request.getContextPath() %>/utilities/immagini/<%= a.getImmagine() %>"
                     class="checkout-img">

                <div class="checkout-info">
                    <h3><%= a.getNomeArticolo() %></h3>
                    <p>Quantità: <%= quantita.get(a.getIdArticolo()) %></p>
                    <p class="price">€ <%= a.getPrezzoListino() %></p>
                </div>

            </div>

            <% } %>

        </div>

        <!-- RIEPILOGO ORDINE -->
        <div class="checkout-summary">

            <h2>Riepilogo ordine</h2>

            <div class="summary-row">
                <span>Totale prodotti</span>
                <span>€ <%= totale %></span>
            </div>

            <div class="summary-row">
                <span>IVA (22%)</span>
                <span>€ <%= iva %></span>
            </div>

            <div class="summary-row">
                <span>Spedizione</span>
                <span>Gratis</span>
            </div>

            <div class="summary-total">
                <span>Totale finale</span>
                <span>€ <%= totaleConIva %></span>
            </div>

            <a href="<%= request.getContextPath() %>/confermaOrdine" class="btn-primary">
                Procedi al pagamento
            </a>

        </div>

    </div>

    <% } %>

</section>

<jsp:include page="/footer.jsp" />

</body>
</html>
