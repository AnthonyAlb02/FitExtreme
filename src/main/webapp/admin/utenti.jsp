<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.beans.Utente" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Gestione Utenti</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/utilities/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/utilities/css/admin.css">

    <style>
        /* FIX BOTTONI NELLA TABELLA */
        .azioni-cell {
            display: flex;
            gap: 10px;
            align-items: center;
        }

        .azioni-cell form {
            margin: 0;
            padding: 0;
        }
    </style>
</head>

<body>

<div class="admin-wrapper">

    <!-- SIDEBAR -->
    <div class="admin-sidebar">
        <h2>FitExtreme Admin</h2>

        <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/admin/utenti" style="background:#222;">Gestione utenti</a>
        <a href="${pageContext.request.contextPath}/admin/ordini">Gestione ordini</a>
        <a href="${pageContext.request.contextPath}/admin/prodotti">Gestione prodotti</a>
    </div>

    <!-- CONTENUTO PRINCIPALE -->
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
                    <th style="width: 220px;">Azioni</th>
                </tr>
            </thead>

            <tbody>
            <%
                Collection<Utente> utenti = (Collection<Utente>) request.getAttribute("utenti");
                if (utenti != null) {
                    for (Utente u : utenti) {

                        String nome = (u.getNome() != null ? u.getNome() : "N/D");
                        String cognome = (u.getCognome() != null ? u.getCognome() : "");
                        String telefono = (u.getTelefono() != null ? u.getTelefono() : "-");
                        String ruolo = u.getRuolo() != null ? u.getRuolo() : "guest";
            %>

            <tr>
                <td><%= u.getIdUtente() %></td>
                <td><%= nome %> <%= cognome %></td>
                <td><%= u.getEmail() %></td>
                <td><%= telefono %></td>

                <td>
                    <span class="badge <%= ruolo.equals("admin") ? "badge-admin" : "badge-user" %>">
                        <%= ruolo %>
                    </span>
                </td>

                <td class="azioni-cell">

                    <% if (!ruolo.equals("admin")) { %>
                    <form action="${pageContext.request.contextPath}/admin/promuovi-utente"
                          method="post"
                          data-nome="<%= nome + " " + cognome %>"
                          onsubmit="apriPopupPromuovi(this.dataset.nome, this); return false;">
                        <input type="hidden" name="id" value="<%= u.getIdUtente() %>">
                        <button class="btn btn-secondary" type="submit">Rendi Admin</button>
                    </form>
                    <% } %>

                    <form action="${pageContext.request.contextPath}/admin/elimina-utente"
                          method="post"
                          data-nome="<%= nome + " " + cognome %>"
                          onsubmit="apriPopupElimina(this.dataset.nome, this); return false;">
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
	