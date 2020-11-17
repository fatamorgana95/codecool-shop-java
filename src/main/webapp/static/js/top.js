let navButtons = document.querySelectorAll("[id$='Nav']");
let pageNumber = 1;
let navType = "top";

window.onload =

    function init() {
    getDataForMainPage();
    navButtons.forEach(nav => {
        nav.addEventListener('click', getData);
    })

}


// function getTopNews(){
//     let xhr = new XMLHttpRequest();
//     xhr.open('GET', 'api/top', true);
//
//     // If specified, responseType must be empty string or "text"
//     xhr.responseType = 'text';
//
//     xhr.onload = function () {
//         if (xhr.readyState === xhr.DONE) {
//             if (xhr.status === 200) {
//                 let row = document.querySelector('.row');
//                 let box = '';
//                 for (let news of xhr.responseText) {
//                     box +=
//                         `<div class="col-md-6 col-lg-4 wow bounceInUp" data-aos="zoom-in" data-aos-delay="100">
//                                 <div class="box">
//                                   <div class="icon" style="background: #fceef3;"><i class="ion-ios-analytics-outline" style="color: #ff689b;"></i></div>
//                                   <h4 class="title">${news.title}</h4>
//                                   <p class="description">${news.time_ago}</p>
//                                   <p class="description">${news.user}</p>
//                                 </div>
//                               </div>`
//                 }
//                 row.innerHTML = box;
//             }
//         }
//     };
//
//     xhr.send(null);
// }

function getDataForMainPage() {
    fetch('api/top')
        .then(response => response.json())
        .then((Response) => showData(Response))
}

function getData() {
    navType = this.dataset.navtype;
    pageNumber = this.dataset.pagenumber;
    let page = "?page=" + pageNumber
    let endpoint = 'api/' + navType + page;
    fetch(endpoint)
        .then(response => response.json())
        .then((Response) => showData(Response))

}

function showData(response) {
    if (navType !== "jobs") {
        showNewsData(response);
    } else {
        showJobsData(response);
    }

}

function showNewsData(response) {
    showNextAndPreviousButtons();
    let row = document.querySelector('.row');
    let box = '';
    for (let news of response) {
        box +=
            `<div class="col-md-6 col-lg-4 wow bounceInUp" data-aos="zoom-in" data-aos-delay="100">
                                    <div class="box">
                                      <div class="icon" style="background: #fceef3;"><i class="ion-ios-analytics-outline" style="color: #ff689b;"></i></div>
                                      <h4 class="title">${news.title}</h4>
                                      <p class="description">${news.time_ago}</p>
                                        <p class="description">${news.user}</p>
                                    </div>
                                  </div>`
    }
    row.innerHTML = box;
}

function showJobsData(response) {

    clearNextAndPreviousButtons();

    let row = document.querySelector('.row');
    let box = '';
    for (let job of response) {
        box +=
            `<div class="col-md-6 col-lg-4 wow bounceInUp" data-aos="zoom-in" data-aos-delay="100">
                                    <div class="box">
                                      <div class="icon" style="background: #fceef3;"><i class="ion-ios-analytics-outline" style="color: #ff689b;"></i></div>
                                      <h4 class="title">${job.title}</h4>
                                      <p class="description">${job.time_ago}</p>
                                    </div>
                                  </div>`
    }
    row.innerHTML = box;
}

function showNextAndPreviousButtons() {
    clearNextAndPreviousButtons();

    let rowHeader = document.querySelector('#rowHeader');
    let next = parseInt(pageNumber) + 1;
    let previous = parseInt(pageNumber) - 1;

    let previousButton = `<a id ="previousButton" class="btn" data-navType=${navType} data-pageNumber="${previous}" >Previous</a>`;
    let nextButton = `<a id ="nextButton" class="btn" data-navType=${navType} data-pageNumber="${next}" style="float: right;">Next</a>`;
    rowHeader.innerHTML += previousButton;
    rowHeader.innerHTML += nextButton;

    document.querySelector('#previousButton').addEventListener('click', getData);
    document.querySelector('#nextButton').addEventListener('click', getData);
}

function clearNextAndPreviousButtons() {
    if (document.querySelector('#previousButton') !== null) {
        document.querySelector('#previousButton').remove();
        document.querySelector('#nextButton').remove();
    }
}