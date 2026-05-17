<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="model.beans.Articolo, java.util.*"%>

<%
    Articolo p = (Articolo) request.getAttribute("prodotto");
    List<Articolo> tutti = (List<Articolo>) request.getAttribute("tuttiProdotti");
%>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<title><%= p.getNomeArticolo() %> | FitExtreme</title>

<!-- CSS GLOBALI -->
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/base.css">
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/header.css">
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/footer.css">

<!-- CSS SPECIFICO -->
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/prodotto.css">
</head>

<body data-context="<%= request.getContextPath() %>"
	class="product-page">

	<!-- HEADER -->
	<jsp:include page="/header.jsp" />

	<!-- HERO -->
	<section class="product-hero">
		<h1><%= p.getNomeArticolo() %></h1>
	</section>

	<!-- LAYOUT PRODOTTO -->
	<section class="product-layout">

		<!-- IMMAGINE GRANDE -->
		<div class="product-gallery">
			<div class="zoom-container">
				<img class="zoom-img"
					src="<%= request.getContextPath() %>/utilities/immagini/<%= p.getImmagine() %>"
					alt="<%= p.getNomeArticolo() %>">
			</div>
		</div>

		<!-- INFO PRODOTTO -->
		<div class="product-details">

			<h2 class="product-title"><%= p.getNomeArticolo() %></h2>

			<p class="product-price">
				€
				<%= p.getPrezzoListino() %></p>

			<p class="product-description"><%= p.getDescrizione() %></p>

			<!-- LOGICA BOTTONE -->
			<% if (p.getQtaDisponibile() > 0) { %>

			<button class="btn-add-cart add-to-cart"
				data-id="<%= p.getIdArticolo() %>">Aggiungi al carrello</button>

			<% } else { %>

			<a href="<%= request.getContextPath() %>/catalogo" class="btn-back">
				Torna al catalogo </a>

			<% } %>

			<p class="shipping-info">🚚 Spedizione gratuita sopra i 50€</p>
			<p class="shipping-info">↩️ Reso facile entro 30 giorni</p>

		</div>

	</section>

	<!-- SEZIONE PRODOTTI CORRELATI -->
	<section class="related-products">
		<h3>Potrebbe interessarti</h3>

		<div class="related-row">

			<%
            int categoria = p.getIdCategoria();
            int count = 0;

            if (tutti != null) {
                Collections.shuffle(tutti);

                for (Articolo c : tutti) {

                    if (c.getIdCategoria() == categoria && c.getIdArticolo() != p.getIdArticolo()) {

                        if (count == 6) break;
                        count++;
        %>

			<a
				href="<%= request.getContextPath() %>/prodotto?id=<%= c.getIdArticolo() %>"
				class="related-card"> <img
				src="<%= request.getContextPath() %>/utilities/immagini/<%= c.getImmagine() %>"
				alt="<%= c.getNomeArticolo() %>">
				<h4><%= c.getNomeArticolo() %></h4>
				<p class="price">
					€
					<%= c.getPrezzoListino() %></p>
			</a>

			<%
                    }
                }
            }

            if (count == 0) {
        %>

			<p class="placeholder">Nessun prodotto correlato disponibile.</p>

			<% } %>

		</div>
	</section>

	<!-- FOOTER -->
	<jsp:include page="/footer.jsp" />

	<!-- JS -->
	<script src="<%= request.getContextPath() %>/utilities/js/cart.js"></script>
	<script src="<%= request.getContextPath() %>/utilities/js/prodotto.js"></script>

</body>
</html>
