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
            addBadge("Categoria: " + categoria.options[categoria.selectedIndex].text, () => categoria.value = "");

        if (order.value)
            addBadge("Ordine: " + order.options[order.selectedIndex].text, () => order.value = "");

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

    function loadProducts() {
        updateBadges();

        const params = new URLSearchParams({
            id: categoria.value,   // 🔥 QUI IL FIX
            order: order.value,
            min: min.value,
            max: max.value
        });

        fetch("prodottiAjax?" + params.toString())
            .then(res => res.json())
            .then(data => {
                container.innerHTML = "";

                if (data.length === 0) {
                    container.innerHTML = "<p class='text-secondary'>Nessun prodotto trovato.</p>";
                    return;
                }

                data.forEach(p => {
                    container.innerHTML += `
                        <div class="card-wrapper fade-in">
                            <a href="Prodotto?id=${p.idArticolo}" class="card-link">
                                <div class="card">
                                    <img src="${ctx}/utilities/immagini/${p.immagine}" alt="${p.nomeArticolo}">
                                    <h3>${p.nomeArticolo}</h3>
                                    <p class="prezzo">€ ${p.prezzoListino}</p>
                                </div>
                            </a>
                            <button class="btn-add add-to-cart" data-id="${p.idArticolo}">
                                Aggiungi al carrello
                            </button>
                        </div>
                    `;
                });
            });
    }

    apply.addEventListener("click", loadProducts);
});
