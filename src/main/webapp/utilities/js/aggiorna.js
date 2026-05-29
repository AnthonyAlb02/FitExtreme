document.addEventListener("DOMContentLoaded", () => {

    // PLUS
    document.querySelectorAll(".qty-plus").forEach(btn => {
        btn.addEventListener("click", () => {
            updateCart(btn.dataset.id, "plus");
        });
    });

    // MINUS
    document.querySelectorAll(".qty-minus").forEach(btn => {
        btn.addEventListener("click", () => {
            updateCart(btn.dataset.id, "minus");
        });
    });

    // REMOVE
    document.querySelectorAll(".remove-item").forEach(btn => {
        btn.addEventListener("click", () => {
            updateCart(btn.dataset.id, "remove");
        });
    });

});

function updateCart(id, action) {

    fetch("updateCart", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: "id=" + id + "&action=" + action
    })
    .then(res => res.json())
    .then(data => {

        // ❗ SE STOCK ESAURITO
        if (data.error === "stock_esaurito") {

            const row = document.getElementById("row-" + id);
            row.classList.add("shake");

            setTimeout(() => row.classList.remove("shake"), 500);

            return; // ❗ BLOCCA AGGIORNAMENTO
        }

        // ❗ SE L'ARTICOLO È STATO RIMOSSO
        if (data.removed === true || data.removed === "true") {
            const row = document.getElementById("row-" + id);
            if (row) row.remove();
        } else {
            // ✔ Aggiorna quantità
            document.getElementById("qty-" + id).textContent = data.qta;

            // ✔ Aggiorna subtotale
            document.getElementById("subtotal-" + id).textContent = data.subtotale + " €";
        }

        // ✔ Aggiorna totale
        document.querySelector(".cart-total-text").textContent = "Totale: " + data.totale + " €";

        // ✔ Aggiorna badge carrello
        document.getElementById("cart-count").textContent = data.cartCount;

        // ✔ Carrello vuoto
        if (data.cartCount === 0) {
            document.querySelector(".cart-container").innerHTML =
                "<p class='text-secondary'>Il carrello è vuoto.</p>";
        }
    });
}
