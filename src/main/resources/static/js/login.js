'use strict';

const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const infoBox = document.querySelector("#info-box");
if (urlParams.has("error")) {
    infoBox.classList.add("alert-danger");
    infoBox.innerText = "Your details were incorrect"
    infoBox.style.display = "block";
}

if (urlParams.has("logout")) {
    infoBox.classList.add("alert-info");
    infoBox.innerText = "Logout sucessful";
    infoBox.style.display = "block";
}

if (urlParams.has("signup")) {
    infoBox.classList.add("alert-success");
    infoBox.innerText = "User created";
    infoBox.style.display = "block";
}
