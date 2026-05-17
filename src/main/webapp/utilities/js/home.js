// ===============================
// INIT
// ===============================
document.addEventListener("DOMContentLoaded", () => {

    initObservers();
    initSmoothScroll();

});


// ===============================
// INTERSECTION OBSERVERS
// ===============================
function initObservers() {

    const elements = {
        cards: document.querySelectorAll(".card"),
        sections: document.querySelectorAll(".hero, .categories, .section, .trust")
    };

    // Fallback per browser senza supporto
    if (!("IntersectionObserver" in window)) {
        elements.cards.forEach(el => el.classList.add("show"));
        elements.sections.forEach(el => el.classList.add("visible"));
        return;
    }

    // Observer per CARD
    const cardObserver = new IntersectionObserver((entries, observer) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add("show");
                observer.unobserve(entry.target); // stop osservazione
            }
        });
    }, {
        threshold: 0.2,
        rootMargin: "0px 0px -50px 0px"
    });

    elements.cards.forEach(card => {
        if (card) cardObserver.observe(card);
    });


    // Observer per SEZIONI
    const sectionObserver = new IntersectionObserver((entries, observer) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add("visible");
                observer.unobserve(entry.target);
            }
        });
    }, {
        threshold: 0.1,
        rootMargin: "0px 0px -80px 0px"
    });

    elements.sections.forEach(section => {
        if (section) sectionObserver.observe(section);
    });
}

// ===== CAROUSEL CATEGORIE =====
document.addEventListener("DOMContentLoaded", () => {
    const carousel = document.getElementById("cat-carousel");
    const btnLeft = document.getElementById("cat-left");
    const btnRight = document.getElementById("cat-right");

    const scrollAmount = 300; // distanza di scorrimento

    btnLeft.addEventListener("click", () => {
        carousel.scrollBy({ left: -scrollAmount, behavior: "smooth" });
    });

    btnRight.addEventListener("click", () => {
        carousel.scrollBy({ left: scrollAmount, behavior: "smooth" });
    });
});



// ===============================
// SMOOTH SCROLL SICURO
// ===============================
function initSmoothScroll() {

    const anchors = document.querySelectorAll('a[href^="#"]');

    anchors.forEach(anchor => {
        anchor.addEventListener("click", function (e) {

            const href = this.getAttribute("href");

            // Evita errori tipo href="#"
            if (!href || href === "#") return;

            const target = document.querySelector(href);

            if (target) {
                e.preventDefault();

                target.scrollIntoView({
                    behavior: "smooth",
                    block: "start"
                });
            }
        });
    });
}


// ===============================
// (OPZIONALE) RIDUZIONE MOTION
// ===============================
function prefersReducedMotion() {
    return window.matchMedia("(prefers-reduced-motion: reduce)").matches;
}

// Se vuoi disattivare animazioni per accessibilità
if (prefersReducedMotion()) {
    document.documentElement.classList.add("no-animations");
}