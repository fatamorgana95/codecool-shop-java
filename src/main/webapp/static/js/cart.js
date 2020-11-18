let removeButton = document.querySelector("#remove-button");


function init() {
    removeButton.addEventListener("click", function(){
            let itemId = this.dataset.id;
            let data = {"itemId": itemId};
            addToCart("/cart", data, deleteItem(), this);
        });
}

function addToCart(url, data, callback, deleteButton) {
    $.delete(url, data, function() {callback(deleteButton)});
}

function deleteItem(deleteButton){
    let row = deleteButton.closest(".form-row");
    row.remove();
}

window.onload = init;