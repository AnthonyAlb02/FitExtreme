<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page
	import="java.util.*, java.math.BigDecimal, model.beans.Articolo"%>

<html>
<head>
<title>Carrello</title>

<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/base.css">
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/header.css">
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/footer.css">
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/carrello.css">
</head>

<body>

	<%@ include file="header.jsp"%>

	<div class="cart-container">

		<h1>Il tuo carrello</h1>

		<%
            List<Articolo> prodotti = (List<Articolo>) request.getAttribute("prodotti");
            Map<Integer, Integer> quantita = (Map<Integer, Integer>) request.getAttribute("quantita");
            BigDecimal totale = (BigDecimal) request.getAttribute("totale");
        %>

		<% if (prodotti == null || prodotti.isEmpty()) { %>

		<p class="text-secondary">Il carrello è vuoto.</p>

		<% } else { %>

		<table class="cart-table">

			<tr>
				<th>Prodotto</th>
				<th>Prezzo</th>
				<th>Quantità</th>
				<th>Subtotale</th>
				<th></th>
			</tr>

			<% for (Articolo a : prodotti) {
                       int qta = quantita.get(a.getIdArticolo());
                       BigDecimal subtotale = a.getPrezzoListino().multiply(new BigDecimal(qta));
                %>

			<tr id="row-<%= a.getIdArticolo() %>">

				<td class="cart-product"><img
					src="<%= request.getContextPath() %>/utilities/immagini/<%= a.getImmagine() %>"
					class="cart-img"> <span><%= a.getNomeArticolo() %></span></td>

				<td><%= a.getPrezzoListino() %> €</td>

				<td>
					<div class="qty-controls">
						<button class="qty-btn qty-minus"
							data-id="<%= a.getIdArticolo() %>">-</button>
						<span class="qty-number" id="qty-<%= a.getIdArticolo() %>"><%= qta %></span>
						<button class="qty-btn qty-plus"
							data-id="<%= a.getIdArticolo() %>">+</button>
					</div>
				</td>

				<td id="subtotal-<%= a.getIdArticolo() %>"><%= subtotale %> €</td>

				<td>
					<button class="remove-btn remove-item"
						data-id="<%= a.getIdArticolo() %>">✖</button>
				</td>

			</tr>

			<% } %>

		</table>

		<div class="cart-summary">
			<h2 class="cart-total-text">
				Totale:
				<%= totale %>
				€
			</h2>
			<a href="checkout" class="btn-acquista">Acquista</a>
		</div>

		<% } %>

	</div>

	<%@ include file="footer.jsp"%>

	<script src="<%= request.getContextPath() %>/utilities/js/aggiorna.js"></script>

</body>
</html>
