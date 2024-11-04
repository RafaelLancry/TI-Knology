const services = {
    software: { name: "Desenvolvimento de Software", price: 5000, deadline: 30 },
    consultoria: { name: "Consultoria em TI", price: 3000, deadline: 15 },
    cloud: { name: "Soluções em Nuvem", price: 4000, deadline: 20 },
    softwarePersonalizado: { name: "Desenvolvimento de Software Personalizado", price: 8000, deadline: 45 },
    transformacaoDigital: { name: "Consultoria em Transformação Digital", price: 7000, deadline: 25 },
    ia: { name: "Inteligência Artificial e Aprendizado de Máquina", price: 12000, deadline: 60 },
    iot: { name: "Soluções de Internet das Coisas (IoT)", price: 9000, deadline: 50 }
};

const TAX_RATE = 0.1; // 10% de imposto
const SHIPPING_COST = 50.0; // Frete fixo de R$ 50,00

function fetchUserData() {
    const isLoggedIn = localStorage.getItem("isLoggedIn");
    const userName = localStorage.getItem("userName");
    const userEmail = localStorage.getItem("userEmail");

    if (isLoggedIn && userName && userEmail) {
        document.getElementById("userName").textContent = `Login: ${userName}`;
        document.getElementById("userEmail").textContent = `Email: ${userEmail}`;
    } else {
        window.location.href = "login.html";
    }
}

function updateServiceDetails() {
    const selectedService = document.getElementById("serviceType").value;
    const serviceDetails = services[selectedService];

    if (serviceDetails) {
        document.getElementById("servicePrice").textContent = `R$ ${serviceDetails.price.toFixed(2)}`;
        document.getElementById("serviceDeadline").textContent = `${serviceDetails.deadline} dias`;

        const currentDate = new Date();
        const expectedDate = new Date(currentDate);
        expectedDate.setDate(currentDate.getDate() + serviceDetails.deadline);
        document.getElementById("expectedDate").textContent = expectedDate.toISOString().split('T')[0];
    } else {
        // Exibe "R$ 0,00" em vez de "-" para manter o layout fixo
        document.getElementById("servicePrice").textContent = "R$ 0,00";
        document.getElementById("serviceDeadline").textContent = "-";
        document.getElementById("expectedDate").textContent = "-";
    }
}


function addRequest() {
    const serviceType = document.getElementById("serviceType").value;
    if (!serviceType) {
        alert("Por favor, selecione um serviço.");
        return;
    }

    const serviceDetails = services[serviceType];
    const request = {
        date: new Date().toISOString().split('T')[0],
        id: Math.floor(Math.random() * 1000),
        serviceName: serviceDetails.name,
        status: "EM ELABORAÇÃO",
        price: Number(serviceDetails.price),
        deadline: `${serviceDetails.deadline} dias`,
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
        const formattedPrice = typeof request.price === "number" ? request.price.toFixed(2) : parseFloat(request.price).toFixed(2);

        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${request.date}</td>
            <td>${request.serviceName}</td>
            <td>${request.status}</td>
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

function updatePartialValue() {
    const serviceRequests = JSON.parse(localStorage.getItem("serviceRequests") || "[]");
    const partialValue = serviceRequests.reduce((total, request) => total + (typeof request.price === "number" ? request.price : parseFloat(request.price)), 0);
    document.getElementById("partialValue").textContent = `R$ ${partialValue.toFixed(2)}`;
}

function openPaymentModal() {
    const serviceRequests = JSON.parse(localStorage.getItem("serviceRequests") || "[]");
    const partialValue = serviceRequests.reduce((total, request) => total + (typeof request.price === "number" ? request.price : parseFloat(request.price)), 0);

    const taxes = partialValue * TAX_RATE;
    const totalValue = partialValue + taxes + SHIPPING_COST;

    document.getElementById("modalPartialValue").textContent = `R$ ${partialValue.toFixed(2)}`;
    document.getElementById("taxesValue").textContent = `R$ ${taxes.toFixed(2)}`;
    document.getElementById("shippingValue").textContent = `R$ ${SHIPPING_COST.toFixed(2)}`;
    document.getElementById("totalValue").textContent = `R$ ${totalValue.toFixed(2)}`;

    document.getElementById("paymentModal").style.display = "flex";
}

function closePaymentModal() {
    document.getElementById("paymentModal").style.display = "none";
    document.getElementById("creditCardForm").style.display = "none";
    document.getElementById("qrCodePix").style.display = "none";
}

function selectPaymentMethod(method) {
    document.getElementById("creditCardForm").style.display = method === "visa" || method === "mastercard" ? "block" : "none";
    document.getElementById("qrCodePix").style.display = method === "pix" ? "block" : "none";
}

function finalizarCompra() {
    localStorage.removeItem("serviceRequests");
    renderServiceRequests();
    updatePartialValue();
    closePaymentModal();
    alert("Compra finalizada com sucesso!");
}

document.addEventListener("DOMContentLoaded", () => {
    fetchUserData();
    renderServiceRequests();
    updatePartialValue();
});
