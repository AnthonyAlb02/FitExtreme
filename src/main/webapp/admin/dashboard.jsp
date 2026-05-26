<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Dashboard Admin - FitExtreme</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/utilities/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/utilities/css/admin.css">
</head>
<body>

<div class="admin-content">

    <p class="admin-subtitle">Dashboard Amministratore</p>
    <h1 class="admin-title">Benvenuto nell'area admin</h1>

    <!-- CARDS STATISTICHE -->
    <div class="admin-stats">

        <div class="admin-card">
            <p class="card-label">Utenti registrati</p>
            <p class="card-value">${numeroUtenti}</p>
        </div>

        <div class="admin-card">
            <p class="card-label">Ordini totali</p>
            <p class="card-value">${numeroOrdini}</p>
        </div>

        <div class="admin-card">
            <p class="card-label">Prodotti a catalogo</p>
            <p class="card-value">${numeroArticoli}</p>
        </div>

    </div>

    <!-- BOTTONI NAVIGAZIONE -->
    <div class="admin-links">

        <a href="${pageContext.request.contextPath}/admin/utenti" class="admin-btn">
            Gestione utenti
        </a>

        <a href="${pageContext.request.contextPath}/admin/ordini" class="admin-btn">
            Gestione ordini
        </a>

        <a href="${pageContext.request.contextPath}/admin/prodotti" class="admin-btn">
            Gestione prodotti
        </a>

    </div>

</div>

</body>
</html>