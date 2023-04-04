"use strict";

document.addEventListener('DOMContentLoaded', init);

function init() {
    const character = localStorage.getItem("character");
    const info = document.querySelector("main");


        const newDiv = document.createElement("div");
        newDiv.setAttribute("class", "characterBackground");
        const nameHeader = document.createElement("h2");
        nameHeader.innerText = character.charAt(0).toUpperCase() + character.slice(1);
        const pawnDescription=pawns[character].description;
        const newParagrave=document.createElement("p");
        newParagrave.innerText=pawnDescription;
        const pawnImage = document.createElement("img");
        pawnImage.setAttribute("src", "assets/media/ranks/" + pawns[character].img);
        pawnImage.setAttribute("class", "characterImage");
        newDiv.insertAdjacentElement("afterbegin", nameHeader);
        newDiv.insertAdjacentElement("beforeend", pawnImage);
        newDiv.insertAdjacentElement("beforeend", newParagrave);
        info.insertAdjacentElement("beforeend", newDiv);



}
