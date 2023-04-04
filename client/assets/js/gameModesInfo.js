"use strict";

document.addEventListener('DOMContentLoaded',init);

function init(){
    const gamemode=localStorage.getItem("gamemodeInfo").charAt(0).toUpperCase() + localStorage.getItem("gamemodeInfo").slice(1);
    const info = document.querySelector("main");
    const newDiv = document.createElement("div");
    newDiv.setAttribute("class", "info");
    const nameHeader = document.createElement("h2");
    nameHeader.innerText = gamemode;
    const pawnDescription=descriptions[gamemode].description;
    const newParagrave=document.createElement("p");
    newParagrave.innerText=pawnDescription;
    newDiv.insertAdjacentElement("afterbegin", nameHeader);
    newDiv.insertAdjacentElement("beforeend", newParagrave);
    info.insertAdjacentElement("beforeend", newDiv);
}
