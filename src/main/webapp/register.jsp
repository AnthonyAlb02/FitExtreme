<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html lang="it">
<head>
 <jsp:include page="/head.jsp" />
<meta charset="UTF-8">
<title>Registrazione | FitExtreme</title>
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/footer.css">

<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/base.css">
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/auth.css">
	<link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/header.css">
</head>

<body>
<jsp:include page="/header.jsp" />

	<section class="auth-container fade-in">

		<h1>Crea un account</h1>

		<% String errore = (String) request.getAttribute("errore"); %>
		<% if (errore != null) { %>
		<p class="auth-error"><%= errore %></p>
		<% } %>

	<form action="<%= request.getContextPath() %>/register" method="post" class="auth-form">

    <label>Nome</label>
    <input type="text" name="nome" required
           pattern="^[A-Za-zÀ-ÖØ-öø-ÿ]+(?: [A-Za-zÀ-ÖØ-öø-ÿ]+)*$"
           title="Solo lettere, senza numeri o simboli">

    <label>Cognome</label>
    <input type="text" name="cognome" required
           pattern="^[A-Za-zÀ-ÖØ-öø-ÿ]+(?: [A-Za-zÀ-ÖØ-öø-ÿ]+)*$"
           title="Solo lettere, senza numeri o simboli">

    <label>Email</label>
    <input type="email" name="email" id="email" required
           pattern="^[^\s@]+@[^\s@]+\.[^\s@]+$"
           title="Inserisci un indirizzo email valido">
    <p id="email-check" class="auth-check"></p>

    <label>Password</label>
    <input type="password" name="password" id="password" required
           pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^A-Za-z\d]).{8,}$"
           title="Minimo 8 caratteri, almeno 1 maiuscola, 1 minuscola, 1 numero e 1 carattere speciale">

    <label>Conferma Password</label>
    <input type="password" name="confirm" id="confirm" required>
    <p id="pass-check" class="auth-check"></p>

    <label>Telefono</label>
    <input type="tel" name="phone"
           pattern="^(?:\+39\s?)?(?:3\d{2}[\s.-]?\d{6,7}|0\d{1,3}[\s.-]?\d{5,8})$"
           title="Esempio: +39 333 1234567 o 06 12345678">

    <label>Indirizzo</label>
    <input type="text" name="indirizzo" required
           pattern="^[A-Za-zÀ-ÖØ-öø-ÿ0-9'., ]{5,100}$"
           title="Inserisci un indirizzo valido (es. Via Roma 10)">

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
