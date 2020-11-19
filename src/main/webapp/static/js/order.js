let payButton = document.querySelector("#pay");
let firstName = document.querySelector("#first-name").value;
let lastName = document.querySelector("#last-name").value;
let eMail = document.querySelector("#email").value;
let phoneNumber = document.querySelector("#phone-number").value;
let country = document.querySelector("#billing-country").value;
let city = document.querySelector("#billing-city").value;
let zipCode = document.querySelector("#billing-zipcode").value;
let address = document.querySelector("#billing-address").value;
let shippingCountry = document.querySelector("#shipping-country").value;
let shippingCity = document.querySelector("#shipping-city").value;
let shippingZipCode = document.querySelector("#shipping-zipcodey").value;
let shippingAddress = document.querySelector("#shipping-address").value;

function init() {
    payButton.addEventListener("click", function(){
        let data = {"firstName": firstName,
                    "lastName" : lastName,
                    "eMail": eMail,
                    "phoneNumber": phoneNumber,
                    "country": country,
                    "city": city,
                    "zipCode": zipCode,
                    "address": address,
                    "shippingCountry": shippingCountry,
                    "shippingCity": shippingCity,
                    "shippingZipCode": shippingZipCode,
                    "shippingAddress": shippingAddress};
        addToCart(data);
    });
}

function addToCart(data) {
    $.post("/order", data);
}


window.onload = init;