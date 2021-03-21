let cardNumberInput;
let cardHolderInput;
let expiryDateInput;
let codeInput;
let eMailInput;
let password;
let creditCard = document.querySelector("#credit-card");
let payPal = document.querySelector("#paypal");
let validationForm = document.querySelector(".needs-validation");
let creditCardList;
let payPalCardList;
let alert2Bar;
let buttons;





function init() {

    creditCard.addEventListener("change", function() {showPaymentInputs(this);});
    payPal.addEventListener("change", function() {
        cardHolderInput = null;
        showPaymentInputs(this);
    });
    createButtons();
}

function createButtons(){
    buttons = document.createElement('div');
    buttons.innerHTML = `                <div class="form-row">
                    <div class="col-md-7,5 m-1 form-header">
                        <div class="modal-footer">
                            <button type="button" style="width:100px;" class="small-button" id="back" data-dismiss="back">Cancel</button>
                            <button type="button" style="width:100px;" class="small-button" id="confirmation">Pay</button>
                            <div id="snackbar">Invalid inputs, please try again.</div>
                        </div>
                    </div>
                </div>`;
    buttons.style.display = "none";
    validationForm.appendChild(buttons);
}

function showPaymentInputs(paymentType) {

    buttons.style.display = "block";
    if (paymentType.value === "credit-card"){
        validationForm.querySelector("#paypali").style.display = "none";
        validationForm.querySelector("#credit").style.display = "block";
        cardNumberInput = document.querySelector("#card-number");
        cardHolderInput = document.querySelector("#card-holder");
        expiryDateInput = document.querySelector("#expiry-date");
        codeInput = document.querySelector("#code");
        creditCardList = [cardNumberInput, cardHolderInput, expiryDateInput, codeInput];
    }
    else {
        validationForm.querySelector("#credit").style.display = "none";
        validationForm.querySelector("#paypali").style.display = "block";
        eMailInput = document.querySelector("#emailke");
        password = document.querySelector("#password");
        payPalCardList = [eMailInput, password];
    }
    document.querySelector("#back").addEventListener("click", function () {
        window.location.href = "/";
    })
    document.querySelector("#confirmation").addEventListener("click", function() {
        let formList = (cardHolderInput !== null)? creditCardList : payPalCardList;
        validation(formList);
        if (validationIsCorrect(formList)) {
            let isCompleted = (Math.floor(Math.random() * 10) < 2)? "error" : "completed";
            window.location.href = "/transaction?transaction=" + isCompleted;
        }
        else {
            alert2Bar = document.getElementById("snackbar");
            alert2Bar.className = "show";
            setTimeout(function(){ alert2Bar.className = alert2Bar.className.replace("show", ""); }, 3000);
        }

    });
}


function validationIsCorrect(formList) {
    let trueCounter = 0;

    for (let i=0; i < formList.length; i++) {
        if (formList[i].classList.contains("is-valid")) {
            trueCounter += 1;
        }
    }
    return trueCounter === formList.length;
}

function validation(formList) {
    for (let i=0; i < formList.length; i++) {
        formList[i].classList.remove("is-invalid");
        formList[i].classList.remove("is-valid");

        if (formList[i] === cardHolderInput || formList[i] === password) {
            textValidation(formList[i]);
        }
        else if (formList[i] === eMailInput) {
            emailValidation(formList[i]);
        }
        else if (formList[i] === cardNumberInput) {
            cardNumberValidation(formList[i]);
        }
        else if (formList[i] === codeInput) {
            codeValidation(formList[i]);
        }
        else {
            expiryDateValidation(formList[i]);
        }
    }
}


function cardNumberValidation(form) {
    if (form.value !== null && form.value.match(/^(([0-9]{4})[ ]?([0-9]{4})[ ]?([0-9]{4})[ ]?([0-9]{4}))$/)) {
        form.classList.add("is-valid");
    }
    else {
        form.classList.add("is-invalid");
    }
}

function expiryDateValidation(form) {
    if (form.value !== null && form.value.match(/^(([0-9]{2})[/. ]?([0-9]{2}))$/)) {
        form.classList.add("is-valid");
    }
    else {
        form.classList.add("is-invalid");
    }
}


function codeValidation(form) {
    if (form.value !== null && form.value.match(/^([0-9]{3})$/)) {
        form.classList.add("is-valid");
    } else {
        form.classList.add("is-invalid");
    }
}



window.onload = init;