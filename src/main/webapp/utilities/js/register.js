(function() {
  var pwd = document.getElementById('password');
  var conf = document.getElementById('confirm');
  var passCheck = document.getElementById('pass-check');

  var pwdRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^A-Za-z\d]).{8,}$/;

  function updatePasswordValidity() {
    var value = pwd.value || '';
    var confValue = conf.value || '';

    if (value.length === 0 && confValue.length === 0) {
      passCheck.textContent = '';
      pwd.setCustomValidity('');
      return false;
    }

    if (!pwdRegex.test(value)) {
      passCheck.textContent = "La password deve avere almeno 8 caratteri, una maiuscola, una minuscola, un numero e un carattere speciale.";
      passCheck.style.color = "red";
      pwd.setCustomValidity("Password non valida");
      return false;
    }

    if (confValue.length > 0 && value !== confValue) {
      passCheck.textContent = "Le password non corrispondono.";
      passCheck.style.color = "red";
      pwd.setCustomValidity("Le password non corrispondono");
      return false;
    }

    passCheck.textContent = "Password valida.";
    passCheck.style.color = "green";
    pwd.setCustomValidity('');
    return true;
  }

  pwd.addEventListener('input', updatePasswordValidity);
  conf.addEventListener('input', updatePasswordValidity);

  pwd.addEventListener('blur', updatePasswordValidity);
  conf.addEventListener('blur', updatePasswordValidity);

  var form = document.querySelector('form.auth-form');
  if (form) {
    form.addEventListener('submit', function(e) {
      var ok = updatePasswordValidity();
      if (!ok) {
        e.preventDefault();
        pwd.focus();
        if (typeof pwd.reportValidity === 'function') pwd.reportValidity();
      }
    });
  }
})();



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
