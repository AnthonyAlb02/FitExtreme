<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<title>Login | FitExtreme</title>
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/footer.css">

<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/base.css">
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/auth.css">
</head>

<body>

	<section class="auth-container fade-in">

		<h1>Accedi</h1>

		<%
        Boolean tentativo = (Boolean) request.getAttribute("tentativo");
        String errore = (String) request.getAttribute("errore");
    %>

		<% if (tentativo != null && tentativo && errore != null) { %>
		<p class="auth-error"><%= errore %></p>
		<% } %>

		<form action="<%= request.getContextPath() %>/login" method="post"
			class="auth-form">

			<label>Email</label> <input type="email" name="email" required
				placeholder="Inserisci la tua email" autocomplete="email"> <label>Password</label>
			<input type="password" name="password" required
				placeholder="Inserisci la tua password"
				autocomplete="current-password">

			<button type="submit" class="auth-btn">Accedi</button>
		</form>

		<p class="auth-switch">
			Non sei registrato? <a
				href="<%= request.getContextPath() %>/register.jsp">Registrati</a>
		</p>

	</section>
	<jsp:include page="/footer.jsp" />

</body>
</html>
