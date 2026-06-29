<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html lang="it">
<head>
 <jsp:include page="/head.jsp" />
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
				placeholder="Inserisci la tua email" autocomplete="email"> 
			<label>Password</label>
					<div class="input-icon-wrapper">
					    <input type="password" name="password" id="password" required
					           placeholder="Inserisci la tua password"
					           autocomplete="current-password">
					    <button type="button" class="toggle-password" id="togglePassword" tabindex="-1" aria-label="Mostra password">
					        <svg id="eyeIcon" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24"
					             fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
					            <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/>
					            <circle cx="12" cy="12" r="3"/>
					        </svg>
					    </button>
</div>
			<button type="submit" class="auth-btn">Accedi</button>
		</form>

		<p class="auth-switch">
			Non sei registrato? <a
				href="<%= request.getContextPath() %>/register.jsp">Registrati</a>
		</p>

	</section>
	<jsp:include page="/footer.jsp" />
	
<script>
    const togglePassword = document.getElementById("togglePassword");
    const passwordInput  = document.getElementById("password");
    const eyeIcon        = document.getElementById("eyeIcon");

    const eyeOpen = `
        <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/>
        <circle cx="12" cy="12" r="3"/>
    `;
    const eyeClosed = `
        <path d="M17.94 17.94A10.94 10.94 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94"/>
        <path d="M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19"/>
        <line x1="1" y1="1" x2="23" y2="23"/>
    `;

    togglePassword.addEventListener("click", () => {
        const visible = passwordInput.type === "text";
        passwordInput.type  = visible ? "password" : "text";
        eyeIcon.innerHTML   = visible ? eyeOpen : eyeClosed;
        togglePassword.setAttribute("aria-label", visible ? "Mostra password" : "Nascondi password");
    });
</script>

</body>
</html>
