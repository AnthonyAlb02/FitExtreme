document.addEventListener("DOMContentLoaded", () => {

    const form = document.getElementById("paymentForm");
    const scadenza = form.querySelector("input[name='scadenza']");
    const numeroCarta = form.querySelector("input[name='numeroCarta']");
    const cvv = form.querySelector("input[name='cvv']");

    function showError(field, msg) {
        let span = field.parentElement.querySelector(".field-error");
        if (!span) {
            span = document.createElement("span");
            span.className = "field-error";
            span.style.color = "red";
            span.style.fontSize = "0.85em";
            field.parentElement.appendChild(span);
        }
        span.textContent = msg;
        field.style.borderColor = "red";
    }

    function clearError(field) {
        const span = field.parentElement.querySelector(".field-error");
        if (span) span.textContent = "";
        field.style.borderColor = "";
    }

    function validateScadenza() {
        const value = scadenza.value.trim();
        if (!/^(0[1-9]|1[0-2])\/\d{2}$/.test(value)) {
            showError(scadenza, "Formato non valido. Usa MM/AA");
            return false;
        }
        const [mm, yy] = value.split("/").map(Number);
        const expiry = new Date(2000 + yy, mm);
        if (expiry <= new Date()) {
            showError(scadenza, "Carta scaduta");
            return false;
        }
        clearError(scadenza);
        return true;
    }

    function validateNumeroCarta() {
        const value = numeroCarta.value.trim();
        if (!/^\d{16}$/.test(value)) {
            showError(numeroCarta, "Il numero carta deve essere di 16 cifre");
            return false;
        }
        clearError(numeroCarta);
        return true;
    }

    function validateCvv() {
        const value = cvv.value.trim();
        if (!/^\d{3}$/.test(value)) {
            showError(cvv, "Il CVV deve essere di 3 cifre");
            return false;
        }
        clearError(cvv);
        return true;
    }

    function validateAll() {
        const okScadenza = validateScadenza();
        const okCarta    = validateNumeroCarta();
        const okCvv      = validateCvv();
        return okScadenza && okCarta && okCvv;
    }

    scadenza.addEventListener("input", function () {
        let v = this.value.replace(/[^0-9]/g, "");
        if (v.length >= 3) v = v.substring(0, 2) + "/" + v.substring(2, 4);
        this.value = v;
        clearError(this);
    });
    scadenza.addEventListener("blur", validateScadenza);

    numeroCarta.addEventListener("input", function () {
        this.value = this.value.replace(/\D/g, "").substring(0, 16);
        clearError(this);
    });
    numeroCarta.addEventListener("blur", validateNumeroCarta);

    cvv.addEventListener("input", function () {
        this.value = this.value.replace(/\D/g, "").substring(0, 3);
        clearError(this);
    });
    cvv.addEventListener("blur", validateCvv);

    form.addEventListener("submit", function (e) {
        e.preventDefault();

        if (!validateAll()) {
            return;
        }

        document.getElementById("confirmPopup").classList.remove("hidden");
    });

    document.getElementById("confirmYes").addEventListener("click", () => {
        document.getElementById("confirmPopup").classList.add("hidden");
        form.submit();
    });

    document.getElementById("confirmNo").addEventListener("click", () => {
        document.getElementById("confirmPopup").classList.add("hidden");
    });

});