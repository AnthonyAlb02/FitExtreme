document.addEventListener("DOMContentLoaded", () => {

    /* ============================
       ZOOM DINAMICO STILE NIKE
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
       POPUP "AGGIUNTO" + REDIRECT
    ============================ */
    const btn = document.querySelector(".add-to-cart");
    const context = document.body.dataset.context;

    if (btn) {
        btn.addEventListener("click", () => {

            // Popup elegante
            const popup = document.createElement("div");
            popup.className = "added-popup";
            popup.textContent = "Prodotto aggiunto al carrello ✓";
            document.body.appendChild(popup);

            // Rimuovi popup dopo animazione
            setTimeout(() => popup.remove(), 2000);

            // Redirect dopo 2 secondi
            setTimeout(() => {
                window.location.href = context + "/catalogo";
            }, 2000);
        });
    }

});
