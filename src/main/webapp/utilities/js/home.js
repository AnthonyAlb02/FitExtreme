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

// ===== CAROUSEL CATEGORIE (CIRCOLARE) =====
document.addEventListener("DOMContentLoaded", function() {
    var carousel = document.getElementById("cat-carousel");
    var btnLeft = document.getElementById("cat-left");
    var btnRight = document.getElementById("cat-right");
    var cards = Array.from(carousel.children);
    var totalCards = cards.length;

    function getCardWidth() {
        return carousel.children[0].offsetWidth + 20;
    }

    // Clona solo una volta: 1 gruppo prima e 1 dopo
    for (var i = 0; i < totalCards; i++) {
        carousel.appendChild(cards[i].cloneNode(true));
    }
    for (var j = totalCards - 1; j >= 0; j--) {
        carousel.insertBefore(cards[j].cloneNode(true), carousel.firstChild);
    }

    var cardWidth = getCardWidth();
    var groupWidth = cardWidth * totalCards;

    // Posiziona sul gruppo centrale (quello vero)
    carousel.style.scrollBehavior = "auto";
    carousel.scrollLeft = groupWidth;

    var scrolling = false;

    function reposition() {
        var left = carousel.scrollLeft;
        // Se supera il gruppo finale, torna al gruppo centrale
        if (left >= groupWidth * 2) {
            carousel.style.scrollBehavior = "auto";
            carousel.scrollLeft -= groupWidth;
        }
        // Se torna prima del gruppo iniziale, salta al gruppo centrale
        if (left <= 0) {
            carousel.style.scrollBehavior = "auto";
            carousel.scrollLeft += groupWidth;
        }
        setTimeout(function() {
            carousel.style.scrollBehavior = "smooth";
            scrolling = false;
        }, 20);
    }

    carousel.addEventListener("scroll", function() {
        if (scrolling) return;
        scrolling = true;
        setTimeout(reposition, 200);
    });

    btnRight.addEventListener("click", function() {
        carousel.style.scrollBehavior = "smooth";
        carousel.scrollLeft += getCardWidth();
    });

    btnLeft.addEventListener("click", function() {
        carousel.style.scrollBehavior = "smooth";
        carousel.scrollLeft -= getCardWidth();
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



function prefersReducedMotion() {
    return window.matchMedia("(prefers-reduced-motion: reduce)").matches;
}


if (prefersReducedMotion()) {
    document.documentElement.classList.add("no-animations");
}