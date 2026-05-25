document.addEventListener("DOMContentLoaded", () => {

    const ctx = document.body.getAttribute("data-context");

    const categoria = document.getElementById("categoriaFiltro");
    const order = document.getElementById("order");
    const min = document.getElementById("min");
    const max = document.getElementById("max");
    const apply = document.getElementById("applyFilters");
    const container = document.getElementById("products-container");
    const activeFilters = document.getElementById("active-filters");

    function updateBadges() {
        activeFilters.innerHTML = "";

        if (categoria.value)
            addBadge("Categoria: " + categoria.options[categoria.selectedIndex].text, 
                     () => categoria.value = "");

        if (order.value)
            addBadge("Ordine: " + order.options[order.selectedIndex].text, 
                     () => order.value = "");

        if (min.value)
            addBadge("Min: €" + min.value, () => min.value = "");

        if (max.value)
            addBadge("Max: €" + max.value, () => max.value = "");
    }

    function addBadge(text, onRemove) {
        const badge = document.createElement("div");
        badge.className = "fx-badge";
        badge.innerHTML = `${text} <span>&times;</span>`;
        badge.querySelector("span").onclick = () => {
            onRemove();
            apply.click();
        };
        activeFilters.appendChild(badge);
    }

    // Riaggancia i listener dei bottoni carrello
    function bindCartButtons() {
        document.querySelectorAll(".add-to-cart").forEach(btn => {
            btn.addEventListener("click", function() {
                // Triggera lo stesso evento che usa cart.js
                const id = this.getAttribute("data-id");
                this.dispatchEvent(new CustomEvent("addToCart", { 
                    bubbles: true, 
                    detail: { id } 
                }));
            });
        });
    }

    function loadProducts() {
        updateBadges();

        const params = new URLSearchParams({
            id: categoria.value,
            order: order.value,
            min: min.value,
            max: max.value
        });

        fetch(ctx + "/prodottiAjax?" + params.toString())
            .then(res => res.json())
            .then(data => {
                container.innerHTML = "";

                if (data.length === 0) {
                    container.innerHTML = "<p class='text-secondary'>Nessun prodotto trovato.</p>";
                    return;
                }

                data.forEach(p => {
                    const img = (p.immagine && p.immagine !== "") 
                                ? p.immagine : "default.jpg";
                    const esaurito = p.qtaDisponibile === 0;

                    container.innerHTML += `
                        <div class="card-wrapper fade-in">
                            <div class="card">

                                ${esaurito 
                                    ? '<span class="soldout-badge">Esaurito</span>' 
                                    : ''}

                                <a href="${ctx}/prodotto?id=${p.idArticolo}" 
                                   class="card-img-link">
                                    <div class="card-img">
                                        <img src="${ctx}/utilities/immagini/${img}" 
                                             alt="${p.nomeArticolo}">
                                    </div>
                                </a>

                                <div class="card-body">
                                    <a href="${ctx}/prodotto?id=${p.idArticolo}" 
                                       class="card-title-link">
                                        <div class="title">${p.nomeArticolo}</div>
                                    </a>
                                    <div class="prezzo">€ ${p.prezzoListino}</div>

                                    ${!esaurito ? `
                                        <button class="card-btn add-to-cart" 
                                                data-id="${p.idArticolo}">
                                            Aggiungi al carrello
                                        </button>` : ''}
                                </div>

                            </div>
                        </div>
                    `;
                });

                // Riaggancia i listener dopo aver aggiornato il DOM
                bindCartButtons();
            })
            .catch(err => {
                console.error("Errore nel caricamento prodotti:", err);
                container.innerHTML = "<p class='text-secondary'>Errore nel caricamento.</p>";
            });
    }

    apply.addEventListener("click", loadProducts);
});