document.addEventListener("DOMContentLoaded", () => {

    const context = document.body.dataset.context;
    const buttons = document.querySelectorAll(".add-to-cart");

    buttons.forEach(btn => {
        btn.addEventListener("click", () => {

            const id = btn.dataset.id;
            let taglia = null;

         

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

                // ❗ SE STOCK ESAURITO
                if (data.error === "stock_esaurito") {
                    btn.classList.add("shake");
                    setTimeout(() => btn.classList.remove("shake"), 500);
                    alert("Stock esaurito! Non puoi aggiungere altre unità.");
                    return;
                }

                //  Aggiorna badge carrello
                const badge = document.getElementById("cart-count");
                badge.textContent = data.cartCount;

                badge.classList.add("cart-pulse");
                setTimeout(() => badge.classList.remove("cart-pulse"), 400);

                //  Effetto bottone
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
