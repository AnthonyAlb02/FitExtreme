
//   VALIDAZIONE PAGAMENTO

document.addEventListener("DOMContentLoaded", () => {

    const form = document.getElementById("paymentForm");
    const scadenza = form.querySelector("input[name='scadenza']");
    const numeroCarta = form.querySelector("input[name='numeroCarta']");
    const cvv = form.querySelector("input[name='cvv']");

    //   AUTO-FORMATTAZIONE SCADENZA
    scadenza.addEventListener("input", function () {
        let v = this.value.replace(/[^0-9]/g, "");

        if (v.length >= 3) {
            v = v.substring(0, 2) + "/" + v.substring(2, 4);
        }

        this.value = v;
        this.setCustomValidity("");
    });

    //   CONTROLLO SCADENZA NON PASSATA
    scadenza.addEventListener("blur", function () {
        const value = this.value;

        if (!/^(0[1-9]|1[0-2])\/\d{2}$/.test(value)) {
            this.setCustomValidity("Formato non valido. Usa MM/AA");
            return;
        }

        const [mm, yy] = value.split("/").map(Number);

        const expiry = new Date(2000 + yy, mm); // scade alla fine del mese precedente
        const now = new Date();

        if (expiry <= now) {
            this.setCustomValidity("Carta scaduta");
        } else {
            this.setCustomValidity("");
        }
    });

    //   CONTROLLO NUMERO CARTA (16 cifre)
    numeroCarta.addEventListener("input", function () {
        this.value = this.value.replace(/\D/g, "").substring(0, 16);
        this.setCustomValidity("");
    });

    //   CONTROLLO CVV (3 cifre)
    cvv.addEventListener("input", function () {
        this.value = this.value.replace(/\D/g, "").substring(0, 3);
        this.setCustomValidity("");
    });

    //   BLOCCA SUBMIT SE INVALIDO
    form.addEventListener("submit", function (e) {
        if (!this.checkValidity()) {
            e.preventDefault();
            const first = this.querySelector(":invalid");
            if (first) {
                first.focus();
                if (typeof first.reportValidity === "function") {
                    first.reportValidity();
                }
            }
        }
    });

});

//   POPUP DI CONFERMA ORDINE

const popup = document.getElementById("confirmPopup");
const btnYes = document.getElementById("confirmYes");
const btnNo = document.getElementById("confirmNo");

form.addEventListener("submit", function (e) {
    if (!this.checkValidity()) return; // lascia la validazione normale

    e.preventDefault(); // blocca invio
    popup.classList.remove("hidden"); // mostra popup
});

// Conferma acquisto
btnYes.addEventListener("click", () => {
    popup.classList.add("hidden");
    form.submit(); // invia davvero
});

// Annulla
btnNo.addEventListener("click", () => {
    popup.classList.add("hidden");
});

