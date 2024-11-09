// Função para validar o formato do e-mail
function validateEmail(email) {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email);
}

// Função para validar os requisitos mínimos da senha
function validatePassword(password) {
    const regex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[!@#$%]).{8,}$/;
    return regex.test(password);
}

// Função para validar o CPF
function isValidCPF(cpf) {
    cpf = cpf.replace(/[^\d]/g, "");
    if (cpf.length !== 11 || /^(\d)\1{10}$/.test(cpf)) return false;

    let sum = 0, remainder;

    for (let i = 1; i <= 9; i++) sum += parseInt(cpf.charAt(i - 1)) * (11 - i);
    remainder = (sum * 10) % 11;
    if (remainder === 10 || remainder === 11) remainder = 0;
    if (remainder !== parseInt(cpf.charAt(9))) return false;

    sum = 0;
    for (let i = 1; i <= 10; i++) sum += parseInt(cpf.charAt(i - 1)) * (12 - i);
    remainder = (sum * 10) % 11;
    if (remainder === 10 || remainder === 11) remainder = 0;
    if (remainder !== parseInt(cpf.charAt(10))) return false;

    return true;
}

// Função para validar a idade (mínimo de 18 anos)
function validateAge(birthDate) {
    const today = new Date();
    const birth = new Date(birthDate);
    let age = today.getFullYear() - birth.getFullYear();
    const month = today.getMonth() - birth.getMonth();

    if (month < 0 || (month === 0 && today.getDate() < birth.getDate())) {
        age--;
    }

    return age >= 18;
}

// Função para validar o telefone
function validatePhone(phone) {
    const phonePattern = /^\d{10,11}$/;
    return phonePattern.test(phone.replace(/\D/g, ""));
}

// Função de validação de requisitos de senha em tempo real
function validatePasswordRequirements() {
    const password = document.getElementById("password").value;
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

// Função para alterar a senha
function changePassword() {
    const login = document.getElementById("login").value;
    const dob = document.getElementById("dob").value;
    const cpf = document.getElementById("cpf").value;
    const phone = document.getElementById("phone").value;
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;
    const messageElement = document.getElementById("message");

    // Limpa a mensagem
    messageElement.textContent = "";

    // Validações
    if (!login) {
        messageElement.textContent = "O login deve ser preenchido.";
        return;
    }
    if (!validateEmail(login)) {
        messageElement.textContent = "Formato de e-mail inválido.";
        return;
    }
    if (!dob) {
        messageElement.textContent = "A data de nascimento deve ser preenchida.";
        return;
    }
    if (!isValidCPF(cpf)) {
        messageElement.textContent = "CPF inválido.";
        return;
    }
    if (!validatePhone(phone)) {
        messageElement.textContent = "Telefone inválido.";
        return;
    }
    if (!password) {
        messageElement.textContent = "A senha deve ser preenchida.";
        return;
    }
    if (!confirmPassword) {
        messageElement.textContent = "A confirmação de senha deve ser preenchida.";
        return;
    }
    if (password !== confirmPassword) {
        messageElement.textContent = "As senhas não coincidem.";
        return;
    }
    if (!validatePassword(password)) {
        messageElement.textContent = "A senha não atende aos requisitos de segurança.";
        return;
    }

    // Busca o usuário na lista 'users' do localStorage
    const users = JSON.parse(localStorage.getItem("users") || "[]");
    const userIndex = users.findIndex(user => 
        user.email === login &&
        user.birthDate === dob &&
        user.cpf === cpf &&
        user.phone === phone
    );

    if (userIndex === -1) {
        messageElement.textContent = "Usuário não encontrado. Verifique suas informações.";
        return;
    }

    // Atualiza a senha do usuário encontrado
    users[userIndex].password = password;
    localStorage.setItem("users", JSON.stringify(users));

    alert("Senha alterada com sucesso!");
    window.history.back();
}

// Função para limpar campos
function clearFields() {
    document.getElementById("login").value = "";
    document.getElementById("dob").value = "";
    document.getElementById("cpf").value = "";
    document.getElementById("phone").value = "";
    document.getElementById("password").value = "";
    document.getElementById("confirmPassword").value = "";
    document.getElementById("message").textContent = "";
    document.getElementById("login").focus();
}
