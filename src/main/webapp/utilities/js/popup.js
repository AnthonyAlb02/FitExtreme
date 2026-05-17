document.addEventListener("DOMContentLoaded", () => {

    /* ============================
       ZOOM DINAMICO (solo prodotto)
    ============================ */
    const container = document.querySelector(".zoom-container");
    const img = document.querySelector(".zoom-img");

    if (container && img) {
        let zoomActive = false;

        container.addEventListener("mouseenter", () => {
            zoomActive = true;
            container.classList.add("active");
        });

        container.addEventListener("mouseleave", () => {
            zoomActive = false;
            container.classList.remove("active");
            img.style.transformOrigin = "center center";
        });

        container.addEventListener("mousemove", (e) => {
            if (!zoomActive) return;

            const rect = container.getBoundingClientRect();
            const x = ((e.clientX - rect.left) / rect.width) * 100;
            const y = ((e.clientY - rect.top) / rect.height) * 100;

            img.style.transformOrigin = `${x}% ${y}%`;
        });
    }

    /* ============================
       POPUP "AGGIUNTO" (catalogo + prodotto)
    ============================ */
    const buttons = document.querySelectorAll(".add-to-cart");
    const context = document.body.dataset.context;
    const isProductPage = document.body.classList.contains("product-page");

    buttons.forEach(btn => {
        btn.addEventListener("click", () => {

            // Popup elegante
            const popup = document.createElement("div");
            popup.className = "added-popup";
            popup.textContent = "Prodotto aggiunto al carrello ✓";
            document.body.appendChild(popup);

            // Rimuovi popup dopo 2 secondi
            setTimeout(() => popup.remove(), 2000);

            // Redirect SOLO nella pagina prodotto
            if (isProductPage) {
                setTimeout(() => {
                    window.location.href = context + "/catalogo";
                }, 2000);
            }
        });
    });

});
