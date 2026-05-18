document.addEventListener("DOMContentLoaded", () => {

    const searchInput    = document.getElementById("searchInput");
    const searchBtn      = document.getElementById("searchBtn");
    const suggestionsBox = document.getElementById("suggestionsBox");

    if (!searchInput || !searchBtn || !suggestionsBox) return;

    function eseguiRicerca() {
        const query = searchInput.value.trim();
        if (query.length === 0) return;
        suggestionsBox.style.display = "none";
        window.location.href = contextPath + "/catalogo?q=" + encodeURIComponent(query);
    }

    searchBtn.addEventListener("click", eseguiRicerca);

    searchInput.addEventListener("keydown", function(e) {
        if (e.key === "Enter") eseguiRicerca();
        if (e.key === "Escape") {
            suggestionsBox.style.display = "none";
            searchInput.blur();
        }
    });

    let debounceTimer = null;

    searchInput.addEventListener("input", function() {

        clearTimeout(debounceTimer);

        const keyword = searchInput.value.trim();

        if (keyword.length < 2) {
            suggestionsBox.style.display = "none";
            suggestionsBox.innerHTML = "";
            return;
        }

        debounceTimer = setTimeout(function() {
            fetchSuggestions(keyword);
        }, 250);
    });

    function fetchSuggestions(keyword) {

        fetch(contextPath + "/searchProdotti?keyword=" + encodeURIComponent(keyword))
            .then(function(res) {
                if (!res.ok) throw new Error("Risposta non OK: " + res.status);
                return res.json();
            })
            .then(function(data) {
                renderSuggestions(data);
            })
            .catch(function(err) {
                console.warn("Errore fetch suggerimenti:", err);
                suggestionsBox.style.display = "none";
            });
    }

    function renderSuggestions(data) {

        suggestionsBox.innerHTML = "";

        if (!data || data.length === 0) {
            suggestionsBox.innerHTML =
                "<div class='suggestion-item empty'>Nessun risultato trovato</div>";
            suggestionsBox.style.display = "block";
            return;
        }

        const fragment = document.createDocumentFragment();

        data.forEach(function(p) {

            const a = document.createElement("a");
            a.className = "suggestion-item";
            a.href = contextPath + "/prodotto?id=" + p.id;

            const nome = escapeHtml(p.nome != null ? p.nome : "");

            let prezzo = "";
            if (p.prezzo != null && !isNaN(p.prezzo)) {
                prezzo = parseFloat(p.prezzo).toFixed(2);
            }

            a.innerHTML =
                "<span class='name'>" + nome + "</span>" +
                "<span class='price'>€ " + prezzo + "</span>";

            // FIX DEFINITIVA
            a.addEventListener("mousedown", function(e) {
                e.stopPropagation();
            });

            a.addEventListener("click", function(e) {
                e.stopPropagation();
                suggestionsBox.style.display = "none";
            });

            fragment.appendChild(a);
        });

        suggestionsBox.appendChild(fragment);
        suggestionsBox.style.display = "block";
    }

    document.addEventListener("click", function(e) {
        if (
            !searchInput.contains(e.target) &&
            !suggestionsBox.contains(e.target) &&
            !searchBtn.contains(e.target)
        ) {
            suggestionsBox.style.display = "none";
        }
    });

    function escapeHtml(str) {
        const div = document.createElement("div");
        div.appendChild(document.createTextNode(str));
        return div.innerHTML;
    }

});
