<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Chi Siamo - FitExtreme</title>
    
    <jsp:include page="/head.jsp" />
    

    <!-- Stessi stylesheet delle altre pagine -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/base.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/header.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/footer.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/aboutus.css">
</head>

<body>

<jsp:include page="/header.jsp" />

<main class="page-container">

    <h1>Chi Siamo</h1>
    <p>
        FitExtreme nasce con un obiettivo semplice: rendere il fitness accessibile a tutti,
        offrendo prodotti di qualità, un’esperienza d’acquisto moderna e un supporto clienti
        sempre presente.
    </p>

    <section>
        <h2>La nostra missione</h2>
        <p>
            Crediamo che allenarsi non significhi solo migliorare il proprio corpo, ma anche
            la propria vita. Per questo selezioniamo attrezzature, abbigliamento e accessori
            pensati per accompagnarti in ogni sfida, dal principiante all’atleta esperto.
        </p>
    </section>

    <section>
        <h2>I nostri valori</h2>
        <ul>
            <li><strong>Qualità:</strong> prodotti affidabili e testati.</li>
            <li><strong>Accessibilità:</strong> prezzi competitivi e offerte costanti.</li>
            <li><strong>Innovazione:</strong> soluzioni moderne per allenamenti più efficaci.</li>
            <li><strong>Supporto:</strong> assistenza clienti rapida e dedicata.</li>
        </ul>
    </section>

    <section>
        <h2>Il nostro team</h2>
        <p>
            Siamo un gruppo di appassionati di sport, tecnologia e design.  
            Ogni giorno lavoriamo per migliorare la piattaforma, ampliare il catalogo
            e offrire un servizio sempre più vicino alle esigenze dei nostri utenti.
        </p>
    </section>

    <section>
        <h2>Perché scegliere FitExtreme</h2>
        <p>
            Perché non vendiamo solo prodotti: costruiamo un’esperienza.  
            Dalla navigazione al checkout, dal supporto post‑vendita alla cura dei dettagli,
            vogliamo essere il tuo punto di riferimento nel mondo del fitness.
        </p>
    </section>

    <section>
        <h2>Contattaci</h2>
        <p>
            Per qualsiasi informazione puoi visitare la pagina
            <a href="<%= request.getContextPath() %>/contatti.jsp">Contatti</a>.
        </p>
    </section>

</main>

<jsp:include page="/footer.jsp" />

</body>
</html>
