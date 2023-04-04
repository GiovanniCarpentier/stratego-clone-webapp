"use strict";

document.addEventListener('DOMContentLoaded', init);

function init() {
    const footer = document.querySelector(".allCharacters");
    for (const key in pawns) {
        if (pawns.hasOwnProperty(key)) {
            const newDiv = document.createElement("div");
            newDiv.addEventListener("click", function (e) {
                test(e, key);
            });
            newDiv.setAttribute("id", key);
            newDiv.setAttribute("class", "characterBackground");
            const nameHeader = document.createElement("h2");
            nameHeader.innerText = key.charAt(0).toUpperCase() + key.slice(1);
            const pawnImage = document.createElement("img");
            pawnImage.setAttribute("src", "assets/media/ranks/" + pawns[key].img);
            pawnImage.setAttribute("class", "characterImage");
            newDiv.insertAdjacentElement("afterbegin", nameHeader);
            newDiv.insertAdjacentElement("beforeend", pawnImage);
            footer.insertAdjacentElement("beforeend", newDiv);
        }

    }
}

function test(e, key) {
    localStorage.setItem("character",key);
    window.location = "characterInfo.html";

}
