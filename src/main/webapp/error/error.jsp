<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Errore</title>

    <link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/base.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/header.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/footer.css">
</head>

<body>

<jsp:include page="/header.jsp" />

<main class="page-container">

    <h1>Si è verificato un errore</h1>

    <div style="text-align:center; margin: 30px 0;">
    <div style="
        font-size: 4rem;
        font-weight: 700;
        color: #8C5A2B;
        margin-bottom: 10px;
    ">
        <%= request.getAttribute("javax.servlet.error.status_code") %>
    </div>

    <p class="text-secondary" style="font-size: 1.2rem;">
        Si è verificato un errore durante l'elaborazione della richiesta.
    </p>
</div>


    <%
        Throwable ex = (Throwable) request.getAttribute("javax.servlet.error.exception");
        if (ex != null) {
    %>
        <p>Dettagli: <%= ex.getMessage() %></p>
    <% } %>

    <a href="<%= request.getContextPath() %>/home" class="btn btn-primary" style="margin-top:20px;">
        Torna alla Home
    </a>

</main>

<jsp:include page="/footer.jsp" />

</body>
</html>
