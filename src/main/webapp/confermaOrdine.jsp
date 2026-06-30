<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.beans.Articolo, java.util.List, java.util.Map, java.math.BigDecimal, java.math.RoundingMode" %>

<%-- ===================== RECUPERO DATI DA SESSIONE ===================== --%>
<%
    List<Articolo> prodotti      = (List<Articolo>)       session.getAttribute("prodottiPagamento");
    Map<Integer,Integer> quantita = (Map<Integer,Integer>) session.getAttribute("quantitaPagamento");
    BigDecimal totale             = (BigDecimal)           session.getAttribute("totalePagamento");
    String errorMessage           = (String)               request.getAttribute("errorMessage");

    if (totale == null) totale = BigDecimal.ZERO;

    BigDecimal imponibile = totale.divide(new BigDecimal("1.22"), 2, RoundingMode.HALF_UP);
    BigDecimal iva        = totale.subtract(imponibile).setScale(2, RoundingMode.HALF_UP);
%>

<!DOCTYPE html>
<html lang="it">
<head>
 <jsp:include page="/head.jsp" />
    <meta charset="UTF-8">
    
    <title>Pagamento ordine | FitExtreme</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/base.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/header.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/footer.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/pagamento.css">
</head>

<body>
<script src="<%= request.getContextPath() %>/utilities/js/pagamento.js"></script>


<jsp:include page="/header.jsp" />

<div class="payment-container">

    <h1>Pagamento ordine</h1>

    <div class="payment-box">

        <!-- ================== FORM ================== -->
        <div class="payment-form">

            <% if (errorMessage != null) { %>
                <div class="error-box"><%= errorMessage %></div>
            <% } %>

            <form method="post" action="<%= request.getContextPath() %>/confermaOrdine"
                  id="paymentForm" novalidate>

                <!-- Dati carta -->
                <div class="form-section">
                    <h3 class="section-title">Dati di pagamento</h3>

                    <div class="form-group">
                        <label>Nome sulla carta</label>
                        <input type="text" name="nomeCarta" placeholder="Nome e Cognome" required
                               oninvalid="this.setCustomValidity('Inserisci il nome esattamente come appare sulla carta')"
                               oninput="this.setCustomValidity('')">
                    </div>

                    <div class="form-group">
                        <label>Numero carta</label>
                        <input type="text" name="numeroCarta" placeholder="1234123412341234"
                               pattern="[0-9]{16}" maxlength="16" required
                               oninvalid="this.setCustomValidity('Il numero carta deve contenere 16 cifre senza spazi')"
                               oninput="this.setCustomValidity('')">
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label>Scadenza</label>
                            <input type="text" name="scadenza" placeholder="MM/AA"
                                   pattern="(0[1-9]|1[0-2])\/[0-9]{2}" maxlength="5" required
                                   oninvalid="this.setCustomValidity('Formato non valido. Usa MM/AA, es. 12/30')"
                                   oninput="this.setCustomValidity('')">
                        </div>
                        <div class="form-group">
                            <label>CVV</label>
                            <input type="text" name="cvv" placeholder="123"
                                   pattern="[0-9]{3}" maxlength="3" required
                                   oninvalid="this.setCustomValidity('Il CVV deve essere di 3 cifre')"
                                   oninput="this.setCustomValidity('')">
                        </div>
                    </div>
                </div>

                <!-- Indirizzo spedizione -->
                <div class="form-section">
                    <h3 class="section-title">Indirizzo di spedizione</h3>

                    <div class="form-group">
                        <label>Nome destinatario</label>
                        <input type="text" name="nomeDestinatario" placeholder="Nome Cognome" required
                               pattern=".{2,100}"
                               oninvalid="this.setCustomValidity('Inserisci il nome del destinatario (minimo 2 caratteri)')"
                               oninput="this.setCustomValidity('')">
                    </div>

                    <div class="form-group">
                        <label>Via e numero civico</label>
                        <input type="text" name="via" placeholder="Via Roma 10" required
                               pattern=".{3,100}"
                               oninvalid="this.setCustomValidity('Inserisci via e numero civico (minimo 3 caratteri)')"
                               oninput="this.setCustomValidity('')">
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label>CAP</label>
                            <input type="text" name="cap" placeholder="10100"
                                   pattern="[0-9]{5}" maxlength="5" required
                                   oninvalid="this.setCustomValidity('Inserisci un CAP valido di 5 cifre')"
                                   oninput="this.setCustomValidity('')">
                        </div>
                        <div class="form-group">
                            <label>Città</label>
                            <input type="text" name="citta" placeholder="Torino" required
                                   pattern="[A-Za-zÀ-ÿ'\\- ]{2,50}"
                                   oninvalid="this.setCustomValidity('Inserisci una città valida')"
                                   oninput="this.setCustomValidity('')">
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label>Provincia</label>
                            <input type="text" name="provincia" placeholder="TO" required
                                   pattern="[A-Za-zÀ-ÿ'\\- ]{2}"
                                   oninvalid="this.setCustomValidity('Inserisci la provincia')"
                                   oninput="this.setCustomValidity('')">
                        </div>
                        <div class="form-group">
                            <label>Paese</label>
                            <input type="text" name="paese" placeholder="Italia" required
                                   pattern="[A-Za-zÀ-ÿ'\\- ]{2,50}" value="Italia"
                                   oninvalid="this.setCustomValidity('Inserisci il paese')"
                                   oninput="this.setCustomValidity('')">
                        </div>
                    </div>
                </div>

                <button type="submit" class="btn-primary btn-large">
                    Conferma pagamento
                </button>

            </form>

        </div>

        <!-- ================== RIEPILOGO ================== -->
        <div class="payment-summary">

            <h2>Riepilogo ordine</h2>

            <div class="summary-items">
                <% if (prodotti != null) {
                       for (Articolo a : prodotti) {
                           int qta = (quantita != null && quantita.get(a.getIdArticolo()) != null)
                                     ? quantita.get(a.getIdArticolo()) : 1;
                           BigDecimal subtotale = a.getPrezzoListino()
                                   .multiply(new BigDecimal(qta))
                                   .setScale(2, RoundingMode.HALF_UP);
                %>
                    <div class="summary-item">
                        <span class="summary-item-name">
                            <%= a.getNomeArticolo() %>
                            <span class="summary-qty">× <%= qta %></span>
                        </span>
                        <span class="summary-item-price">€ <%= subtotale %></span>
                    </div>
                <%
                       }
                   }
                %>
            </div>

            <div class="summary-divider"></div>

            <div class="summary-totals">
                <div class="summary-row">
                    <span>Imponibile</span>
                    <span>€ <%= imponibile %></span>
                </div>
                <div class="summary-row">
                    <span>IVA (22%)</span>
                    <span>€ <%= iva %></span>
                </div>
                <div class="summary-row">
                    <span>Spedizione</span>
                    <span class="free-shipping">Gratuita</span>
                </div>
            </div>

            <div class="summary-divider"></div>

            <div class="summary-row totale">
                <span>Totale</span>
                <strong>€ <%= totale %></strong>
            </div>

        </div>

    </div>

</div>



<jsp:include page="/footer.jsp" />

<div id="confirmPopup" class="confirm-popup hidden">
    <div class="confirm-box">
        <h3>Conferma ordine</h3>
        <p>Vuoi procedere all'acquisto?</p>

        <div class="confirm-buttons">
            <button id="confirmYes" class="btn-primary">Sì, procedi</button>
            <button id="confirmNo" class="btn-secondary">Annulla</button>
        </div>
    </div>
</div>

<



</body>
</html>