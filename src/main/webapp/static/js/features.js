
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
    if (products.length === 0){
        card += `<div class="container">
                    <i id="frown-icon" class="fa fa-frown-o fa-5x"></i><br>
                    <text>   There are no dragons under these filters.</text>
                </div>`
    }else{
    for (let prod of products) {
        card +=
            `<div id="cardContainer" class="col-md-6 col-lg-4 wow bounceInUp" data-aos="zoom-in" data-aos-delay="100">
                    <div class="box">
                        
                        <img class="img" src="/static/images/${prod.image}" alt="" />
                        <br>
                        <h4 class="title" >${prod.name}</h4>
                        <p class="description" >${prod.description}</p>
                        <br>
                        <strong><p class="description">${prod.defaultPrice} ${prod.defaultCurrency}</p></strong>
                        <br>
                        <button class="small-button" style="width:100px;" id="add-to-cart-button" data-id="${prod.id}">Add to cart</button>
                    </div>
                    </div>`
    }}
    container.innerHTML = card;
    init();
}

window.onload = init;