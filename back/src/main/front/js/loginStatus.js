// loginStatus.js

document.addEventListener("DOMContentLoaded", function() {
    const isLoggedIn = localStorage.getItem("isLoggedIn");

    const userNameElement = document.getElementById("userName");
    const userEmailElement = document.getElementById("userEmail");

    if (userNameElement) {
        userNameElement.textContent = localStorage.getItem("userName") || "Usuário não identificado";
    }

    const logoutLink = document.getElementById("logoutLink");
    if (logoutLink) {
        logoutLink.style.display = isLoggedIn ? "block" : "none";
    }

    const serviceContractLink = document.getElementById("serviceContractLink");
    if (serviceContractLink) {
        serviceContractLink.style.display = isLoggedIn ? "block" : "none";
        profileLink.style.display = isLoggedIn ? "block" : "none";
    }

    const loginLink = document.getElementById("loginLink");
    const registerLink = document.getElementById("registerLink");
    if (isLoggedIn) {
        if (loginLink) loginLink.style.display = "none";
        if (registerLink) registerLink.style.display = "none";
    } else {
        if (loginLink) loginLink.style.display = "block";
        if (registerLink) registerLink.style.display = "block";
    }
});

// Função de logout
function logout() {
    localStorage.removeItem("isLoggedIn");
    localStorage.removeItem("userName");
    localStorage.removeItem("userEmail");
    window.location.href = "../pages/index.html";
}
