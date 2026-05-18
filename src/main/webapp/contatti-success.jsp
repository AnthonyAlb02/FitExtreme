<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Messaggio inviato - FitExtreme</title>

    <link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/base.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/header.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/footer.css">
</head>

<body>

<jsp:include page="/header.jsp" />

<main class="page-container">
    <h1>Messaggio inviato!</h1>
    <p>
        Il tuo messaggio è stato inviato correttamente tramite il tuo client email.<br>
        Ti risponderemo il prima possibile.
    </p>

    <a href="<%= request.getContextPath() %>/home" class="btn btn-primary">Torna alla Home</a>
</main>

<jsp:include page="/footer.jsp" />

</body>
</html>
