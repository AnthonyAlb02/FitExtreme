<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html lang="it">
<head>
 <jsp:include page="/head.jsp" />
<meta charset="UTF-8">
<title>Ordine completato | FitExtreme</title>

<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/base.css">
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/header.css">
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/footer.css">

<style>
.success-box {
	max-width: 700px;
	margin: 60px auto;
	background: #fff;
	padding: 30px;
	border-radius: 12px;
	text-align: center;
	box-shadow: 0 4px 14px rgba(0, 0, 0, 0.1);
}

.success-box h1 {
	color: #FFC266;
	font-size: 32px;
	margin-bottom: 10px;
}

.success-box p {
	font-size: 18px;
	margin-bottom: 15px;
}

.btn-primary {
	background: #FFC266;
	padding: 12px 20px;
	border-radius: 8px;
	text-decoration: none;
	color: #111;
	font-weight: 700;
	cursor: pointer;
	display: inline-block;
	margin-top: 15px;
}
</style>
</head>

<body>

	<jsp:include page="/header.jsp" />

	<div class="success-box">
		<h1>Ordine completato!</h1>
		<p>Il tuo ordine è stato registrato correttamente.</p>
		<p>
			ID ordine: <strong>#<%= request.getAttribute("idOrdine") %></strong>
		</p>
		<p>La ricevuta è stata inviata alla tua email.</p>

		<!-- ⭐ BOTTONE PER GENERARE E VISUALIZZARE LA FATTURA -->
		<button id="btnFattura" class="btn-primary">Visualizza
			fattura</button>

		<br>
		<br> <a href="<%= request.getContextPath() %>/catalogo"
			class="btn-primary"> Torna al catalogo </a>
	</div>

	<jsp:include page="/footer.jsp" />

	<script>
document.getElementById("btnFattura").addEventListener("click", async () => {

    const idOrdine = "<%= request.getAttribute("idOrdine") %>";

    const res = await fetch("<%= request.getContextPath() %>/generaFattura", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: "idOrdine=" + idOrdine
    });

    if (!res.ok) {
        alert("Errore durante la generazione della fattura.");
        return;
    }

    const blob = await res.blob();
    const url = URL.createObjectURL(blob);

    window.open(url, "_blank");
});
</script>
	<script>
document.addEventListener("DOMContentLoaded", () => {
    const badge = document.getElementById("cart-count");
    if (badge) badge.textContent = "0";
});
</script>

</body>
</html>
