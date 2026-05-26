<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.beans.Utente" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Gestione Utenti</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/utilities/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/utilities/css/admin.css">
</head>
<body>

<div class="admin-content">

    <h1 class="admin-title">Gestione Utenti</h1>

    <table class="admin-table">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Email</th>
                <th>Telefono</th>
                <th>Ruolo</th>
                <th>Azioni</th>
            </tr>
        </thead>
        <tbody>
        <%
            Collection<Utente> utenti = (Collection<Utente>) request.getAttribute("utenti");
            if (utenti != null) {
                for (Utente u : utenti) {
        %>
        <tr>
            <td><%= u.getIdUtente() %></td>
            <td><%= u.getNome() %> <%= u.getCognome() %></td>
            <td><%= u.getEmail() %></td>
            <td><%= u.getTelefono() %></td>
            <td>
                <span class="badge <%= u.getRuolo().equals("admin") ? "badge-admin" : "badge-user" %>">
                    <%= u.getRuolo() %>
                </span>
            </td>
            <td>
                <% if (!u.getRuolo().equals("admin")) { %>
                <form action="${pageContext.request.contextPath}/admin/promuovi-utente"
                      method="post"
                      onsubmit="apriPopupPromuovi(this.dataset.nome, this); return false;"
                      data-nome="<%= u.getNome() %> <%= u.getCognome() %>"
                      style="display:inline;">
                    <input type="hidden" name="id" value="<%= u.getIdUtente() %>">
                    <button class="btn btn-secondary" type="submit">Rendi Admin</button>
                </form>
                <% } %>

                <form action="${pageContext.request.contextPath}/admin/elimina-utente"
                      method="post"
                      onsubmit="apriPopupElimina(this.dataset.nome, this); return false;"
                      data-nome="<%= u.getNome() %> <%= u.getCognome() %>"
                      style="display:inline;">
                    <input type="hidden" name="id" value="<%= u.getIdUtente() %>">
                    <button class="btn btn-primary" type="submit">Elimina</button>
                </form>
            </td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>

</div>

<!-- POPUP ELIMINAZIONE -->
<div id="popup-elimina" class="popup-overlay" style="display:none;">
    <div class="popup-box">
        <h3>Conferma Eliminazione</h3>
        <p id="popup-elimina-message"></p>
        <div class="popup-buttons">
            <button id="elimina-confirm" class="btn btn-primary">Elimina</button>
            <button id="elimina-cancel" class="btn btn-secondary">Annulla</button>
        </div>
    </div>
</div>

<!-- POPUP PROMOZIONE -->
<div id="popup-promuovi" class="popup-overlay" style="display:none;">
    <div class="popup-box">
        <h3>Conferma Promozione</h3>
        <p id="popup-promuovi-message"></p>
        <div class="popup-buttons">
            <button id="promuovi-confirm" class="btn btn-secondary">Conferma</button>
            <button id="promuovi-cancel" class="btn btn-primary">Annulla</button>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/utilities/js/admin/elimina.js"></script>

</body>
</html>