<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Dashboard Admin - FitExtreme</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/utilities/css/admin.css">
    
 <link rel="stylesheet" href="${pageContext.request.contextPath}/utilities/css/base.css">
</head>
<body>

<div class="admin-container">

    <h1>Benvenuto nell'Area Admin</h1>
    <p>Gestisci utenti, ordini e prodotti da un'unica interfaccia.</p>

    <div class="cards-container">

        <div class="admin-card">
            <h2>${numeroUtenti}</h2>
            <p>Utenti Registrati</p>
        </div>

        <div class="admin-card">
            <h2>${numeroOrdini}</h2>
            <p>Ordini Totali</p>
        </div>

        <div class="admin-card">
            <h2>${numeroArticoli}</h2>
            <p>Prodotti a Catalogo</p>
        </div>

    </div>

<div class="admin-links">

    <a href="${pageContext.request.contextPath}/admin/utenti" class="admin-btn">
        <i class="fa-solid fa-users"></i>
        Gestione Utenti
    </a>

    <a href="${pageContext.request.contextPath}/admin/ordini" class="admin-btn">
        <i class="fa-solid fa-receipt"></i>
        Gestione Ordini
    </a>

    <a href="${pageContext.request.contextPath}/admin/prodotti" class="admin-btn">
        <i class="fa-solid fa-box"></i>
        Gestione Prodotti
    </a>

</div>



</div>

</body>
</html>