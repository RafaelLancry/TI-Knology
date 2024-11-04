// gallery.js

// Função para abrir o modal e exibir a imagem ampliada
function openModal(imageSource) {
    const modal = document.getElementById("imageModal");
    const expandedImage = document.getElementById("expandedImg");
    
    expandedImage.src = imageSource;
    modal.style.display = "block";
}

// Função para fechar o modal
function closeModal() {
    const modal = document.getElementById("imageModal");
    modal.style.display = "none";
}
