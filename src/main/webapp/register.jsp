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
<div class="input-icon-wrapper">
    <input type="password" name="password" id="password" required
           pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^A-Za-z\d]).{8,}$"
           title="Minimo 8 caratteri, almeno 1 maiuscola, 1 minuscola, 1 numero e 1 carattere speciale">
    <button type="button" class="toggle-password" id="togglePassword" aria-label="Mostra password">
        <svg id="eyeIcon" xmlns="http://www.w3.org/2000/svg" width="20" height="20"
             viewBox="0 0 24 24" fill="none" stroke="currentColor"
             stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/>
            <circle cx="12" cy="12" r="3"/>
        </svg>
    </button>
</div>

<label>Conferma Password</label>
<div class="input-icon-wrapper">
    <input type="password" name="confirm" id="confirm" required>
    <button type="button" class="toggle-password" id="toggleConfirm" aria-label="Mostra password">
        <svg id="eyeIconConfirm" xmlns="http://www.w3.org/2000/svg" width="20" height="20"
             viewBox="0 0 24 24" fill="none" stroke="currentColor"
             stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/>
            <circle cx="12" cy="12" r="3"/>
        </svg>
    </button>
</div>
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
	<script type="text/javascript">
	const eyeOpen   = `<path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/><circle cx="12" cy="12" r="3"/>`;
	const eyeClosed = `<path d="M17.94 17.94A10.94 10.94 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94"/><path d="M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19"/><line x1="1" y1="1" x2="23" y2="23"/>`;

	function setupToggle(btnId, inputId, iconId) {
	    const btn   = document.getElementById(btnId);
	    const input = document.getElementById(inputId);
	    const icon  = document.getElementById(iconId);

	    btn.addEventListener("click", () => {
	        const visible  = input.type === "text";
	        input.type     = visible ? "password" : "text";
	        icon.innerHTML = visible ? eyeOpen : eyeClosed;
	    });
	}

	setupToggle("togglePassword", "password",  "eyeIcon");
	setupToggle("toggleConfirm",  "confirm",   "eyeIconConfirm");
	</script>

</body>
</html>
