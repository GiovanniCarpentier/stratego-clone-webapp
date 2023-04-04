"use strict";

document.addEventListener('DOMContentLoaded',init);

function init(){
    fetchFromServer(`${config.getAPIUrl()}/versions`,'GET')
        .then(response =>{
            const main = document.querySelector("main");
            for (let i= response.versions.length-1; i>= 0;i--){
                const link = document.createElement("a");
                link.setAttribute("href","gamemodeInfo.html");
                const tekst = document.createElement("h2");
                tekst.innerText = `Stratego ${response.versions[i]}`;
                link.addEventListener("click",function(e) {saveMode(e, response.versions[i]);});
                link.insertAdjacentElement("beforeend",tekst);
                main.insertAdjacentElement("afterbegin", link);
            }
        });
}

function saveMode(e,mode){
    e.preventDefault();
    let clickedElement;

    //makes sure link is selected
    if(e.target.tagName === "H2"){
        clickedElement = e.target.parentNode;
    }else {
        clickedElement = e.target;
    }
    localStorage.setItem("gamemodeInfo",mode);
    //go to page after saving
    window.location = clickedElement.href;

}
