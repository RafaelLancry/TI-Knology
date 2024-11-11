let services = {}; // Variável para armazenar serviços carregados

// Função para buscar serviços da API
async function fetchServices() {
    try {
        const response = await fetch("http://localhost:8080/services");
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        services = await response.json(); // Armazena os serviços na variável global

        const serviceTypeSelect = document.getElementById("serviceType");
        serviceTypeSelect.innerHTML = '<option value="">Selecione um serviço</option>';

        // Adiciona novos serviços ao select
        services.forEach(service => {
            if(service.status == "ANALISE"){
                const option = document.createElement("option");
                option.value = service.name; // Aqui usamos o nome do serviço
                option.textContent = service.name;
                serviceTypeSelect.appendChild(option);
            }
        });
    } catch (error) {
        console.error("Error fetching services:", error);
    }
}

// Função para atualizar os detalhes do serviço selecionado
function updateServiceDetails() {
    const selectedService = document.getElementById("serviceType").value;

    // Encontra o serviço correspondente na lista de serviços
    const serviceDetails = services.find(service => service.name === selectedService);

    if (serviceDetails) {
        document.getElementById("servicePrice").textContent = `R$ ${serviceDetails.price.toFixed(2)}`;
        document.getElementById("serviceDeadline").textContent = `${serviceDetails.due} dias`;

        const currentDate = new Date();
        const expectedDate = new Date(currentDate);
        expectedDate.setDate(currentDate.getDate() + serviceDetails.due);
        document.getElementById("expectedDate").textContent = expectedDate.toISOString().split('T')[0];
    } else {
        // Exibe "R$ 0,00" em vez de "-" para manter o layout fixo
        document.getElementById("servicePrice").textContent = "R$ 0,00";
        document.getElementById("serviceDeadline").textContent = "-";
        document.getElementById("expectedDate").textContent = "-";
    }
}

// Função para adicionar uma solicitação de serviço
function addRequest() {
    const serviceType = document.getElementById("serviceType").value;
    if (!serviceType) {
        alert("Por favor, selecione um serviço.");
        return;
    }

    const serviceDetails = services.find(service => service.name === serviceType);
    
    date = new Date().toISOString().split('T')[0]
    const request = {
        date: date,
        id: serviceDetails.id, // Geração de ID aleatório
        serviceName: serviceDetails.name,
        status: serviceDetails.status, // Status padrão
        price: serviceDetails.price,
        deadline: `${serviceDetails.due} dias`,
    };

    const serviceRequests = JSON.parse(localStorage.getItem("serviceRequests") || "[]");
    serviceRequests.push(request);
    localStorage.setItem("serviceRequests", JSON.stringify(serviceRequests));

    renderServiceRequests();
    updatePartialValue();
}


function renderServiceRequests() {
    const serviceRequestsTableBody = document.getElementById("serviceRequestsTableBody");
    serviceRequestsTableBody.innerHTML = "";
    const serviceRequests = JSON.parse(localStorage.getItem("serviceRequests") || "[]");

    serviceRequests.forEach(request => {
        const formattedPrice = request.price.toFixed(2);

        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${request.date}</td>
            <td>${request.serviceName}</td>
            <td>${request.status.replace("_", " ")}</td>
            <td>R$ ${formattedPrice}</td>
            <td>${request.deadline}</td>
            <td><button onclick="deleteRow(this)">Excluir</button></td>
        `;
        serviceRequestsTableBody.appendChild(row);
    });
}



function deleteRow(button) {
    const row = button.closest("tr");
    row.parentNode.removeChild(row);

    const serviceName = row.cells[1].textContent;
    let serviceRequests = JSON.parse(localStorage.getItem("serviceRequests") || "[]");
    serviceRequests = serviceRequests.filter(request => request.serviceName !== serviceName);
    localStorage.setItem("serviceRequests", JSON.stringify(serviceRequests));

    updatePartialValue();
}

async function fetchUserData() {
    var dataReturn = null;
    const userId = localStorage.getItem("userId");

    if (!userId) {
        alert("ID do usuário não encontrado. Você precisa fazer login novamente.");
        window.location.href = "login.html";
        return;
    }

    await fetch(`http://localhost:8080/usuario?idReceived=${encodeURIComponent(userId)}`,
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
    document.getElementById("userName").textContent = `Seja bem vindo ${data.name}!`;
}


// Função para atualizar o valor parcial das solicitações
function updatePartialValue() {
    const serviceRequests = JSON.parse(localStorage.getItem("serviceRequests") || "[]");
    const partialValue = serviceRequests.reduce((total, request) => total + request.price, 0);
    document.getElementById("partialValue").textContent = `R$ ${partialValue.toFixed(2)}`;
}

// Função para abrir o modal de pagamento
function openPaymentModal() {
    const serviceRequests = JSON.parse(localStorage.getItem("serviceRequests") || "[]");
    const partialValue = serviceRequests.reduce((total, request) => total + request.price, 0);

    const taxes = partialValue * 0.1; // 10% de impostos
    const totalValue = partialValue + taxes + 50.0; // Frete fixo de R$ 50,00

    document.getElementById("modalPartialValue").textContent = `R$ ${partialValue.toFixed(2)}`;
    document.getElementById("taxesValue").textContent = `R$ ${taxes.toFixed(2)}`;
    document.getElementById("shippingValue").textContent = `R$ 50,00`;
    document.getElementById("totalValue").textContent = `R$ ${totalValue.toFixed(2)}`;

    document.getElementById("paymentModal").style.display = "flex";
}

// Função para fechar o modal de pagamento
function closePaymentModal() {
    document.getElementById("paymentModal").style.display = "none";
    document.getElementById("creditCardForm").style.display = "none";
    document.getElementById("qrCodePix").style.display = "none";
}

// Função para selecionar o método de pagamento
function selectPaymentMethod(method) {
    document.getElementById("creditCardForm").style.display = method === "visa" || method === "mastercard" ? "block" : "none";
    document.getElementById("qrCodePix").style.display = method === "pix" ? "block" : "none";
}

async function finalizarCompra() {
    const userId = localStorage.getItem("userId"); // Obtém o ID do usuário do localStorage
    const serviceRequests = JSON.parse(localStorage.getItem("serviceRequests") || "[]");

    if (serviceRequests.length === 0) {
        alert("Não há solicitações de serviço para finalizar a compra.");
        return;
    }

    const serviceIds = serviceRequests.map(request => request.id);

    const serviceIdsString = serviceIds.join(',');


    // Faz o fetch para enviar os dados de compra
    fetch(`http://localhost:8080/pagamento/confirmar/${userId}?serviceIds=${serviceIdsString}`, {
        method: "POST",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        }
    }).then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
    }).then(data => {
        console.log("Compras criadas:", data);
        localStorage.removeItem("serviceRequests"); // Limpa as solicitações
        renderServiceRequests(); // Atualiza a tabela
        updatePartialValue(); // Atualiza o valor parcial
        closePaymentModal(); // Fecha o modal de pagamento
        alert("Compra finalizada com sucesso!");
    }).catch(error => {
        console.error("Erro ao confirmar compra:", error);
        alert("Houve um erro ao finalizar a compra. Tente novamente.");
    });
}


// Evento para carregar dados ao carregar a página
document.addEventListener("DOMContentLoaded", () => {
    fetchUserData();
    fetchServices();
    renderServiceRequests();
    updatePartialValue();
});