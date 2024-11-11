// Função para carregar dados do usuário
async function loadUserProfile() {
    const userId = localStorage.getItem("userId");

    if (!userId) {
        alert("ID do usuário não encontrado. Você precisa fazer login.");
        window.location.href = "login.html";
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/usuario/perfil?idReceived=${encodeURIComponent(userId)}`);
        const data = await response.json();

        document.getElementById("name").value = data.name;
        document.getElementById("email").value = data.email;
        document.getElementById("phone").value = data.phone || '';
    } catch (error) {
        console.error("Erro ao carregar perfil:", error);
        alert("Erro ao carregar o perfil. Tente novamente.");
    }
}

// Função para atualizar o perfil
async function updateProfile() {
    const userId = localStorage.getItem("userId");
    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const phone = document.getElementById("phone").value;

    try {
        const response = await fetch(`http://localhost:8080/usuario/atualizar?userId=${userId}`, {
            method: "PUT",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ name: name, email: email, phone: phone })
        });

        if (response.ok) {
            alert("Perfil atualizado com sucesso.");
        } else {
            alert("Erro ao atualizar perfil.");
        }
    } catch (error) {
        console.error("Erro ao atualizar perfil:", error);
        alert("Erro ao atualizar perfil. Tente novamente.");
    }

    loadUserProfile();
}

// Função para confirmar exclusão de conta
function confirmDeleteAccount() {
    if (confirm("Tem certeza que deseja excluir sua conta? Esta ação não pode ser desfeita.")) {
        deleteAccount();
    }
}

// Função para excluir a conta
async function deleteAccount() {
    const userId = localStorage.getItem("userId");

    try {
        const response = await fetch(`http://localhost:8080/usuario/deletar?idReceived=${encodeURIComponent(userId)}`, {
            method: "DELETE",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            }
        });

        if (response.ok) {
            alert("Conta excluída com sucesso.");
            localStorage.clear();
            window.location.href = "login.html";
        } else {
            alert("Erro ao excluir conta.");
        }
    } catch (error) {
        console.error("Erro ao excluir conta:", error);
        alert("Erro ao excluir conta. Tente novamente.");
    }
}

// Carrega o perfil ao iniciar a página
document.addEventListener("DOMContentLoaded", loadUserProfile);
