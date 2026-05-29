<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<title>Pagamento ordine | FitExtreme</title>

<link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/base.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/header.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/footer.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/pagamento.css">


</style>

</head>

<body>

<jsp:include page="/header.jsp" />

<div class="payment-container">

    <h1>Pagamento ordine</h1>

    <div class="payment-box">

        <!-- FORM PAGAMENTO -->
        <div class="payment-form">

            <h2>Dati di pagamento</h2>
            <p class="sandbox-info">Pagamento sandbox — nessuna transazione reale verrà effettuata.</p>

            <form method="post" action="<%= request.getContextPath() %>/confermaOrdine">

                <label>Nome sulla carta</label>
                <input type="text" name="nomeCarta" placeholder="Mario Rossi" required>

                <label>Numero carta (sandbox)</label>
                <input type="text" name="numeroCarta" placeholder="card number"
                       pattern="[0-9]{16}" maxlength="16" required>

                <div class="row">
                    <div style="flex:1">
                        <label>Scadenza</label>
                        <input type="text" name="scadenza" placeholder="12/30"
                               pattern="(0[1-9]|1[0-2])/[0-9]{2}" maxlength="5" required>
                    </div>

                    <div style="flex:1">
                        <label>CVV</label>
                        <input type="text" name="cvv" placeholder="123"
                               pattern="[0-9]{3}" maxlength="3" required>
                    </div>
                </div>

                <button type="submit" class="btn-primary btn-large">
                    Conferma pagamento
                </button>

            </form>

        </div>

        <!-- RIEPILOGO ORDINE -->
        <div class="payment-summary">
            <h2>Riepilogo ordine</h2>

           

            <div class="summary-row totale">
                <span>Totale</span>
                <strong>€ <%= session.getAttribute("totalePagamento") %></strong>
            </div>
        </div>

    </div>

</div>

<jsp:include page="/footer.jsp" />

</body>
</html>
