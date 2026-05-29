<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.beans.Articolo" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Gestione Prodotti - Admin</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/utilities/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/utilities/css/admin.css">

    <style>
        .azioni-cell {
            display: flex;
            gap: 10px;
            align-items: center;
        }
        .azioni-cell form {
            margin: 0;
        }
    </style>
</head>

<body>

<div class="admin-wrapper">

    <!-- SIDEBAR -->
    <div class="admin-sidebar">
        <h2>FitExtreme Admin</h2>

        <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/admin/utenti">Gestione utenti</a>
        <a href="${pageContext.request.contextPath}/admin/ordini">Gestione ordini</a>
        <a href="${pageContext.request.contextPath}/admin/prodotti" style="background:#222;">Gestione prodotti</a>
    </div>

    <!-- CONTENUTO PRINCIPALE -->
    <div class="admin-content">
    
    
    
      <!-- MESSAGGI -->
    <%
        String msgOk = (String) session.getAttribute("messaggioSuccesso");
        String msgErr = (String) session.getAttribute("messaggioErrore");

        if (msgOk != null) {
    %>
        <div class="alert success"><%= msgOk %></div>
    <%
            session.removeAttribute("messaggioSuccesso");
        }

        if (msgErr != null) {
    %>
        <div class="alert error"><%= msgErr %></div>
    <%
            session.removeAttribute("messaggioErrore");
        }
    %>

        <p class="admin-subtitle">Catalogo</p>
        <h1 class="admin-title">Gestione Prodotti</h1>

        <!-- Pulsante aggiungi -->
        <a href="insProdotto" class="btn btn-primary" style="margin-bottom:20px;">+ Aggiungi nuovo prodotto</a>

        <%
            Collection<Articolo> lista = (Collection<Articolo>) request.getAttribute("listaArticoli");

            if (lista == null || lista.isEmpty()) {
        %>

            <p class="admin-empty">Nessun prodotto presente nel catalogo.</p>

        <%
            } else {
        %>

        <table class="admin-table">
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Prezzo</th>
                <th>Quantità</th>
                <th style="width: 220px;">Azioni</th>
            </tr>

            <%
                for (Articolo a : lista) {
            %>
            <tr>
                <td><%= a.getIdArticolo() %></td>
                <td><%= a.getNomeArticolo() %></td>
                <td>€ <%= a.getPrezzoListino() %></td>
                <td><%= a.getQtaDisponibile() %></td>

<td class="azioni-cell">

    <!-- Modifica -->
    <a class="btn btn-secondary"
       href="modProdotto?id=<%= a.getIdArticolo() %>">
        Modifica
    </a>

    <!-- Elimina (stesso stile di prima, ma bello come Modifica) -->
    <a class="btn btn-primary"
       href="#"
       onclick="return apriPopupElimina('<%= a.getNomeArticolo() %>', <%= a.getIdArticolo() %>);">
        Elimina
    </a>

</td>


            </tr>
            <% } %>

        </table>

        <% } %>

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

<script>
    let idDaEliminare = null;

    function apriPopupElimina(nomeProdotto, idArticolo) {
        idDaEliminare = idArticolo;

        document.getElementById("popup-elimina-message").innerText =
            "Sei sicuro di voler eliminare il prodotto \"" + nomeProdotto + "\"?";

        document.getElementById("popup-elimina").style.display = "flex";
        return false;
    }

    document.getElementById("elimina-confirm").onclick = function () {

        if (idDaEliminare !== null) {

            // Creo un form invisibile per inviare la richiesta POST
            const form = document.createElement("form");
            form.method = "post";
            form.action = "${pageContext.request.contextPath}/admin/elimina-prodotto";

            const input = document.createElement("input");
            input.type = "hidden";
            input.name = "id";
            input.value = idDaEliminare;

            form.appendChild(input);
            document.body.appendChild(form);

            form.submit();
        }
    };

    document.getElementById("elimina-cancel").onclick = function () {
        document.getElementById("popup-elimina").style.display = "none";
    };
</script>

</body>
</html>
