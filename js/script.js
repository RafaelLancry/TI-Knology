// Validação de Email
function validateEmail(email) {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email);
}

// Exibir Mensagem
function showMessage(elementId, message, success = true) {
    const messageElement = document.getElementById(elementId);
    messageElement.innerText = message;
    messageElement.style.color = success ? "green" : "red";
}