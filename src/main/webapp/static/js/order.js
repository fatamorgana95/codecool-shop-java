let payButton = document.querySelector("#pay");
let backButton = document.querySelector("#back");
let firstName = document.querySelector("#first-name");
let lastName = document.querySelector("#last-name");
let eMail = document.querySelector("#email");
let phoneNumber = document.querySelector("#phone-number");
let country = document.querySelector("#billing-country");
let city = document.querySelector("#billing-city");
let zipCode = document.querySelector("#billing-zipcode");
let address = document.querySelector("#billing-address");
let shippingCountry = document.querySelector("#shipping-country");
let shippingCity = document.querySelector("#shipping-city");
let shippingZipCode = document.querySelector("#shipping-zipcode");
let shippingAddress = document.querySelector("#shipping-address");
let formsList = [firstName, lastName, eMail, phoneNumber, country, city, zipCode, address, shippingCountry, shippingCity, shippingZipCode, shippingAddress];
let alertBar = document.getElementById("snackbar");


function init() {
    backButton.addEventListener("click", function () {
        window.location.href = "/cart";
    })
    payButton.addEventListener("click", function() {
        validation();
        if (validationIsCorrect()) {
            let data = {"firstName": firstName.value,
                "lastName" : lastName.value,
                "eMail": eMail.value,
                "phoneNumber": phoneNumber.value,
                "country": country.value,
                "city": city.value,
                "zipCode": zipCode.value,
                "address": address.value,
                "shippingCountry": shippingCountry.value,
                "shippingCity": shippingCity.value,
                "shippingZipCode": shippingZipCode.value,
                "shippingAddress": shippingAddress.value};
            addToOrder(data);
            window.location.href = "/cart";
        }
        else {
            alertBar.className = "show";
            setTimeout(function(){ alertBar.className = alertBar.className.replace("show", ""); }, 3000);
        }

    });
}



function addToOrder(data) {
    $.post("/order", data);
}

function validationIsCorrect() {
    let trueCounter = 0;

    for (let i=0; i < formsList.length; i++) {
        if (formsList[i].classList.contains("is-valid")) {
            trueCounter += 1;
        }
    }
    return trueCounter === formsList.length;
}

function validation() {
    for (let i=0; i < formsList.length; i++) {
        formsList[i].classList.remove("is-invalid");
        formsList[i].classList.remove("is-valid");
        if (formsList[i] === firstName || formsList[i] === lastName || formsList[i] === country || formsList[i] === city || formsList[i] === shippingCity || formsList[i] === shippingCountry) {
            textValidation(formsList[i]);
        }
        else if (formsList[i] === eMail) {
            emailValidation(formsList[i]);
        }
        else if (formsList[i] === phoneNumber) {
            phoneNumberValidation(formsList[i]);
        }
        else if (formsList[i] === zipCode || formsList[i] === shippingZipCode) {
            zipCodeValidation(formsList[i]);
        }
        else {
            addressValidation(formsList[i]);
        }
    }
}

function textValidation(form) {
    if (form.value !== null && /^[a-zA-Z]+$/.test(form.value)) {
        form.classList.add("is-valid");
    }
    else {
        form.classList.add("is-invalid")
    }
}

function phoneNumberValidation(form) {
    if (form.value !== null && form.value.match(/^\+?([0-9]{2})\)?[-. ]?([0-9]{2})[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/)) {
        form.classList.add("is-valid");
    }
    else {
        form.classList.add("is-invalid");
    }
}

function emailValidation(form) {
    if (form.value !== null && /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/.test(form.value)) {
        form.classList.add("is-valid");
    }
    else {
        form.classList.add("is-invalid");
    }
}

function zipCodeValidation(form) {
    if (form.value !== null && form.value.match(/^([0-9]{4})$/)) {
        form.classList.add("is-valid");
    } else {
        form.classList.add("is-invalid");
    }
}

function addressValidation(form) {
        if (form.value !== null && form.value.length > 2) {
            form.classList.add("is-valid");
        }
        else {
            form.classList.add("is-invalid");
        }
    }


window.onload = init;