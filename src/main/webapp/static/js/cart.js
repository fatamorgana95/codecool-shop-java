let removeButtons = document.querySelectorAll("#remove-button");
let checkOutButton = document.querySelector("#check-out");
let homeButton = document.querySelector("#home");
let quantityInputs = document.querySelectorAll("#quantity-input");


function init2() {
    quantityInputs.forEach((inputField) => {
        inputField.addEventListener("change", function(){
            let itemId = this.dataset.id;
            let quantity = this.value;
            let data = {"itemId": itemId, "quantity": quantity};
            fetch2( data,quantity, changeQuantity, this);
        });})
}


function fetch2(data, quantity, callback, quantityInput) {
    $.post("/cart/change", data, function(response) {callback(quantity, quantityInput, response)});
}


function changeQuantity(quantity, quantityInput, response){
    let row = quantityInput.closest(".form-row");
    let parts = response.split(",");
    let subtotalPrice = parts[0];
    let totalPrice = parts[1];
    if (quantity <= 1){
        quantityInput.value = 1;
    }
    row.querySelector("#subtotal").innerText = subtotalPrice;
    document.querySelector("#total").innerText = totalPrice;
}

function init() {
    checkOutButton.addEventListener('click', function () {
        window.location.href = "/order";
    })
    homeButton.addEventListener('click', function () {
        window.location.href = "/";
    })
    document.addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
            event.preventDefault();
        }
    });
    removeButtons.forEach((btn) => {
        btn.addEventListener("click", function(){
            event.preventDefault();
            let itemId = this.dataset.id;
            let data = {"itemId": itemId};
            fetch("/cart", data, deleteItem, this);
        });})
    init2();
}

function fetch(url, data, callback, deleteButton) {
    $.post("/cart", data, function(response) {callback(deleteButton, response)});
}

function deleteItem(deleteButton, response){
    let row = deleteButton.closest(".form-row");
    row.remove();
    document.querySelector("#total").innerText = response;
}

window.onload = init;