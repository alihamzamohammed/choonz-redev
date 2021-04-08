'use strict';

let getLoginInfo = async () => {
    const response = await fetch("username");
    if (response.status != 200) {
        console.error(`Error: Status code ${response.status}\n${response.json}`);
    }
    let data = await response.text();
    return data;
}

getLoginInfo().then((data) => {
    let userindication1 = document.querySelector("#userindication1");
    let userindication2 = document.querySelector("#userindication2");
    let navlink1 = document.createElement("a");
    let navlink2 = document.createElement("a");
    navlink1.className = "nav-link";
    navlink2.className = "nav-link";
    if (data === "anonymousUser") {
        navlink1.innerText = "Login";
        navlink1.href = "login.html";
        navlink2.innerText = "Sign up";
        navlink2.href = "signup.html";
    } else {
        navlink1.innerText = data;
        navlink1.href = "#userPage";
        navlink2.innerText = "Sign out";
        navlink2.href = "perform_logout";
    }
    userindication1.appendChild(navlink1);
    userindication2.appendChild(navlink2);
})

