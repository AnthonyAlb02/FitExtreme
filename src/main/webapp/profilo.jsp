<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="model.beans.Utente"%>

<%
    Utente utente = (Utente) request.getAttribute("utente");
%>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<title>Profilo Utente | FitExtreme</title>

<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/base.css">
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/header.css">
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/footer.css">
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/profilo.css">
</head>

<body>

	<jsp:include page="/header.jsp" />

	<div class="profile-wrapper">

		<div class="profile-card">

			<h1>Il tuo profilo</h1>

			<div class="profile-info">

				<div class="info-box">
					<h3>Nome</h3>
					<p><%= utente.getNome() %>
						<%= utente.getCognome() %></p>
				</div>

				<div class="info-box">
					<h3>Email</h3>
					<p><%= utente.getEmail() %></p>
				</div>

				<div class="info-box">
					<h3>Telefono</h3>
					<p><%= utente.getTelefono() != null ? utente.getTelefono() : "—" %></p>
				</div>

				<div class="info-box">
					<h3>Indirizzo</h3>
					<p><%= utente.getIndirizzoSpedizione() != null ? utente.getIndirizzoSpedizione() : "—" %></p>
				</div>

			</div>

			<div class="btn-row">
				<a href="<%= request.getContextPath() %>/ordini" class="btn-primary">I
					miei ordini</a>
			</div>

		</div>

	</div>

	<jsp:include page="/footer.jsp" />

</body>
</html>
