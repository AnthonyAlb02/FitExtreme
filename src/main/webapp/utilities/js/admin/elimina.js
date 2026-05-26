let formElimina = null;
let formPromuovi = null;

function apriPopupElimina(nomeCompleto, form) {
    formElimina = form;
    document.getElementById("popup-elimina-message").innerText =
        "Vuoi eliminare l'utente " + nomeCompleto + "?";
    document.getElementById("popup-elimina").style.display = "flex";
}

function apriPopupPromuovi(nomeCompleto, form) {
    formPromuovi = form;
    document.getElementById("popup-promuovi-message").innerText =
        "Vuoi promuovere " + nomeCompleto + " ad amministratore?";
    document.getElementById("popup-promuovi").style.display = "flex";
}

document.addEventListener("DOMContentLoaded", () => {

    document.getElementById("elimina-cancel").addEventListener("click", () => {
        document.getElementById("popup-elimina").style.display = "none";
        formElimina = null;
    });

    document.getElementById("elimina-confirm").addEventListener("click", () => {
        if (formElimina) formElimina.submit();
    });

    document.getElementById("promuovi-cancel").addEventListener("click", () => {
        document.getElementById("popup-promuovi").style.display = "none";
        formPromuovi = null;
    });

    document.getElementById("promuovi-confirm").addEventListener("click", () => {
        if (formPromuovi) formPromuovi.submit();
    });

});