<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page
	import="java.util.*, model.beans.Articolo, model.beans.Categoria"%>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<title>Catalogo | FitExtreme</title>

<!-- CSS GLOBALI -->
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/base.css">
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/header.css">
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/footer.css">

<!-- CSS SPECIFICO -->
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/catalogo.css">
</head>

<body data-context="<%= request.getContextPath() %>">

	<!-- HEADER -->
	<jsp:include page="/header.jsp" />

	<!-- HERO -->
	<section class="catalogo-hero">
		<h1>Catalogo Prodotti</h1>
		<p>Scopri i prodotti della categoria selezionata</p>
	</section>

	<!-- FILTRI ORIZZONTALI -->
	<section class="fx-filters">

		<div class="fx-filter">
			<label>Categoria</label> <select id="categoriaFiltro">
				<option value="">Tutte</option>
				<%
                List<Categoria> categorie = (List<Categoria>) request.getAttribute("categorie");
                Integer catSel = (Integer) request.getAttribute("categoriaSelezionata");

                if (categorie != null) {
                    for (Categoria c : categorie) {
            %>
				<option value="<%= c.getIdCategoria() %>"
					<%= (catSel != null && catSel == c.getIdCategoria()) ? "selected" : "" %>>
					<%= c.getNome() %>
				</option>
				<%
                    }
                }
            %>
			</select>
		</div>

		<div class="fx-filter">
			<label>Ordina</label> <select id="order">
				<option value="">Predefinito</option>
				<option value="prezzo_asc">Prezzo ↑</option>
				<option value="prezzo_desc">Prezzo ↓</option>
				<option value="nome_asc">Nome A‑Z</option>
				<option value="nome_desc">Nome Z‑A</option>
			</select>
		</div>

		<div class="fx-filter">
			<label>Prezzo</label>
			<div class="fx-price">
				<input type="number" id="min" placeholder="Min"> <span>-</span>
				<input type="number" id="max" placeholder="Max">
			</div>
		</div>

		<button id="applyFilters" class="fx-btn">Filtra</button>

	</section>

	<!-- BADGE FILTRI ATTIVI -->
	<div id="active-filters" class="fx-active-filters"></div>

	<!-- GRID PRODOTTI -->
	<section class="prodotti-container" id="products-container">

		<%
        List<Articolo> prodotti = (List<Articolo>) request.getAttribute("prodotti");
        if (prodotti != null && !prodotti.isEmpty()) {
            for (Articolo p : prodotti) {
    %>

		<div class="card-wrapper">

			<div class="card">

				<% if (p.getQtaDisponibile() == 0) { %>
				<span class="soldout-badge">Esaurito</span>
				<% } %>

				<a
					href="<%= request.getContextPath() %>/prodotto?id=<%= p.getIdArticolo() %>"
					class="card-link"> <img
					src="<%= request.getContextPath() %>/utilities/immagini/<%= p.getImmagine() %>"
					alt="<%= p.getNomeArticolo() %>">

					<h3><%= p.getNomeArticolo() %></h3>
					<p class="prezzo">
						€
						<%= p.getPrezzoListino() %></p>
				</a>

				<% if (p.getQtaDisponibile() > 0) { %>
				<button class="btn-add add-to-cart card-btn"
					data-id="<%= p.getIdArticolo() %>">Aggiungi al carrello</button>
				<% } %>

			</div>

		</div>


		<%
            }
        } else {
    %>

		<p class="text-secondary">Nessun prodotto trovato.</p>

		<% } %>

	</section>

	<!-- FOOTER -->
	<jsp:include page="/footer.jsp" />

	<!-- JS -->
	<script src="<%= request.getContextPath() %>/utilities/js/cart.js"></script>
	<script src="<%= request.getContextPath() %>/utilities/js/popup.js"></script>
	<script src="<%= request.getContextPath() %>/utilities/js/filtri.js"></script>

</body>
</html>
