async function changePassword() {
    const userId = localStorage.getItem("userId");
    const currentPassword = document.getElementById("currentPassword").value;
    const newPassword = document.getElementById("newPassword").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    if (newPassword !== confirmPassword) {
        document.getElementById("passwordMessage").textContent = "As senhas novas não coincidem.";
        document.getElementById("passwordMessage").style.color = "red";
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/usuario/trocarSenha`, {
            method: "PUT",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                userId: userId,
                currentPassword: currentPassword,
                newPassword: newPassword
            })
        });

        if (response.ok) {
            document.getElementById("passwordMessage").textContent = "Senha alterada com sucesso!";
            document.getElementById("passwordMessage").style.color = "green";
        } else {
            document.getElementById("passwordMessage").textContent = "Erro ao trocar senha. Verifique a senha atual.";
            document.getElementById("passwordMessage").style.color = "red";
        }
    } catch (error) {
        console.error("Erro ao trocar senha:", error);
        document.getElementById("passwordMessage").textContent = "Erro ao trocar senha. Tente novamente.";
        document.getElementById("passwordMessage").style.color = "red";
    }
}

// Função para validar os requisitos mínimos da senha
function validatePassword(password) {
    const regex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[!@#$%]).{8,}$/;
    return regex.test(password);
}

// Função de validação de requisitos de senha em tempo real
function validatePasswordRequirements() {
    const password = document.getElementById("newPassword").value;
    const lengthRequirement = document.getElementById("lengthRequirement");
    const numberRequirement = document.getElementById("numberRequirement");
    const uppercaseRequirement = document.getElementById("uppercaseRequirement");
    const lowercaseRequirement = document.getElementById("lowercaseRequirement");
    const specialCharRequirement = document.getElementById("specialCharRequirement");

    lengthRequirement.classList.toggle("valid", password.length >= 8);
    lengthRequirement.classList.toggle("invalid", password.length < 8);

    numberRequirement.classList.toggle("valid", /[0-9]/.test(password));
    numberRequirement.classList.toggle("invalid", !/[0-9]/.test(password));

    uppercaseRequirement.classList.toggle("valid", /[A-Z]/.test(password));
    uppercaseRequirement.classList.toggle("invalid", !/[A-Z]/.test(password));

    lowercaseRequirement.classList.toggle("valid", /[a-z]/.test(password));
    lowercaseRequirement.classList.toggle("invalid", !/[a-z]/.test(password));

    specialCharRequirement.classList.toggle("valid", /[!@#$%]/.test(password));
    specialCharRequirement.classList.toggle("invalid", !/[!@#$%]/.test(password));
}