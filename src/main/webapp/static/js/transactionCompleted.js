function init() {
    let homeButton = document.querySelector("#homepage");
    homeButton.addEventListener("click", function () {
        window.location.href = "/";
    })
}


window.onload = init;