<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="model.beans.Articolo, java.util.*"%>
<%@ page import="model.beans.Recensione" %>
<%@ page import="model.beans.Utente" %>
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
	<!-- SEZIONE RECENSIONI -->
<section class="reviews-section">
    <h3>Recensioni</h3>

    <%
        List<Recensione> recensioni = (List<Recensione>) request.getAttribute("recensioni");
        Utente utenteLoggato = (Utente) session.getAttribute("utente");
    %>

    <!-- LISTA RECENSIONI -->
    <div class="reviews-list">
        <% if (recensioni == null || recensioni.isEmpty()) { %>
            <p class="placeholder">Nessuna recensione disponibile.</p>
        <% } else { %>
            <% for (Recensione r : recensioni) { %>
                <div class="review-card">
                    <div class="review-rating">Voto: <%= r.getVoto() %>/5 ⭐</div>
                    <p class="review-comment"><%= r.getCommento() %></p>
                    <small class="review-date"><%= r.getDataRecensione() %></small>
                </div>
            <% } %>
        <% } %>
    </div>

    <!-- FORM RECENSIONE (solo utenti loggati) -->
    <% if (utenteLoggato != null) { %>
        <div class="review-form">
            <h4>Lascia una recensione</h4>

            <form action="<%= request.getContextPath() %>/addReview" method="post">
                <input type="hidden" name="idArticolo" value="<%= p.getIdArticolo() %>">

                <label for="voto">Voto:</label>
                <select name="voto" id="voto" required>
                    <option value="1">1 ⭐</option>
                    <option value="2">2 ⭐⭐</option>
                    <option value="3">3 ⭐⭐⭐</option>
                    <option value="4">4 ⭐⭐⭐⭐</option>
                    <option value="5">5 ⭐⭐⭐⭐⭐</option>
                </select>

                <label for="commento">Commento:</label>
                <textarea name="commento" id="commento" required></textarea>

                <button type="submit" class="btn-add-cart">Invia recensione</button>
            </form>
        </div>
    <% } else { %>
        <p class="login-warning">Effettua il login per lasciare una recensione.</p>
    <% } %>

</section>
	

	<!-- FOOTER -->
	<jsp:include page="/footer.jsp" />

	<!-- JS -->
	<script src="<%= request.getContextPath() %>/utilities/js/cart.js"></script>
	<script src="<%= request.getContextPath() %>/utilities/js/prodotto.js"></script>

</body>
</html>
