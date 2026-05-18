<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Cookie Policy - FitExtreme</title>
    <link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/base.css">
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/header.css">
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/footer.css">
	<link rel="stylesheet"
	href="<%= request.getContextPath() %>/utilities/css/policy.css">

   
    
</head>

<body>

<jsp:include page="/header.jsp" />



<div class="page-container">

    <h1 class="policy-title">Cookie Policy</h1>
    <p class="policy-intro">
        In questa pagina trovi informazioni chiare e trasparenti su come FitExtreme utilizza i cookie
        per offrirti un’esperienza di navigazione moderna, personalizzata e sicura.
    </p>

    <h2>1. Cosa sono i cookie?</h2>
    <p>
        I cookie sono piccoli file di testo che i siti web salvano sul tuo dispositivo per ricordare
        informazioni utili, migliorare la navigazione e personalizzare i contenuti.
    </p>

    <h2>2. Tipologie di cookie utilizzati</h2>

    <h3>Cookie tecnici (necessari)</h3>
    <p>
        Sono indispensabili per il corretto funzionamento del sito e non richiedono consenso.
        Permettono, ad esempio, di mantenere gli articoli nel carrello o gestire il login.
    </p>

    <h3>Cookie analitici</h3>
    <p>
        Raccolgono dati statistici anonimi sull’utilizzo del sito (pagine visitate, tempo di permanenza, ecc.)
        per migliorare le prestazioni e l’esperienza utente.
    </p>

    <h3>Cookie di profilazione (previo consenso)</h3>
    <p>
        Utilizzati per mostrarti contenuti e offerte personalizzate in base ai tuoi interessi.
    </p>

    <h2>3. Cookie utilizzati da FitExtreme</h2>
    <ul>
        <li><strong>cookieConsent</strong> – memorizza la tua scelta riguardo l’accettazione dei cookie.</li>
        <li>Cookie tecnici per login, carrello e funzionalità essenziali.</li>
        <li>Eventuali cookie di terze parti (analytics o marketing).</li>
    </ul>

    <h2>4. Gestione del consenso</h2>
    <p>
        Al primo accesso puoi scegliere se accettare o rifiutare i cookie non necessari tramite il banner.
        Puoi modificare la tua scelta cancellando i cookie dal browser.
    </p>

    <h2>5. Come disabilitare i cookie dal browser</h2>
    <p>
        Puoi gestire o disattivare i cookie dalle impostazioni del tuo browser (Chrome, Firefox, Safari, Edge).
    </p>

    <h2>6. Contatti</h2>
    <p>
        Per informazioni sulla presente Cookie Policy puoi visitare la sezione
        <a href="<%= request.getContextPath() %>/contatti.jsp">Contatti</a>.
    </p>

    <div style="margin-top: 30px;">
        <a class="btn btn-primary" href="<%= request.getContextPath() %>/home">Torna alla Home</a>
    </div>

</div>

<jsp:include page="/footer.jsp" />

</body>
</html>
