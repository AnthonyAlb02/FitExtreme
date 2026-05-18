<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Contatti - FitExtreme</title>

   
    <link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/base.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/header.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/footer.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/contatti.css">
</head>

<body>

<jsp:include page="/header.jsp" />

<main class="page-container">

    <h1>Contatti</h1>
    <p>
        Hai bisogno di assistenza o vuoi metterti in contatto con noi?  
        Puoi utilizzare le informazioni qui sotto oppure compilare il modulo.
    </p>

    <!-- SEZIONE INFO -->
    <section>
        <h2>Informazioni di contatto</h2>

        <address>
            <p><strong>Email:</strong> <a href="mailto:support@fitExtreme.it">support@fitExtreme.it</a></p>
            <p><strong>Telefono:</strong> <a href="tel:+393393786329">+39 339 37 8632</a></p>
            <p><strong>Indirizzo:</strong><br>
                FitExtreme HQ<br>
                Via MegaFit 22<br>
                80100 – Italia
            </p>
        </address>
    </section>

    <!-- SEZIONE FORM -->
    <section>
    <h2>Scrivici un messaggio</h2>

    <!-- Messaggi di feedback (gestiti dalla servlet) -->
    <%
        String success = request.getParameter("success");
        String error = request.getParameter("error");
    %>

    <% if ("true".equals(success)) { %>
        <p class="form-success">Il tuo messaggio è stato inviato correttamente. Ti risponderemo al più presto.</p>
    <% } %>

    <% if ("true".equals(error)) { %>
        <p class="form-error">Si è verificato un errore durante l'invio. Riprova più tardi.</p>
    <% } %>

<form action="<%= request.getContextPath() %>/contatti-success.jsp" method="get" class="contact-form">

    <label for="nome">Nome e cognome</label>
    <input type="text" id="nome" name="nome" required placeholder="Inserisci il tuo nome">

    <label for="email">Email</label>
    <input type="email" id="email" name="email" required placeholder="nome@example.com">

    <label for="oggetto">Oggetto</label>
    <input type="text" id="oggetto" name="oggetto" required placeholder="Es. Informazioni su un ordine">

    <label for="messaggio">Messaggio</label>
    <textarea id="messaggio" name="messaggio" required placeholder="Scrivi qui il tuo messaggio..."></textarea>

    <button type="submit" class="btn btn-primary">Invia messaggio</button>
</form>


</section>


</main>
<script>
function sendMail(event) {
    event.preventDefault();

    const nome = document.getElementById("nome").value;
    const email = document.getElementById("email").value;
    const oggetto = document.getElementById("oggetto").value;
    const messaggio = document.getElementById("messaggio").value;

    const destinatario = "support@fitExtreme.it";

    const body =
        "Messaggio inviato da: " + nome + " (" + email + ")\n\n" +
        messaggio;

    const mailtoLink =
        "mailto:" + destinatario +
        "?subject=" + encodeURIComponent(oggetto) +
        "&body=" + encodeURIComponent(body);

    // Apre il client email
    window.location.href = mailtoLink;

    // Dopo 1 secondo, redirect alla pagina di conferma
    setTimeout(() => {
        window.location.href = "<%= request.getContextPath() %>/contatti-success.jsp";
    }, 800);
}
</script>


<jsp:include page="/footer.jsp" />

</body>
</html>
