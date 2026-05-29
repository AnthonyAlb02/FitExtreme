<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.beans.Articolo" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Gestione Prodotti - Admin</title>

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
        <a href="${pageContext.request.contextPath}/admin/prodotti" style="background:#222;">Gestione prodotti</a>
    </div>

    <!-- CONTENUTO PRINCIPALE -->
    <div class="admin-content">

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
                <th>Azioni</th>
            </tr>

            <%
                for (Articolo a : lista) {
            %>
            <tr>
                <td><%= a.getIdArticolo() %></td>
                <td><%= a.getNomeArticolo() %></td>
                <td>€ <%= a.getPrezzoListino() %></td>
                <td><%= a.getQtaDisponibile() %></td>

                <td>
                    <a class="btn btn-secondary" href="modProdotto?id=<%= a.getIdArticolo() %>">Modifica</a>
                    <a class="btn btn-primary" href="CancellaProdotto?id=<%= a.getIdArticolo() %>"
                       onclick="return confirm('Sei sicuro di voler eliminare questo prodotto?');">
                       Elimina
                    </a>
                </td>
            </tr>
            <% } %>

        </table>

        <% } %>

    </div>

</div>

</body>
</html>
