'use strict';

const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const infoBox = document.querySelector("#info-box");
if (urlParams.has("incorrect")) {
    infoBox.classList.add("alert-danger");
    infoBox.innerText = "The passwords don't match"
    infoBox.style.display = "block";
}

const sendDetails = () => {
    let username = document.querySelector("#UName");
    let password = document.querySelector("#Password");
    let cpassword = document.querySelector("#CPassword");
    if (password.value === cpassword.value) {
        window.location.href = `perform_signup?username=${username.value}&password=${password.value}`;
    } else {
        window.location.href = "signup.html?incorrect=true";
    }
};
