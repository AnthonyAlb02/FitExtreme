document.addEventListener("DOMContentLoaded", () => {

    const context = document.body.dataset.context;
    const buttons = document.querySelectorAll(".add-to-cart");

    buttons.forEach(btn => {
        btn.addEventListener("click", () => {

            const id = btn.dataset.id;
            let taglia = null;

            // Se il prodotto richiede taglia
            if (btn.dataset.size === "true") {
                const select = document.getElementById("taglia");

                if (!select || !select.value) {
                    alert("Seleziona una taglia prima di aggiungere al carrello.");
                    return;
                }

                taglia = select.value;
            }

            // Prepara il body della richiesta
            let body = "id=" + id;
            if (taglia !== null) {
                body += "&taglia=" + taglia;
            }

            fetch(context + "/addToCart", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: body
            })
            .then(res => res.json())
            .then(data => {

                // Aggiorna badge carrello
                const badge = document.getElementById("cart-count");
                badge.textContent = data.cartCount;

                // Effetto badge
                badge.classList.add("cart-pulse");
                setTimeout(() => badge.classList.remove("cart-pulse"), 400);

                // Effetto bottone
                btn.classList.add("added");
                const originalText = btn.textContent;
                btn.textContent = "Aggiunto ✓";

                setTimeout(() => {
                    btn.classList.remove("added");
                    btn.textContent = originalText;
                }, 900);
            });
        });
    });

});
