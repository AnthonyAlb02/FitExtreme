//   VALIDAZIONE PAGAMENTO

document.addEventListener("DOMContentLoaded", () => {

    const form = document.getElementById("paymentForm");
    const scadenza = form.querySelector("input[name='scadenza']");
    const numeroCarta = form.querySelector("input[name='numeroCarta']");
    const cvv = form.querySelector("input[name='cvv']");

    // AUTO-FORMATTAZIONE SCADENZA
    scadenza.addEventListener("input", function () {
        let v = this.value.replace(/[^0-9]/g, "");
        if (v.length >= 3) {
            v = v.substring(0, 2) + "/" + v.substring(2, 4);
        }
        this.value = v;
        this.setCustomValidity("");
    });

    scadenza.addEventListener("blur", function () {
        validateScadenza(this);
    });

    //  CONTROLLO NUMERO CARTA (16 cifre) 
    numeroCarta.addEventListener("input", function () {
        this.value = this.value.replace(/\D/g, "").substring(0, 16);
        this.setCustomValidity("");
    });

    numeroCarta.addEventListener("blur", function () {
        validateNumeroCarta(this);
    });

    //  CONTROLLO CVV (3 cifre) 
    cvv.addEventListener("input", function () {
        this.value = this.value.replace(/\D/g, "").substring(0, 3);
        this.setCustomValidity("");
    });

    cvv.addEventListener("blur", function () {
        validateCvv(this);
    });

    //  FUNZIONI DI VALIDAZIONE 

    function validateScadenza(field) {
        const value = field.value.trim();

        if (!/^(0[1-9]|1[0-2])\/\d{2}$/.test(value)) {
            field.setCustomValidity("Formato non valido. Usa MM/AA");
            return false;
        }

        const [mm, yy] = value.split("/").map(Number);
        const expiry = new Date(2000 + yy, mm); 
        const now = new Date();

        if (expiry <= now) {
            field.setCustomValidity("Carta scaduta");
            return false;
        }

        field.setCustomValidity("");
        return true;
    }

    function validateNumeroCarta(field) {
        const value = field.value.trim();

        if (!/^\d{16}$/.test(value)) {
            field.setCustomValidity("Il numero carta deve essere di 16 cifre");
            return false;
        }

        field.setCustomValidity("");
        return true;
    }

    function validateCvv(field) {
        const value = field.value.trim();

        if (!/^\d{3}$/.test(value)) {
            field.setCustomValidity("Il CVV deve essere di 3 cifre");
            return false;
        }

        field.setCustomValidity("");
        return true;
    }

    //  VALIDAZIONE COMPLETA AL SUBMIT 

    function validateAll() {
        const okScadenza     = validateScadenza(scadenza);
        const okNumeroCarta  = validateNumeroCarta(numeroCarta);
        const okCvv          = validateCvv(cvv);
        return okScadenza && okNumeroCarta && okCvv;
    }

    // BLOCCA SUBMIT SE INVALIDO
    form.addEventListener("submit", function (e) {
        const valid = validateAll();

        if (!valid || !this.checkValidity()) {
            e.preventDefault();
            const first = this.querySelector(":invalid");
            if (first) {
                first.focus();
                if (typeof first.reportValidity === "function") {
                    first.reportValidity();
                }
            }
            return;
        }

        //POPUP DI CONFERMA 
        e.preventDefault();
        const popup = document.getElementById("confirmPopup");
        popup.classList.remove("hidden");
    });

    //CONFERMA / ANNULLA POPUP
    document.getElementById("confirmYes").addEventListener("click", () => {
        document.getElementById("confirmPopup").classList.add("hidden");
        form.submit();
    });

    document.getElementById("confirmNo").addEventListener("click", () => {
        document.getElementById("confirmPopup").classList.add("hidden");
    });

});