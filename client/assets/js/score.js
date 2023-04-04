"use strict";

document.addEventListener('DOMContentLoaded', init);

function init() {
    let winLose;
    const wins =+  localStorage.getItem("wins");
    const losses =+ localStorage.getItem("losses");
    const winHeader = document.querySelector("#winHeader");
    const lossHeader = document.querySelector("#lossHeader");
    const totalHeader = document.querySelector("#totalHeader");
    const ratioHeader = document.querySelector("#ratioHeader");

    winHeader.innerText = "You have won: " + wins;
    lossHeader.innerText = "You have lost: " + losses;
    const total = wins+losses;

    if (total===0){
        winLose=100;
    }else{
        winLose=Math.round((wins/(total)) * 100);
    }
    totalHeader.innerText = `You have played a total of ${total} games.`;
    ratioHeader.innerText = `Your win/loss ratio is ${winLose}%`;
}
