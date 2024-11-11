

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
    let dataReturn;
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
        alert("Credenciais inválidas. Tente novamente.");
        window.location.href = "login.html";
    })

    userDataValidation(dataReturn)
}

function userDataValidation(data){
    document.getElementById("userName").textContent = data.name;
    document.getElementById("userEmail").textContent = data.email;
    loadContractedServices();
}

async function fetchAcquiredServices(){
    const userId = localStorage.getItem("userId");

    if (!userId) {
        alert("ID do usuário não encontrado. Você precisa fazer login.");
        window.location.href = "login.html";
    }

    fetch(`http://localhost:8080/usuario/perfil/biblioteca?idReceived=${userId}`, {
        method: "GET",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            const profileTableBody = document.getElementById("servicesTableBody");
            data.forEach(request => {
                const formattedPrice = request.price.toFixed(2);
                const date = new Date(request.deliverDate);
                const options = { day: 'numeric', month: 'long', year: 'numeric' };
                const formattedDate = date.toLocaleDateString('pt-BR', options);
                const row = document.createElement("tr");
                row.innerHTML = `
                <td>${formattedDate}</td>
                <td>${request.name}</td>
                <td>${request.status}</td>
                <td>R$ ${formattedPrice}</td>
                <td>${request.due}</td>
            `;
                    profileTableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error("Erro ao buscar a biblioteca do usuário:", error);
            alert("Houve um erro ao carregar a biblioteca. Tente novamente.");
        });
}

document.addEventListener("DOMContentLoaded", function() {
    fetchUserData();
    fetchAcquiredServices();
});