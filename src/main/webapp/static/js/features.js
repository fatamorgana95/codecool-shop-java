
function init() {
    addEventListeners();
    let addToCartButtons = document.querySelectorAll("#add-to-cart-button");
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


function addEventListeners () {
    let categories = document.getElementById("category-scroll");
    categories.addEventListener("change", function () {filterBy(this.value, suppliers.value);})

    let suppliers = document.getElementById("supplier-scroll");
    suppliers.addEventListener("change", function () {filterBy(categories.value, this.value);})
}

function filterBy(category, supplier) {
    fetch(`/filter/?category=${category}&supplier=${supplier}`)
        .then(response => response.json())
        .then((Response) => showFilteredProducts(Response))
}


function showFilteredProducts(products) {
    let container = document.querySelector(".row");
    let card = '';
    for (let prod of products) {
        card +=
            `<div id="cardContainer" class="col-md-6 col-lg-4 wow bounceInUp" data-aos="zoom-in" data-aos-delay="100">
                    <div class="box">
                        <div class="icon" style="background: #fceef3;"><i class="ion-ios-analytics-outline" style="color: #ff689b;"></i></div>
                        <h4 class="title" >${prod.name}</h4>
                        <img class="img" src="/static/images/${prod.image}" alt="" />
                        <p class="description" >${prod.description}</p>
                        <strong><p class="description">${prod.defaultPrice} ${prod.defaultCurrency}</p></strong>
                        <button class="btn" id="add-to-cart-button" data-id="${prod.id}">Add to cart</button>
                    </div>
                    </div>`
    }
    container.innerHTML = card;
    init();
}

window.onload = init;