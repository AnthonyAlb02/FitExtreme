<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Privacy Policy - FitExtreme</title>

    <!-- Stessi stylesheet della Cookie Policy -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/base.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/header.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/footer.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/policy.css">

    
    

</head>

<body>

<jsp:include page="/header.jsp" />

<div class="page-container">

    <h1 class="policy-title">Privacy Policy</h1>
    <p class="policy-intro">
        La presente Privacy Policy descrive come FitExtreme raccoglie, utilizza, conserva e protegge
        i dati personali degli utenti che interagiscono con il sito e i servizi offerti.
    </p>

    <h2>1. Titolare del trattamento</h2>
    <p>
        Il titolare del trattamento dei dati è FitExtreme.  
        Per qualsiasi richiesta o chiarimento è possibile contattarci tramite la sezione
        <a href="<%= request.getContextPath() %>/contatti.jsp">Contatti</a>.
    </p>

    <h2>2. Tipologie di dati raccolti</h2>

    <h3>Dati forniti volontariamente dall’utente</h3>
    <ul>
        <li>Nome e cognome</li>
        <li>Email</li>
        <li>Indirizzo di spedizione</li>
        <li>Numero di telefono</li>
        <li>Dati di pagamento (gestiti tramite provider sicuri)</li>
    </ul>

    <h3>Dati raccolti automaticamente</h3>
    <p>
        Durante la navigazione vengono raccolti automaticamente alcuni dati tecnici, come:
    </p>
    <ul>
        <li>Indirizzo IP</li>
        <li>Tipo di browser e dispositivo</li>
        <li>Pagine visitate</li>
        <li>Tempo di permanenza sul sito</li>
    </ul>

    <h2>3. Finalità del trattamento</h2>
    <p>I dati personali vengono utilizzati per:</p>
    <ul>
        <li>Gestire ordini, pagamenti e spedizioni</li>
        <li>Creare e gestire account utente</li>
        <li>Inviare comunicazioni relative agli ordini</li>
        <li>Migliorare l’esperienza di navigazione</li>
        <li>Prevenire attività fraudolente</li>
        <li>Inviare newsletter (solo previo consenso)</li>
    </ul>

    <h2>4. Base giuridica del trattamento</h2>
    <p>
        Il trattamento dei dati si basa su:
    </p>
    <ul>
        <li>Consenso dell’utente</li>
        <li>Esecuzione di un contratto (acquisto di prodotti)</li>
        <li>Obblighi di legge</li>
        <li>Legittimo interesse del titolare</li>
    </ul>

    <h2>5. Conservazione dei dati</h2>
    <p>
        I dati vengono conservati per il tempo necessario alle finalità indicate o secondo gli obblighi di legge.
    </p>

    <h2>6. Sicurezza dei dati</h2>
    <p>
        FitExtreme adotta misure tecniche e organizzative per proteggere i dati personali da accessi non autorizzati,
        perdita o divulgazione.
    </p>

    <h2>7. Diritti dell’utente</h2>
    <p>L’utente può in qualsiasi momento:</p>
    <ul>
        <li>Richiedere l’accesso ai propri dati</li>
        <li>Chiedere la rettifica o cancellazione</li>
        <li>Limitare o opporsi al trattamento</li>
        <li>Richiedere la portabilità dei dati</li>
        <li>Revocare il consenso</li>
    </ul>

    <h2>8. Cookie</h2>
    <p>
        Per informazioni dettagliate sull’uso dei cookie, consulta la nostra
        <a href="<%= request.getContextPath() %>/cookie-policy.jsp">Cookie Policy</a>.
    </p>

    <h2>9. Modifiche alla Privacy Policy</h2>
    <p>
        FitExtreme può aggiornare questa informativa in qualsiasi momento. Le modifiche saranno pubblicate su questa pagina.
    </p>

    <div style="margin-top: 30px;">
        <a class="btn btn-primary" href="<%= request.getContextPath() %>/home">Torna alla Home</a>
    </div>

</div>

<jsp:include page="/footer.jsp" />

</body>
</html>
