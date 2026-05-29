<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.beans.Categoria" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Inserisci Prodotto</title>

    <!-- CSS GLOBALI -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/utilities/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/utilities/css/admin.css">
</head>
<body>

<div class="admin-wrapper">

    <!-- SIDEBAR -->
    <div class="admin-sidebar">
        <h2>FitExtreme Admin</h2>

        <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/admin/utenti">Gestione utenti</a>
        <a href="${pageContext.request.contextPath}/admin/ordini">Gestione ordini</a>
        <a href="${pageContext.request.contextPath}/admin/prodotti">Gestione prodotti</a>
    </div>

    <!-- CONTENUTO PRINCIPALE -->
    <div class="admin-content">

        <h1 class="admin-title">Inserisci nuovo prodotto</h1>

        <!-- Messaggio di errore -->
        <%
            String errore = (String) request.getAttribute("errore");
            if (errore != null) {
        %>
            <div class="alert-danger" style="margin-bottom:20px;">
                <%= errore %>
            </div>
        <% } %>

        <form id="formInserimento"
              action="${pageContext.request.contextPath}/admin/insProdotto"
              method="post"
              enctype="multipart/form-data"
              style="max-width: 600px;">

            <!-- Nome -->
            <div class="mb-3">
                <label class="form-label">Nome prodotto</label>
                <input type="text" name="nome" class="form-control" required>
            </div>

            <!-- Descrizione -->
            <div class="mb-3">
                <label class="form-label">Descrizione</label>
                <textarea name="descrizione" class="form-control" rows="4" required></textarea>
            </div>

            <!-- Prezzo -->
            <div class="mb-3">
                <label class="form-label">Prezzo (€)</label>
                <input type="number" name="prezzo" step="0.01" min="0" class="form-control" required>
            </div>

            <!-- Quantità -->
            <div class="mb-3">
                <label class="form-label">Quantità disponibile</label>
                <input type="number" name="quantita" min="0" class="form-control" required>
            </div>

            <!-- Categoria -->
            <div class="mb-3">
                <label class="form-label">Categoria</label>
                <select name="categoria" class="form-select" required>
                    <option value="">-- Seleziona categoria --</option>

                    <%
                        Collection<Categoria> categorie =
                                (Collection<Categoria>) request.getAttribute("categorie");

                        if (categorie != null) {
                            for (Categoria c : categorie) {
                    %>
                        <option value="<%= c.getIdCategoria() %>">
                            <%= c.getNome() %>
                        </option>
                    <%
                            }
                        }
                    %>
                </select>
            </div>

            <!-- Immagine -->
            <div class="mb-3">
                <label class="form-label">Immagine prodotto</label>
                <input type="file" name="immagine" accept="image/*" class="form-control" required>
            </div>

            <!-- Pulsanti -->
            <div style="display:flex; gap:12px; margin-top:20px;">
                <button type="button" class="btn btn-primary" onclick="apriPopupInserisci()">Inserisci prodotto</button>

                <a href="${pageContext.request.contextPath}/admin/prodotti"
                   class="btn btn-secondary">
                    Annulla
                </a>
            </div>

        </form>

    </div>
</div>

<!-- POPUP CONFERMA INSERIMENTO -->
<div id="popup-inserisci" class="popup-overlay" style="display:none;">
    <div class="popup-box">
        <h3>Conferma Inserimento</h3>
        <p>Vuoi davvero inserire questo articolo?</p>

        <div class="popup-buttons">
            <button id="inserisci-confirm" class="btn btn-primary">Conferma</button>
            <button id="inserisci-cancel" class="btn btn-secondary">Annulla</button>
        </div>
    </div>
</div>

<script>
    const popup = document.getElementById("popup-inserisci");
    const form = document.getElementById("formInserimento");

    function apriPopupInserisci() {
        popup.style.display = "flex";
    }

    document.getElementById("inserisci-cancel").onclick = () => {
        popup.style.display = "none";
    };

    document.getElementById("inserisci-confirm").onclick = () => {
        form.submit();
    };
</script>

</body>
</html>
