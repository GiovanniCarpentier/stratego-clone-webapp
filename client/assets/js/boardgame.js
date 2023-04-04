"use strict";

document.addEventListener("DOMContentLoaded",init);
let selected = "";
let pawnLocation;
//conf & playing
let stage = "conf";
let piecesCurrentVersion;
let piecesCurrentAmount;
let _gameConfig;
let currentSide = "blue";
let currentMoveList;
let currentSelectedPawnOnBoard = null;
let gameStarted=false;
const filePath = "assets/media/ranks/";
async function init(){
    document.querySelector("#start").addEventListener("click",startGame);
    document.querySelector("#end").addEventListener("click",endGame);
    document.querySelector("#warCry").addEventListener("click",playWarCry);
    piecesCurrentVersion = await loadPionen();
    //duplicate not reference
    piecesCurrentAmount = JSON.parse(JSON.stringify(piecesCurrentVersion));
    setupboard();
    makePawnBoard();
    setInfiltrator(piecesCurrentVersion);
}


// surender knop
function endGame(){
    if (_gameConfig.player === "blue"){
        winScreen("red");// geeft het tegengestelde mee aan winscreen zodat je verliest
    } else {
        winScreen("blue");
    }
}

// kijk aan wie zijn beurt het is
function checkYourTurn(){
    fetchFromServer(`${config.getAPIUrl()}/games/${_gameConfig.gameId}/moves`,'GET').then(moveList => {
        currentMoveList = moveList.moves;
        const lastPlayerPlayed = currentMoveList[currentMoveList.length - 1].player;
        const moveText = document.querySelector("#showTurn");
        parseAttack();
        if(lastPlayerPlayed === currentSide){
            moveText.innerText = "Enemy Turn";
            waitForEnemyMove();
        }else {
            moveText.innerText = "Your Turn";
        }
    });

}

function boardClickEvent(e){
    let lastPlayerMoved;
    if(currentMoveList) {
        lastPlayerMoved = currentMoveList[currentMoveList.length - 1].player;
    }
    if(stage === "conf"){
        placePawn(e);
    } else if (stage === "playing" && currentSide !== lastPlayerMoved){
        const infiltratorSelect = document.getElementById("infiltratorSelect");
        if(e.target.parentNode.className === "friendly infiltrator"){
            infiltratorSelect.hidden = false;
            moveInfiltrator(e);
        }
        else{
            infiltratorSelect.hidden = true;
            movePawn(e);
        }
    }
}

function movePawn(e){

    let clickedDiv;

    //makes sure div is selected
    if(e.target.tagName === "DIV") {
        clickedDiv = e.target;
    } else {
        clickedDiv = e.target.parentNode;
    }

    const location = clickedDiv.getAttribute("id").split("X");
    const tRow = location[1], tCol = location[0];
    //checks if no start pawn is selected and if you're pressing on a pawn
    if(clickedDiv.classList[0] === "friendly"){
        currentSelectedPawnOnBoard = location;
    //deselect if clicked on same pawn
    } else if(JSON.stringify(currentSelectedPawnOnBoard) === JSON.stringify(location)){
        currentSelectedPawnOnBoard = null;
    //TODO check if clicked on enemy pawn
    } else if(currentSelectedPawnOnBoard !== null){
        const sRow = currentSelectedPawnOnBoard[1], sCol = currentSelectedPawnOnBoard[0];
        console.log(sRow,sCol,tRow,tCol);
        fetchFromServer(`${config.getAPIUrl()}/games/${_gameConfig.gameId}/moves`,'POST', {
                "src": {
                    "row": parseInt(sRow),
                    "col": parseInt(sCol)
                },
                "tar": {
                    "row": parseInt(tRow),
                    "col": parseInt(tCol)
                },
            }
    ).then(response => {
        if(response.failure === 409){
            const moveText = document.querySelector("#errors");
            moveText.innerText = response.cause;
        }
        else{
            const moveText = document.querySelector("#errors");
            moveText.innerText = "";
        }
        updatePlayerBoard();
        checkYourTurn();
    } );
    }
}

//dit moet opgeslagen worden
function parseAttack(){
    const move = currentMoveList[currentMoveList.length - 1];
    if(move.hasOwnProperty("attacker")){
        const attackerPawn = move.attacker;
        const defenderPawn = move.defender;
        const winnerSide = move.winner;
        const playerPlayed = move.player;
        let loserPawn;
        let losingSide;

        if(defenderPawn === "flag"){
            winScreen(playerPlayed);
        }

        if(winnerSide === "defender"){
            loserPawn = attackerPawn;
            if(currentSide === playerPlayed){
                losingSide = 0;
            } else {
                losingSide = 1;
            }
        }else {
            loserPawn = defenderPawn;
            if(currentSide === playerPlayed){
                losingSide = 1;
            } else {
                losingSide = 0;
            }
        }

        if(winnerSide === "draw"){
            changeSidePanelAmount(attackerPawn,0);
            changeSidePanelAmount(attackerPawn,1);
        }else{
            changeSidePanelAmount(loserPawn,losingSide);
        }

    }

}
if (localStorage.getItem("wins") === "undefined"){
    localStorage.setItem('wins',parseInt(0));
    localStorage.setItem('losses',parseInt(0));
}
function winScreen(playerPlayed){
    if (playerPlayed === _gameConfig.player){
        localStorage.setItem('wins', Number(localStorage.getItem("wins"))+1);
        createPage("WINNER");
    } else {
        localStorage.setItem('losses',Number(localStorage.getItem("losses"))+1);
        createPage("LOSER");
    }
}

function createPage(text){
    document.querySelector("body").innerHTML = "";
    const body = document.querySelector("body");
    body.insertAdjacentHTML("afterbegin",
    `<header>
        <h1>Stratego</h1>
    </header>
    <main>
        <h2 id="final">${text}</h2>
        <a href="index.html"><h4>Return to menu</h4></a>
    </main>
    `);
}

function placePawn(e){
    e.preventDefault();

    let clickedDiv;

    //makes sure div is selected
    if(e.target.tagName === "DIV") {
        clickedDiv = e.target;
    } else {
        clickedDiv = e.target.parentNode;
    }

    const location = clickedDiv.getAttribute("id").split("X");
    //get pawn on location u clicked on the board
    const pawnOnLocation = clickedDiv.classList[1];
    const x = location[0], y = location[1];

    //check if pawn is selected and if you're in configuration mode and if there are still pawns left
    if (selected !== "" && stage === "conf" && y >= 6 && piecesCurrentAmount[selected] > 0) {
        const amountPawnText = document.querySelector(`#amount${selected}`);
        pawnLocation[y][x] = selected;
        piecesCurrentAmount[selected] -= 1;
        amountPawnText.innerText = piecesCurrentAmount[selected];
    } else if(stage === "conf" && y >= 6) {
        pawnLocation[y][x] = null;
    }
    //subtracts if pawn is changed or removed on the board
    if(pawnOnLocation) {
        const amountPawnText = document.querySelector(`#amount${pawnOnLocation}`);
        piecesCurrentAmount[pawnOnLocation] += 1;
        amountPawnText.innerText = piecesCurrentAmount[pawnOnLocation];
    }
    updateBoard();
}

function selectPawn(e,pawnName) {
    let clickedDiv;
    if (!gameStarted) {
        //makes sure div is selected
        if (e.target.tagName === "DIV") {
            clickedDiv = e.target;
        } else {
            clickedDiv = e.target.parentNode;
        }

        //makes not active back to gray background
        const footer = document.querySelector("#pawn-footer").children;
        for (let i = 0; i < footer.length; i++) {
            const div = footer[i];
            div.classList.remove('active');
        }
            //if nothing selected clear place
            if (selected === pawnName) {
                selected = "";
            } else if (!gameStarted){
                selected = pawnName;
                clickedDiv.classList.add('active');
            }
        }
}
