<%@ page contentType="text/html;charset=UTF-8" %>


<%@ page import="model.beans.Utente"%>

<%
    Utente u = (Utente) session.getAttribute("utente");
Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
    boolean adminMode = (isAdmin != null && isAdmin);

    boolean cookieAccepted = false;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie c : cookies) {
            if ("cookieConsent".equals(c.getName())) {
                cookieAccepted = true;
                break;
            }
        }
    }
%>

<!-- HEADER -->
<header class="main-header <%= adminMode ? "admin-header" : "" %>">

    <% if (adminMode) { %>
        <div class="admin-banner">
            Modalità Amministratore Attiva
        </div>
    <% } %>

    <!-- TOP BAR -->
    <div class="top-bar">

        <!-- LOGO -->
        <div class="logo">
            <a href="<%= request.getContextPath() %>/home">
                <img src="<%= request.getContextPath() %>/utilities/immagini/logo.svg" alt="FitExtreme Logo">
            </a>
        </div>

        <% if (!adminMode) { %>
        <!-- SEARCH BAR (solo utenti normali) -->
        <div class="search-container">
            <div class="search-bar">
                <input type="text" id="searchInput" placeholder="Cerca prodotti..."
                       value="<%= request.getParameter("q") != null ? request.getParameter("q") : "" %>">
                <button id="searchBtn" aria-label="Cerca"></button>
            </div>
            <div id="suggestionsBox" class="suggestions-box"></div>
        </div>
        <% } %>

        <!-- ICONS -->
        <div class="header-icons">

            <% if (!adminMode) { %>
            <!-- CARRELLO (solo utenti normali) -->
            <a href="<%= request.getContextPath() %>/carrello" class="icon-btn cart-btn">
                <img src="<%= request.getContextPath() %>/utilities/immagini/cart.png" class="icon-img" alt="Carrello">
                <span id="cart-count" class="cart-badge">
                    <%= session.getAttribute("cartCount") != null ? session.getAttribute("cartCount") : 0 %>
                </span>
            </a>
            <% } %>

            <!-- USER MENU -->
            <div class="user-menu">
                <button class="icon-btn user-btn" aria-haspopup="true" aria-expanded="false">
                    <img src="<%= request.getContextPath() %>/utilities/immagini/user.png" class="icon-img" alt="Utente">
                </button>

                <div class="user-dropdown">

                    <% if (u != null) { %>

                    <p class="dropdown-user">
                        👋 Ciao, <strong><%= u.getNome() %></strong>
                    </p>

                    <% if (adminMode) { %>

                        <!-- ⭐ ADMIN: mostra solo Dashboard -->
                        <a href="<%= request.getContextPath() %>/admin/dashboard">
                            <img src="<%= request.getContextPath() %>/utilities/immagini/admin.png" class="dropdown-icon" alt="">
                            Dashboard
                        </a>

                    <% } else { %>

                        <!-- ⭐ UTENTE NORMALE: Profilo + Ordini -->
                        <a href="<%= request.getContextPath() %>/profilo">
                            <img src="<%= request.getContextPath() %>/utilities/immagini/user2.png" class="dropdown-icon" alt="">
                            Profilo
                        </a>

                        <a href="<%= request.getContextPath() %>/ordini">
                            <img src="<%= request.getContextPath() %>/utilities/immagini/ordini.png" class="dropdown-icon" alt="">
                            I miei ordini
                        </a>

                    <% } %>

                    <!-- Logout per tutti -->
                    <a href="<%= request.getContextPath() %>/logout">
                        <img src="<%= request.getContextPath() %>/utilities/immagini/logout.png" class="dropdown-icon" alt="">
                        Logout
                    </a>

                    <% } else { %>

                    <!-- Non loggato -->
                    <a href="<%= request.getContextPath() %>/login">
                        <img src="<%= request.getContextPath() %>/utilities/immagini/login.png" class="dropdown-icon" alt="">
                        Login
                    </a>

                    <a href="<%= request.getContextPath() %>/register">
                        <img src="<%= request.getContextPath() %>/utilities/immagini/register.png" class="dropdown-icon" alt="">
                        Registrati
                    </a>

                    <% } %>

                </div>
            </div>

        </div>
    </div>

    <!-- NAVBAR -->
    <% if (!adminMode) { %>
    <nav class="navbar">
        <a href="<%= request.getContextPath() %>/home">Home</a>
        <a href="<%= request.getContextPath() %>/catalogo">Catalogo</a>
        <a href="<%= request.getContextPath() %>/catalogo?id=1">Abbigliamento</a>
        <a href="<%= request.getContextPath() %>/catalogo?id=2">Accessori</a>
        <a href="<%= request.getContextPath() %>/catalogo?id=3">Attrezzatura</a>
        <a href="<%= request.getContextPath() %>/contatti">Contatti</a>
    </nav>
    <% } %>

</header>

<!-- COOKIE BANNER -->
<% if (!cookieAccepted) { %>
<div id="cookie-banner" role="dialog" aria-label="Consenso cookie">
    <div class="cookie-content">
        <p>
            Questo sito utilizza cookie tecnici e, previo consenso, cookie di profilazione.
            Per maggiori informazioni consulta la nostra 
            <a href="<%= request.getContextPath() %>/cookie-policy.jsp">Cookie Policy</a> 
            e la 
            <a href="<%= request.getContextPath() %>/privacy-policy.jsp">Privacy Policy</a>.
        </p>

        <div class="cookie-buttons">
            <button id="acceptCookies" class="cookie-accept">Accetta</button>
            <button id="rejectCookies" class="cookie-reject">Rifiuta</button>
        </div>
    </div>
</div>
<% } %>

<script>
    const contextPath = "<%= request.getContextPath() %>";
</script>

<script src="<%= request.getContextPath() %>/utilities/js/header.js"></script>
<script src="<%= request.getContextPath() %>/utilities/js/search.js"></script>
