<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.beans.Ordine"%>

<%
    List<Ordine> ordini = (List<Ordine>) request.getAttribute("ordini");
%>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<title>I miei ordini | FitExtreme</title>

<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/base.css">
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/header.css">
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/footer.css">
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/ordini.css">
</head>

<body>

	<jsp:include page="/header.jsp" />

	<div class="orders-wrapper">

		<h1>I miei ordini</h1>

		<% if (ordini == null || ordini.isEmpty()) { %>

		<p class="no-orders">Non hai ancora effettuato ordini.</p>

		<% } else { %>

		<div class="orders-list">

			<% for (Ordine o : ordini) { %>

			<div class="order-card fade-in">

				<div class="order-header">
					<span class="order-id">Ordine #<%= o.getIdOrdine() %></span> <span
						class="order-date"><%= o.getDataOrdine() %></span>
				</div>

				<div class="order-body">
					<div class="order-row">
						<span>Totale:</span> <strong>€ <%= o.getImportoTotale() %></strong>
					</div>

					<div class="order-row">
						<span>Stato:</span> <span class="status"><%= o.getStatoAvanzamento() %></span>
					</div>
				</div>

				<a class="btn-primary btn-small"
					href="<%= request.getContextPath() %>/dettaglioOrdine?id=<%= o.getIdOrdine() %>">
					Vedi dettagli </a>

			</div>

			<% } %>

		</div>

		<% } %>

	</div>

	<jsp:include page="/footer.jsp" />

</body>
</html>
