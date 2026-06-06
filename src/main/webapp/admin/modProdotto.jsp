<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.beans.Articolo" %>
<%@ page import="model.beans.Categoria" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Modifica Prodotto</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/utilities/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/utilities/css/admin.css">

    <style>
        .alert-danger {
            background: #ffdddd;
            padding: 12px;
            border-left: 4px solid #d10000;
            margin-bottom: 20px;
        }
        .preview-img {
            width: 120px;
            border-radius: 6px;
            margin-top: 10px;
        }
    </style>
</head>

<body>

<div class="admin-wrapper">

    <div class="admin-sidebar">
        <h2>FitExtreme Admin</h2>

        <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/admin/utenti">Gestione utenti</a>
        <a href="${pageContext.request.contextPath}/admin/ordini">Gestione ordini</a>
        <a href="${pageContext.request.contextPath}/admin/prodotti">Gestione prodotti</a>
    </div>

    <div class="admin-content">

        <h1 class="admin-title">Modifica prodotto</h1>

        <%
            Articolo p = (Articolo) request.getAttribute("prodotto");
            String errore = (String) request.getAttribute("errore");
            Collection<Categoria> categorie = (Collection<Categoria>) request.getAttribute("categorie");
        %>

        <% if (errore != null) { %>
            <div class="alert-danger"><%= errore %></div>
        <% } %>

        <form id="formModifica"
              action="${pageContext.request.contextPath}/admin/modProdotto"
              method="post"
              enctype="multipart/form-data"
              style="max-width: 600px;">

            <input type="hidden" name="id" value="<%= p.getIdArticolo() %>">

            <div class="mb-3">
                <label class="form-label">Nome prodotto</label>
                <input type="text" name="nome" class="form-control"
                       value="<%= p.getNomeArticolo() %>" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Descrizione</label>
                <textarea name="descrizione" class="form-control" rows="4" required><%= p.getDescrizione() %></textarea>
            </div>

            <div class="mb-3">
                <label class="form-label">Prezzo (€)</label>
                <input type="number" name="prezzo" step="0.01" min="0"
                       class="form-control" value="<%= p.getPrezzoListino() %>" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Quantità disponibile</label>
                <input type="number" name="quantita" min="0"
                       class="form-control" value="<%= p.getQtaDisponibile() %>" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Categoria</label>
                <select name="categoria" class="form-select" required>
                    <% for (Categoria c : categorie) { %>
                        <option value="<%= c.getIdCategoria() %>"
                                <%= (c.getIdCategoria() == p.getIdCategoria()) ? "selected" : "" %>>
                            <%= c.getNome() %>
                        </option>
                    <% } %>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label">Immagine attuale</label><br>
                <img class="preview-img"
                     src="${pageContext.request.contextPath}/utilities/immagini/<%= p.getImmagine() %>">
            </div>

            <div class="mb-3">
                <label class="form-label">Nuova immagine (opzionale)</label>
                <input type="file" name="immagine" accept="image/*" class="form-control">
                <p style="font-size:13px;color:#666;margin-top:6px;">
                    Se non carichi nulla, l'immagine attuale rimarrà invariata.
                </p>
            </div>

            <div style="display:flex; gap:12px; margin-top:20px;">
                <button type="button" class="btn btn-primary" onclick="apriPopup()">Salva modifiche</button>

                <a href="${pageContext.request.contextPath}/admin/prodotti"
                   class="btn btn-secondary">
                    Annulla
                </a>
            </div>

        </form>

    </div>
</div>

<div id="popup-modifica" class="popup-overlay" style="display:none;">
    <div class="popup-box">
        <h3>Conferma Modifica</h3>
        <p>Vuoi davvero salvare le modifiche?</p>

        <div class="popup-buttons">
            <button id="modifica-confirm" class="btn btn-primary">Conferma</button>
            <button id="modifica-cancel" class="btn btn-secondary">Annulla</button>
        </div>
    </div>
</div>

<script>
    const popup = document.getElementById("popup-modifica");
    const form = document.getElementById("formModifica");

    function apriPopup() {
        popup.style.display = "flex";
    }

    document.getElementById("modifica-cancel").onclick = () => {
        popup.style.display = "none";
    };

    document.getElementById("modifica-confirm").onclick = () => {
        form.submit();
    };
</script>

</body>
</html>
