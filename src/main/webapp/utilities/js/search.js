// ===============================
// HEADER: Nascondi/Mostra allo Scroll
// ===============================

let lastScroll = 0;
const header = document.querySelector('.main-header');

window.addEventListener('scroll', () => {
    const currentScroll = window.pageYOffset;

    if (currentScroll > lastScroll && currentScroll > 80) {
        // Scroll verso il basso → nascondi header
        header.style.transform = "translateY(-100%)";
    } else {
        // Scroll verso l'alto → mostra header
        header.style.transform = "translateY(0)";
    }

    lastScroll = currentScroll;
});


// ===============================
// SEARCH BAR: Ricerca Prodotti
// ===============================

document.addEventListener("DOMContentLoaded", () => {

    const searchInput = document.getElementById("searchInput");
    const searchBtn = document.getElementById("searchBtn");

    // Se per qualche motivo non esistono, esci
    if (!searchInput || !searchBtn) return;

    // Funzione di ricerca
    function eseguiRicerca() {
        const query = searchInput.value.trim();

        if (query.length === 0) return;

        window.location.href = contextPath + "/catalogo?q=" + encodeURIComponent(query);
    }

    // Clic sulla lente
    searchBtn.addEventListener("click", eseguiRicerca);

    // Invio da tastiera
    searchInput.addEventListener("keydown", (e) => {
        if (e.key === "Enter") {
            eseguiRicerca();
        }
    });

    // Reset automatico: se l’utente cancella tutto → torna al catalogo completo
    searchInput.addEventListener("input", () => {
        if (searchInput.value.trim() === "") {
            window.location.href = contextPath + "/catalogo";
        }
    });

});
