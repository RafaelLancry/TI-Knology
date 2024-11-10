// Exibir dados do usuário ao carregar a página


// Carregar e exibir os serviços contratados
function loadContractedServices() {
    const servicesTableBody = document.getElementById("servicesTableBody");
    const services = JSON.parse(localStorage.getItem("serviceRequests") || "[]");

    servicesTableBody.innerHTML = "";
    services.forEach(service => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${service.date}</td>
            <td>${service.serviceName}</td>
            <td>${service.status}</td>
            <td>R$ ${service.price.toFixed(2)}</td>
            <td>${service.deadline}</td>
        `;
        servicesTableBody.appendChild(row);
    });
}

async function fetchUserData() {
    var dataReturn = null;
    const userId = localStorage.getItem("userId");

    if (!userId) {
        alert("ID do usuário não encontrado. Você precisa fazer login novamente.");
        window.location.href = "login.html";
        return;
    }

    await fetch(`http://localhost:8080/usuario/perfil?idReceived=${encodeURIComponent(userId)}`,
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

    userDataValidation(dataReturn)
}

function userDataValidation(data){
    document.getElementById("userName").textContent = data.name;
    document.getElementById("userEmail").textContent = data.email;
    loadContractedServices();
}    

document.addEventListener("DOMContentLoaded", function() {
    fetchUserData();
});