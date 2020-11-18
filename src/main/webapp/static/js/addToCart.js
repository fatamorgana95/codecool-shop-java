let addToCartButtons = document.querySelectorAll("#add-to-cart-button");


function init() {
    addToCartButtons.forEach( (btn) =>{
        btn.addEventListener("click", function(){
        let productId = this.dataset.id;
        let data = {"productId": productId};
        addToCart("/", data, refreshAddedProductsQuantity);
    })});
}

function addToCart(url, data, callback) {
    $.post("/", data, function(response) {callback(JSON.parse(response))});
}

function refreshAddedProductsQuantity(response){
    // need filter(?)
    let quantityElement = document.querySelector("#added-products-quantity");
    quantityElement.innerText = response;
}

window.onload = init;