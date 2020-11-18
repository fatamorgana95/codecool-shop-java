function init() {
    addEventListeners();

    function addEventListeners () {
        let categories = document.getElementById("category-scroll");
        categories.addEventListener("change", function () {filterByCategory(this.value);})

        let suppliers = document.getElementById("supplier-scroll");
        suppliers.addEventListener("change", function () {filterBySupplier(this.value);})
    }

    function filterBySupplier(supplier) {
        fetch(`/suppliers/?supplier=${supplier}`)
            .then(response => response.json())
            .then((Response) => showFilteredProducts(Response))
    }

    function filterByCategory(category) {
        fetch(`/categories/?category=${category}`)
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
                        <a class="btn" href="#">Add to cart</a>
                    </div>
                    </div>`
        }
        container.innerHTML = card;
    }

}


init();