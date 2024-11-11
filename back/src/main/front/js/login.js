// login.js

function validateLogin() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    if (!email || !validateEmail(email)) {
        alert("Por favor, insira um e-mail válido.");
        return;
    }

    if (!password) {
        alert("Por favor, insira a senha.");
        return;
    }

    validateUser(email, password);
}

async function validateUser(userEmail, userPassword){
    var dataReturn = null;
    await fetch(`http://localhost:8080/usuario/login?email=${encodeURIComponent(userEmail)}&password=${encodeURIComponent(userPassword)}`,
    {
        method: "GET",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        }
    }).then(response => {
        if (!response) {
            return null;
        }
        return response.json();
    }).then(data => {
        dataReturn = data;
    }).catch( error => {
        alert("Credenciais inválidas. Tente novamente.", error);
    })

    console.log(dataReturn);

    handleRedirect(dataReturn);
}

function handleRedirect(user){
    if (user != null) {
        localStorage.setItem("isLoggedIn", true);
        localStorage.setItem("userName", user.name);
        localStorage.setItem("userId", user.id);
        window.location.href = "../index.html";
    }
}

function validateEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

// Função de logout
function logout() {
    localStorage.removeItem("isLoggedIn");
    localStorage.removeItem("userName");
    localStorage.removeItem("userEmail");
    window.location.href = "../index.html";
}
