let quantityInputs = document.querySelectorAll("#quantity-input");


function init() {
    document.addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
            event.preventDefault();
        }
    });
    quantityInputs.forEach((inputField) => {
        inputField.addEventListener("change", function(){
            let itemId = this.dataset.id;
            let quantity = this.value;
            let data = {"itemId": itemId, "quantity": quantity};
            fetch("/cart/change", data, changeQuantity, this);
        });})
}


function fetch(url, data, callback, quantityInput) {
    $.post("/cart/change", data, function(response) {callback(quantityInput, response)});
}


function changeQuantity(quantityInput, response){
    let row = quantityInput.closest(".form-row");
    row.querySelector("#subtotal").value = response["subtotalPrice"];
    document.querySelector("#total").value = response["totalPrice"];
}

window.onload = init;