<%@ page  pageEncoding="UTF-8" %>


<footer class="footer" id="footer">

    <div class="footer-top">

        <div class="footer-feature">
            <h4>Reso facile</h4>
            <p>Fino a 30 giorni</p>
        </div>

        <div class="footer-feature">
            <h4>Supporto rapido</h4>
            <p>Assistenza 7 giorni su 7</p>
        </div>

        <div class="footer-feature">
            <h4>Pagamenti sicuri</h4>
            <p>Transazioni protette</p>
        </div>

        <div class="footer-feature">
            <h4>Spedizioni veloci</h4>
            <p>In tutta Italia</p>
        </div>

    </div>

    <div class="footer-grid">

        <!-- BRAND -->
        <div class="footer-col brand">
            <h3>FitExtreme</h3>
            <p>Sport, performance e materiali per ogni atleta.</p>
        </div>

        <!-- CATEGORIE -->
        <div class="footer-col">
            <h4>Categorie</h4>
            <ul>
                <li><a href="<%= request.getContextPath() %>/catalogo?id=1">Abbigliamento</a></li>
                <li><a href="<%= request.getContextPath() %>/catalogo?id=2">Accessori</a></li>
                <li><a href="<%= request.getContextPath() %>/catalogo?id=3">Attrezzatura</a></li>
                <li><a href="<%= request.getContextPath() %>/catalogo">Tutto il catalogo</a></li>
            </ul>
        </div>

        <!-- ABOUT US -->
        <div class="footer-col">
            <h4>About Us</h4>
            <ul>
                <li><a href="<%= request.getContextPath() %>/about">Chi siamo</a></li>
            </ul>
        </div>

        <!-- CONTATTI -->
        <div class="footer-col">
            <h4>Contattaci</h4>
            <ul class="contact-list">
                <li><a href="mailto:assistenza@fitextreme.it">assistenza@fitextreme.it</a></li>
                <li><a href="tel:+393393786329">+39 339 373 9632</a></li>
            </ul>
        </div>

    </div>

    <div class="footer-bottom">© 2026 FitExtreme — Tutti i diritti riservati</div>

</footer>
