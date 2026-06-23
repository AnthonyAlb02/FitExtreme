(function() {
  const pwd = document.getElementById('password');
  const conf = document.getElementById('confirm');
  const passCheck = document.getElementById('pass-check');

  // Regex password (coerente con HTML)
  const pwdRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^A-Za-z\d]).{8,}$/;

  function updatePasswordValidity() {
    const value = pwd.value.trim();
    const confValue = conf.value.trim();

    // Nessun input → reset
    if (!value && !confValue) {
      passCheck.textContent = '';
      pwd.setCustomValidity('');
      return false;
    }

    // Regex non rispettata
    if (!pwdRegex.test(value)) {
      passCheck.textContent = "La password deve avere almeno 8 caratteri, una maiuscola, una minuscola, un numero e un carattere speciale.";
      passCheck.style.color = "red";
      pwd.setCustomValidity("Password non valida");
      return false;
    }

    // Password diverse
    if (confValue && value !== confValue) {
      passCheck.textContent = "Le password non corrispondono.";
      passCheck.style.color = "red";
      pwd.setCustomValidity("Le password non corrispondono");
      return false;
    }

    // Tutto ok
    passCheck.textContent = "Password valida.";
    passCheck.style.color = "green";
    pwd.setCustomValidity('');
    return true;
  }

  pwd.addEventListener('input', updatePasswordValidity);
  conf.addEventListener('input', updatePasswordValidity);
  pwd.addEventListener('blur', updatePasswordValidity);
  conf.addEventListener('blur', updatePasswordValidity);

  // Validazione finale al submit
  const form = document.querySelector('form.auth-form');
  if (form) {
    form.addEventListener('submit', function(e) {
      if (!updatePasswordValidity()) {
        e.preventDefault();
        pwd.focus();
        if (typeof pwd.reportValidity === "function") {
          pwd.reportValidity();
        }
      }
    });
  }
  })();



// ===============================
//   CONTROLLO EMAIL AJAX
// ===============================

const email = document.getElementById("email");
const emailCheck = document.getElementById("email-check");

// Regex email lato JS (coerente con HTML)
const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

email.addEventListener("blur", () => {

    const value = email.value.trim();

    // Se email non valida → non chiamare AJAX
    if (!emailRegex.test(value)) {
        emailCheck.textContent = "Formato email non valido";
        emailCheck.style.color = "red";
        return;
    }

    // Controllo AJAX
    fetch("checkEmail", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: "email=" + encodeURIComponent(value)
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
    })
    .catch(() => {
        emailCheck.textContent = "Errore di connessione";
        emailCheck.style.color = "red";
    });
});
