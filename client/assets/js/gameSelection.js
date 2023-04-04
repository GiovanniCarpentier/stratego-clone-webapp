"use strict";

document.addEventListener('DOMContentLoaded',init);

function init(){
    gameSelection();
}

function gameSelection(){
    fetchFromServer(`${config.getAPIUrl()}/versions`,'GET')
        .then(response =>{
            const pol = document.querySelector("#gameSelection");
            for (let i= response.versions.length-1; i>= 0;i--){
                const link = document.createElement("a");
                link.setAttribute("href","boardgame.html");
                const tekst = document.createElement("h2");
                tekst.innerText = `Stratego ${response.versions[i]}`;
                link.addEventListener("click",function(e) {saveMode(e,i,response.versions[i]);});
                link.insertAdjacentElement("beforeend",tekst);
                pol.insertAdjacentElement("afterbegin", link);
            }
        });
}

function saveMode(e,mode, modeInText){
    e.preventDefault();
    let clickedElement;

    //makes sure link is selected
    if(e.target.tagName === "H2"){
        clickedElement = e.target.parentNode;
    }else {
        clickedElement = e.target;
    }
    localStorage.setItem("gamemode",mode);
    localStorage.setItem("gamemodeInText",modeInText);
    //go to page after saving
    window.location = clickedElement.href;

}
