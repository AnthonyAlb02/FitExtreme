// ===============================
// INIT
// ===============================
document.addEventListener("DOMContentLoaded", function() {

    initObservers();
    initSmoothScroll();
    initHideHeader();

});


// ===============================
// HIDE HEADER ON SCROLL
// ===============================
function initHideHeader() {

    var header = document.querySelector(".main-header");
    if (!header) return;

    var lastScroll = 0;
    var threshold = 60; // px da scorrere prima di nascondere

    window.addEventListener("scroll", function() {

        var currentScroll = window.pageYOffset;

        // Scrollando giù: nascondi
        if (currentScroll > lastScroll && currentScroll > threshold) {
            header.classList.add("header-hidden");
        }
        // Scrollando su: mostra
        else if (currentScroll < lastScroll) {
            header.classList.remove("header-hidden");
        }

        lastScroll = currentScroll;
    }, { passive: true });
}


// ===============================
// INTERSECTION OBSERVERS
// ===============================
function initObservers() {

    var elements = {
        cards: document.querySelectorAll(".card"),
        sections: document.querySelectorAll(".hero, .categories, .section, .trust")
    };

    if (!("IntersectionObserver" in window)) {
        elements.cards.forEach(function(el) { el.classList.add("show"); });
        elements.sections.forEach(function(el) { el.classList.add("visible"); });
        return;
    }

    var cardObserver = new IntersectionObserver(function(entries, observer) {
        entries.forEach(function(entry) {
            if (entry.isIntersecting) {
                entry.target.classList.add("show");
                observer.unobserve(entry.target);
            }
        });
    }, {
        threshold: 0.2,
        rootMargin: "0px 0px -50px 0px"
    });

    elements.cards.forEach(function(card) {
        if (card) cardObserver.observe(card);
    });

    var sectionObserver = new IntersectionObserver(function(entries, observer) {
        entries.forEach(function(entry) {
            if (entry.isIntersecting) {
                entry.target.classList.add("visible");
                observer.unobserve(entry.target);
            }
        });
    }, {
        threshold: 0.1,
        rootMargin: "0px 0px -80px 0px"
    });

    elements.sections.forEach(function(section) {
        if (section) sectionObserver.observe(section);
    });
}


// ===============================
// CAROUSEL CATEGORIE
// ===============================
document.addEventListener("DOMContentLoaded", function() {

    var carousel = document.getElementById("cat-carousel");
    var btnLeft  = document.getElementById("cat-left");
    var btnRight = document.getElementById("cat-right");

    if (!carousel || !btnLeft || !btnRight) return;

    var scrollAmount = 300;

    btnLeft.addEventListener("click", function() {
        carousel.scrollBy({ left: -scrollAmount, behavior: "smooth" });
    });

    btnRight.addEventListener("click", function() {
        carousel.scrollBy({ left: scrollAmount, behavior: "smooth" });
    });
});


// ===============================
// SMOOTH SCROLL
// ===============================
function initSmoothScroll() {

    var anchors = document.querySelectorAll('a[href^="#"]');

    anchors.forEach(function(anchor) {
        anchor.addEventListener("click", function(e) {

            var href = this.getAttribute("href");
            if (!href || href === "#") return;

            var target = document.querySelector(href);
            if (target) {
                e.preventDefault();
                target.scrollIntoView({ behavior: "smooth", block: "start" });
            }
        });
    });
}


// ===============================
// RIDUZIONE MOTION
// ===============================
if (window.matchMedia("(prefers-reduced-motion: reduce)").matches) {
    document.documentElement.classList.add("no-animations");
}