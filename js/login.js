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

    const users = JSON.parse(localStorage.getItem("users") || "[]");
    const user = users.find(user => user.email === email && user.password === password);

    if (user) {
        alert("Login realizado com sucesso!");
        localStorage.setItem("isLoggedIn", true);
        localStorage.setItem("userName", user.name);
        localStorage.setItem("userEmail", user.email);
        window.location.href = "../index.html";
    } else {
        alert("Credenciais inválidas. Tente novamente.");
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
