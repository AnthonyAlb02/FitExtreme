<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<title>Pagamento ordine | FitExtreme</title>

<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/base.css">
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/header.css">
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/footer.css">

<style>
body {
	background: #f5f5f5;
}

.payment-wrapper {
	display: flex;
	justify-content: center;
	align-items: center;
	padding: 60px 20px;
}

.payment-card {
	background: #ffffff;
	width: 100%;
	max-width: 480px;
	padding: 35px;
	border-radius: 16px;
	box-shadow: 0 8px 25px rgba(0, 0, 0, 0.12);
	animation: fadeIn .4s ease;
}

.payment-card h1 {
	text-align: center;
	font-size: 26px;
	margin-bottom: 20px;
}

.sandbox-info {
	text-align: center;
	font-size: 14px;
	color: #777;
	margin-bottom: 25px;
}

.payment-card form label {
	font-weight: 600;
	margin-top: 12px;
	display: block;
}

.payment-card input {
	width: 100%;
	padding: 12px;
	margin-top: 6px;
	border-radius: 8px;
	border: 1px solid #ccc;
	font-size: 15px;
}

.row {
	display: flex;
	gap: 15px;
}

.btn-primary {
	display: inline-block;
	background: #FFC266;
	color: #111;
	padding: 10px 18px;
	border-radius: 8px;
	font-weight: 700;
	text-decoration: none;
	transition: 0.25s;
}

.btn-primary:hover {
	background: #e6a957;
}

.btn-large {
	width: 100%;
	text-align: center;
	padding: 14px;
	margin-top: 20px;
}

@
keyframes fadeIn {from { opacity:0;
	transform: translateY(10px);
}

to {
	opacity: 1;
	transform: translateY(0);
}
}
</style>
</head>

<body>

	<jsp:include page="/header.jsp" />

	<div class="payment-wrapper">

		<div class="payment-card">

			<h1>Pagamento ordine</h1>

			<p class="sandbox-info">Pagamento sandbox — nessuna transazione
				reale verrà effettuata.</p>

			<!-- ⭐ FORM NORMALE SENZA AJAX -->
			<form method="post"
				action="<%= request.getContextPath() %>/confermaOrdine">

				<label>Nome sulla carta</label> <input type="text" name="nomeCarta"
					placeholder="Mario Rossi" required> <label>Numero
					carta (sandbox)</label> <input type="text" name="numeroCarta"
					placeholder="4242424242424242" pattern="[0-9]{16}" maxlength="16"
					required>

				<div class="row">
					<div style="flex: 1">
						<label>Scadenza</label> <input type="text" name="scadenza"
							placeholder="12/30" pattern="(0[1-9]|1[0-2])/[0-9]{2}"
							maxlength="5" required>
					</div>

					<div style="flex: 1">
						<label>CVV</label> <input type="text" name="cvv" placeholder="123"
							pattern="[0-9]{3}" maxlength="3" required>
					</div>
				</div>

				<button type="submit" class="btn-primary btn-large">
					Conferma pagamento</button>

			</form>

		</div>

	</div>

	<jsp:include page="/footer.jsp" />

</body>
</html>
