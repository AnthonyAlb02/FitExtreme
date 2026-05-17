// Controllo password live
const pass = document.getElementById("password");
const confirm = document.getElementById("confirm");
const passCheck = document.getElementById("pass-check");

confirm.addEventListener("input", () => {
    if (pass.value !== confirm.value) {
        passCheck.textContent = "Le password non coincidono";
        passCheck.style.color = "red";
    } else {
        passCheck.textContent = "Password ok";
        passCheck.style.color = "green";
    }
});

// Controllo email AJAX
const email = document.getElementById("email");
const emailCheck = document.getElementById("email-check");

email.addEventListener("blur", () => {

    fetch("checkEmail", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: "email=" + encodeURIComponent(email.value)
    })
    .then(res => res.json())
    .then(data => {
        if (data.exists === true) {
            emailCheck.textContent = "Email già registrata";
            emailCheck.style.color = "red";
        } else {
            emailCheck.textContent = "Email disponibile";
            emailCheck.style.color = "green";
        }
    });
});
