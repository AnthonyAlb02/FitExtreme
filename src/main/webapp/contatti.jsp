<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <jsp:include page="/head.jsp" />
    <meta charset="UTF-8">
    <title>Contatti - FitExtreme</title>

    <link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/base.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/header.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/footer.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/utilities/css/contatti.css">

    <style>
        .form-success {
            margin-top: 20px;
            padding: 15px;
            background: #e6ffe6;
            border-left: 5px solid #28a745;
            color: #155724;
            font-size: 1.1rem;
            border-radius: 6px;
            animation: fadeIn 0.4s ease-out;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(5px); }
            to   { opacity: 1; transform: translateY(0); }
        }
    </style>
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
            <p><strong>Email:</strong> support@fitExtreme.it</p>
            <p><strong>Telefono:</strong> +39 339 37 8632</p>
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

        <!-- MESSAGGIO DI CONFERMA -->
        <div id="successMessage" class="form-success" style="display:none;">
            Il tuo messaggio è stato inviato correttamente. Ti risponderemo al più presto.
        </div>

        <!-- FORM FAKE -->
        <form onsubmit="sendFake(event)" class="contact-form">

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
function sendFake(event) {
    event.preventDefault();

    // Mostra messaggio di conferma
    document.getElementById("successMessage").style.display = "block";

    // Reset del form
    event.target.reset();
}
</script>

<jsp:include page="/footer.jsp" />

</body>
</html>
