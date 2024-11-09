// register.js

// Função para aplicar a máscara no campo CPF
function applyCpfMask(event) {
    let value = event.target.value.replace(/\D/g, "");
    if (value.length > 11) value = value.slice(0, 11);

    if (value.length <= 3) {
        event.target.value = value;
    } else if (value.length <= 6) {
        event.target.value = value.replace(/(\d{3})(\d+)/, "$1.$2");
    } else if (value.length <= 9) {
        event.target.value = value.replace(/(\d{3})(\d{3})(\d+)/, "$1.$2.$3");
    } else {
        event.target.value = value.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, "$1.$2.$3-$4");
    }
}

// Função para aplicar a máscara no campo Telefone
function applyPhoneMask(event) {
    let value = event.target.value.replace(/\D/g, "");
    if (value.length > 11) value = value.slice(0, 11);

    if (value.length <= 2) {
        event.target.value = `(${value}`;
    } else if (value.length <= 6) {
        event.target.value = value.replace(/(\d{2})(\d+)/, "($1) $2");
    } else if (value.length <= 10) {
        event.target.value = value.replace(/(\d{2})(\d{4})(\d+)/, "($1) $2-$3");
    } else {
        event.target.value = value.replace(/(\d{2})(\d{5})(\d{4})/, "($1) $2-$3");
    }
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

// Função para validar o nome completo
function validateName(name) {
    const regex = /^[a-zA-ZÀ-ÿ\s]{2,}$/;
    const words = name.trim().split(" ");
    return words.length >= 2 && words.every(word => word.length >= 2);
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
    const regex = /^\(\d{2}\) \d{4,5}-\d{4}$/;
    return regex.test(phone);
}

// Adiciona evento para remover o vermelho quando o usuário começa a preencher
function addInputListeners(elements) {
    elements.forEach(element => {
        element.addEventListener("input", () => {
            if (element.value) {
                element.style.borderColor = ""; // Remove o vermelho
            }
        });
    });
}

document.getElementById("cpf").addEventListener("input", applyCpfMask);
document.getElementById("phone").addEventListener("input", applyPhoneMask);
document.getElementById("password").addEventListener("input", validatePasswordRequirements);

function validateRegistration() {
    const emailInput = document.getElementById("email");
    const passwordInput = document.getElementById("password");
    const confirmPasswordInput = document.getElementById("confirmPassword");
    const nameInput = document.getElementById("name");
    const cpfInput = document.getElementById("cpf");
    const birthDateInput = document.getElementById("birthDate");
    const phoneInput = document.getElementById("phone");

    const requiredInputs = [emailInput, passwordInput, confirmPasswordInput, nameInput, cpfInput, birthDateInput];
    let valid = true;

    // Verifica se todos os campos obrigatórios estão preenchidos
    requiredInputs.forEach(input => {
        if (!input.value) {
            input.style.borderColor = "red";
            valid = false;
        }
    });

    // Se houver campos obrigatórios vazios, exibe o alerta e adiciona listeners para corrigir o visual ao preencher
    if (!valid) {
        alert("Preencha todos os campos obrigatórios!");
        addInputListeners(requiredInputs);
        return;
    }

    if (!validateEmail(emailInput.value)) {
        alert("Por favor, insira um e-mail válido.");
        emailInput.style.borderColor = "red";
        return;
    }

    if (!validatePassword(passwordInput.value)) {
        alert("A senha não atende aos requisitos mínimos.");
        passwordInput.style.borderColor = "red";
        return;
    }

    if (passwordInput.value !== confirmPasswordInput.value) {
        alert("As senhas não coincidem.");
        confirmPasswordInput.style.borderColor = "red";
        return;
    }

    if (!validateName(nameInput.value)) {
        alert("Por favor, insira um nome completo válido.");
        nameInput.style.borderColor = "red";
        return;
    }

    if (!isValidCPF(cpfInput.value)) {
        alert("Por favor, insira um CPF válido.");
        cpfInput.style.borderColor = "red";
        return;
    }

    if (!validateAge(birthDateInput.value)) {
        alert("Você deve ter pelo menos 18 anos.");
        birthDateInput.style.borderColor = "red";
        return;
    }

    if (phoneInput.value && !validatePhone(phoneInput.value)) {
        alert("Por favor, insira um telefone válido.");
        phoneInput.style.borderColor = "red";
        return;
    }

    const userData = {
        email: emailInput.value,
        password: passwordInput.value,
        name: nameInput.value,
        cpf: cpfInput.value.replace(/\D/g, ""),
        birthDate: birthDateInput.value,
        phone: phoneInput.value.replace(/\D/g, "")
    };

    console.log(userData);

    saveUserData(userData);
}

async function saveUserData(userData) {
    await fetch("http://localhost:8080/usuario/registro",
    {
        method: "POST",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            birthdate: userData.birthDate,
            cpf: userData.cpf,
            email: userData.email,
            name: userData.name,
            password: userData.password,
            phone: userData.phone
        }),
    })
    .then(function (res) {console.log(res) })
    .catch(function (res) { console.log(res) })


    window.location.href = "login.html";
}
