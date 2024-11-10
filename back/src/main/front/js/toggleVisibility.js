// toggleVisibility.js

function toggleVisibility(elementId, button) {
    const element = document.getElementById(elementId);
    if (element.style.display === "none") {
        element.style.display = "block";
        button.textContent = "Ocultar"; // Muda o texto do botão para "Ocultar"
    } else {
        element.style.display = "none";
        button.textContent = "Ver Mais"; // Restaura o texto original do botão para "Ver Mais"
    }
}
