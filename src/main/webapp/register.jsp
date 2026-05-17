<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<title>Registrazione | FitExtreme</title>
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/footer.css">

<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/base.css">
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/auth.css">
</head>

<body>

	<section class="auth-container fade-in">

		<h1>Crea un account</h1>

		<% String errore = (String) request.getAttribute("errore"); %>
		<% if (errore != null) { %>
		<p class="auth-error"><%= errore %></p>
		<% } %>

		<form action="<%= request.getContextPath() %>/register" method="post"
			class="auth-form">

			<label>Nome</label> <input type="text" name="nome" required>

			<label>Cognome</label> <input type="text" name="cognome" required>

			<label>Email</label> <input type="email" name="email" id="email"
				required>
			<p id="email-check" class="auth-check"></p>

			<label>Password</label> <input type="password" name="password"
				id="password" required> <label>Conferma Password</label> <input
				type="password" name="confirm" id="confirm" required>
			<p id="pass-check" class="auth-check"></p>

			<label>Telefono</label> <input type="text" name="telefono"> <label>Indirizzo</label>
			<input type="text" name="indirizzo">

			<button type="submit" class="auth-btn">Registrati</button>
		</form>

		<p class="auth-switch">
			Hai già un account? <a
				href="<%= request.getContextPath() %>/login.jsp">Accedi</a>
		</p>

	</section>

	<script src="<%= request.getContextPath() %>/utilities/js/register.js"></script>
	<jsp:include page="/footer.jsp" />

</body>
</html>
