<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Collection"%>
<%@ page import="model.beans.Articolo"%>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<title>FitExtreme - Home</title>

<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/base.css">
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/header.css">
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/footer.css">
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/home.css">
</head>

<body>

	<jsp:include page="header.jsp" />

	<!-- HERO -->
	<section class="hero">
		<div class="hero-content">
			<h1>Allenati al massimo</h1>
			<p>Scopri i prodotti più richiesti del momento</p>
			<a href="<%= request.getContextPath() %>/catalogo" class="hero-btn">Vai
				al catalogo</a>
		</div>
	</section>

	<!-- CATEGORIE -->
	<section class="categories">
		<h2 class="section-title">Categorie principali</h2>

		<div class="carousel-wrapper">

			<button class="carousel-btn left" id="cat-left">&#10094;</button>

			<div class="carousel" id="cat-carousel">

				<a href="<%= request.getContextPath() %>/catalogo?id=1"
					class="cat-card">
					<div class="img-wrapper">
						<img
							src="<%= request.getContextPath() %>/utilities/immagini/abbigliamento.jpg"
							alt="Abbigliamento">
					</div> <span>Abbigliamento</span>
				</a> <a href="<%= request.getContextPath() %>/catalogo?id=2"
					class="cat-card">
					<div class="img-wrapper">
						<img
							src="<%= request.getContextPath() %>/utilities/immagini/accessori.jpg"
							alt="Accessori">
					</div> <span>Accessori</span>
				</a> <a href="<%= request.getContextPath() %>/catalogo?id=3"
					class="cat-card">
					<div class="img-wrapper">
						<img
							src="<%= request.getContextPath() %>/utilities/immagini/attrezzatura.jpg"
							alt="Attrezzatura">
					</div> <span>Attrezzatura</span>
				</a> <a href="<%= request.getContextPath() %>/catalogo?id=4"
					class="cat-card">
					<div class="img-wrapper">
						<img
							src="<%= request.getContextPath() %>/utilities/immagini/integratori.jpg"
							alt="Integratori">
					</div> <span>Integratori</span>
				</a> <a href="<%= request.getContextPath() %>/catalogo?id=5"
					class="cat-card">
					<div class="img-wrapper">
						<img
							src="<%= request.getContextPath() %>/utilities/immagini/pesi.jpg"
							alt="Pesi">
					</div> <span>Pesi</span>
				</a>

			</div>

			<button class="carousel-btn right" id="cat-right">&#10095;</button>

		</div>
	</section>

	<!-- PRODOTTI DEL MOMENTO -->
	<section class="section">
		<div class="container">
			<h2 class="section-title">Prodotti del momento</h2>

			<div class="grid">

				<%
                Collection<Articolo> prodotti = (Collection<Articolo>) request.getAttribute("prodotti");

                if (prodotti != null && !prodotti.isEmpty()) {
                    for (Articolo a : prodotti) {

                        String img = (a.getImmagine() != null && !a.getImmagine().isEmpty())
                                     ? a.getImmagine()
                                     : "default.jpg";
            %>

				<a class="card-link"
					href="<%= request.getContextPath() %>/prodotto?id=<%= a.getIdArticolo() %>">
					<div class="card">

						<div class="card-img">
							<img
								src="<%= request.getContextPath() %>/utilities/immagini/<%= img %>"
								alt="<%= a.getNomeArticolo() %>">
						</div>

						<div class="card-body">
							<div class="title"><%= a.getNomeArticolo() %></div>
							<div class="price">
								€
								<%= a.getPrezzoListino() %></div>
						</div>

					</div>
				</a>

				<%
                    }
                } else {
            %>

				<p>Nessun prodotto disponibile.</p>

				<%
                }
            %>

			</div>
		</div>
	</section>

	<!-- TRUST ICONS -->
	<section class="trust">
		<div class="trust-grid">

			<div class="trust-item">
				<img
					src="<%= request.getContextPath() %>/utilities/immagini/free-delivery.png"
					alt="Spedizione">
				<p>Spedizione veloce</p>
			</div>

			<div class="trust-item">
				<img
					src="<%= request.getContextPath() %>/utilities/immagini/credit-card.png"
					alt="Pagamenti">
				<p>Pagamenti sicuri</p>
			</div>

			<div class="trust-item">
				<img
					src="<%= request.getContextPath() %>/utilities/immagini/operator.png"
					alt="Supporto">
				<p>Assistenza clienti</p>
			</div>

		</div>
	</section>

	<jsp:include page="footer.jsp" />

	<script src="<%= request.getContextPath() %>/utilities/js/home.js"></script>

</body>
</html>
