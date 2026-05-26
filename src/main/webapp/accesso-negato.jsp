<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Accesso negato - FitExtreme</title>

    <link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/base.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/header.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/footer.css">
</head>

<body>

<jsp:include page="/header.jsp" />

<main class="page-container">

    <h1>Accesso negato</h1>

    <p class="text-secondary">
        Non hai i permessi necessari per accedere a questa sezione del sito.
    </p>

    <p>
        Se ritieni che si tratti di un errore, contatta l’amministratore del sistema.
    </p>

    <a href="<%= request.getContextPath() %>/home" class="btn btn-primary" style="margin-top: 20px;">
        Torna alla Home
    </a>

</main>

<jsp:include page="/footer.jsp" />

</body>
</html>
