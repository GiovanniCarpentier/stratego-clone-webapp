function setupboard(){
    makePlayBoard();
    makeSelectionFooter();
    initSidePanel("Friendly");
    initSidePanel("Enemy");
}

function initSidePanel(side){
    const place = document.querySelector(`#taken${side}Pawns`);

    for(const pawn in pawns) {
        if(piecesCurrentVersion.hasOwnProperty(pawn)) {
            const row = document.createElement("div");
            row.setAttribute("class", "takenPawnRow");
            const pictureDiv = document.createElement("div");
            pictureDiv.setAttribute("class","sidePanelPicHolder");
            const picture = document.createElement("img");
            picture.setAttribute("src",filePath + pawns[pawn].img);
            picture.setAttribute("class","sidePanelPic");
            pictureDiv.insertAdjacentElement("beforeend",picture);
            row.insertAdjacentElement("beforeend",pictureDiv);
            const amountTakenDiv = document.createElement("div");
            amountTakenDiv.setAttribute("class","amountTakenHolder");
            const amountTaken = document.createElement("p");
            amountTaken.setAttribute("id",`amount${side}${pawn}`);
            amountTaken.innerText = "0";
            amountTakenDiv.insertAdjacentElement("beforeend" , amountTaken);
            row.insertAdjacentElement("beforeend",amountTakenDiv);
            place.insertAdjacentElement("beforeend", row);

        }
    }
}

function changeSidePanelAmount(pawn,side){
    let idName;
    if(side === 0){
        idName = "amountFriendly" + pawn;
    } else if(side === 1){
        idName = "amountEnemy" + pawn;
    }
    const currentAmount = parseInt(document.querySelector(`#${idName}`).innerText);
    document.querySelector(`#${idName}`).innerText = currentAmount + 1;
}

function allPawnsPlaced(){
    for(const key in piecesCurrentAmount){
        if(piecesCurrentAmount.hasOwnProperty(key) && piecesCurrentAmount[key] > 0){
            return false;
        }
    }
    return true;
}

function startGame(){
    const roomid = document.querySelector("#roomid").value;
    if(roomid && allPawnsPlaced()) {
        sendConfigToApi(pawnLocation, roomid, localStorage.getItem("gamemodeInText"));
        gameStarted=true;
        const footer = document.querySelector("#pawn-footer").children;
        for (let i = 0; i < footer.length; i++) {
            const div = footer[i];
            div.classList.remove('active');
        }
        stage="waiting";
        document.querySelector("#start").style.display ="none";
        checkEasterEggs(roomid);
    }
}

function sendConfigToApi(configuration, roomId, gameversion){
    fetchFromServer(`${config.getAPIUrl()}/games/rooms/${roomId}/configuration`, 'POST',
        {
            version: gameversion,
            startConfiguration: configuration
        }
    ).then(gameInfo => {
        _gameConfig = gameInfo;
        currentSide = _gameConfig.player;
        waitForPlayer();
    });
}

function waitForPlayer(){
    fetchFromServer(`${config.getAPIUrl()}/games/${_gameConfig.gameId}/moves`,'GET').then(moveList => {
        currentMoveList = moveList.moves;
        if(moveList.moves.length < 2){
            document.querySelector("#showTurn").innerHTML = "Waiting for players";
            setTimeout(waitForPlayer, 1000);
        } else {
            console.log("Players connected and ready");
            document.querySelector("#end").style.display = 'inline';
            document.querySelector("#warCry").style.display = 'inline';
            updatePlayerBoard();
            stage = "playing";
            document.querySelector("form").style.display = "none";
            checkYourTurn();
        }
    });
}

function waitForEnemyMove(){
    fetchFromServer(`${config.getAPIUrl()}/games/${_gameConfig.gameId}/moves`,'GET').then(newMoveList => {
        if(newMoveList.moves.length > currentMoveList.length){
            currentMoveList = newMoveList.moves;
            updatePlayerBoard();
            checkYourTurn();
        } else {
            setTimeout(waitForEnemyMove,1000);
        }
    });
}

function loadPionen(){
    //load gamemode
    const gamemode = localStorage.getItem("gamemode");
    return fetchFromServer(`${config.getAPIUrl()}/versions`,'GET')
        .then(response => JSON.stringify(response))
        .then(versions => {
            return fetchFromServer(`${config.getAPIUrl()}/versions/${JSON.parse(versions)['versions'][gamemode]}`,'GET',).then(response => {
                return response.pieceCount;
            });
        });
}

function updateBoard(){
    for(let y = 0; y < config.defaultBoardSize.rows; y++){
        for(let x = 0; x < config.defaultBoardSize.cols; x++){
            const location = `${x}X${y}`;
            const placeInBoard = document.querySelector(`[id="${location}"]`);
            const pawnName = pawnLocation[y][x];
            if(pawnName !== null){
                placeInBoard.className = "friendly " + pawnName;
                placeInBoard.innerHTML = "";
                const newImg = document.createElement("img");
                newImg.setAttribute("src", filePath + pawns[pawnName].img);
                newImg.setAttribute("class", "boardPicture");
                placeInBoard.insertAdjacentElement("beforeend", newImg);
            } else {
                placeInBoard.className = "";
                placeInBoard.innerHTML = "";
            }
        }
    }

}

function makePlayBoard(){
    const board = document.querySelector("#board");
    for(let i = 0; i < config.defaultBoardSize.rows; i++){
        for(let j = 0; j < config.defaultBoardSize.cols; j++){
            const newDiv = document.createElement("div");
            newDiv.setAttribute("id",j+"X"+i);
            newDiv.addEventListener("click",boardClickEvent);
            board.insertAdjacentElement("beforeend", newDiv);
        }
    }
}

function updatePlayerBoard(){
    fetchFromServer(`${config.getAPIUrl()}/games/${_gameConfig.gameId}/configuration`,'GET').then(boardSetup => {
        for(let y = 0; y < 10; y++){
            for(let x = 0; x < 10; x++){
                const currentDiv = document.querySelector(`[id="${x}X${y}"]`);
                if(currentDiv.hasChildNodes()){
                    currentDiv.removeChild(currentDiv.childNodes[0]);
                }
                if(boardSetup.configuration[y][x] === null){
                    currentDiv.className = "";
                } else if (boardSetup.configuration[y][x].rank) {
                    const pawnName = boardSetup.configuration[y][x].rank;
                    currentDiv.className = "friendly " + pawnName;
                    currentDiv.innerHTML = "";
                    const newImg = document.createElement("img");
                    newImg.setAttribute("src", filePath + pawns[pawnName].img);
                    newImg.setAttribute("class", "boardPicture");
                    currentDiv.insertAdjacentElement("beforeend", newImg);
                } else {
                    currentDiv.className = "enemy";
                }
            }
        }
    });
}

function makeSelectionFooter(){
    const footer = document.querySelector("#pawn-footer");
    const amountPieces = Object.keys(piecesCurrentVersion).length;
    //change col amount to amount of pieces for current version
    footer.style.setProperty('grid-template-columns', `repeat(${amountPieces}, 1fr)`);
    for(const key in pawns){
        if(pawns.hasOwnProperty(key) && piecesCurrentVersion.hasOwnProperty(key)) {
            const newDiv = document.createElement("div");
            if (stage === "conf"){
                newDiv.addEventListener("click", function (e) {
                    selectPawn(e, key);
                });
            }
            newDiv.setAttribute("id",key);
            newDiv.setAttribute("class","pawn");
            const nameParagraph = document.createElement("p");
            nameParagraph.innerText = key;
            const pawnImage = document.createElement("img");
            pawnImage.setAttribute("src",filePath + pawns[key].img);
            pawnImage.setAttribute("class","footer-img");
            const amountText = document.createElement("p");
            amountText.innerText = piecesCurrentVersion[key];
            amountText.setAttribute("id",`amount${key}`);
            const hoverText = document.createElement("span");
            hoverText.innerText = pawns[key].description;
            hoverText.setAttribute("class",`${key}`);
            newDiv.insertAdjacentElement("beforeend",nameParagraph);
            newDiv.insertAdjacentElement("beforeend",pawnImage);
            if (stage === "conf"){
                newDiv.insertAdjacentElement("beforeend",amountText);
            }
            newDiv.insertAdjacentElement("beforeend",hoverText);
            footer.insertAdjacentElement("beforeend", newDiv);
        }

    }
}

//makes board in memory
function makePawnBoard(){
    pawnLocation = Array.from({length: config.defaultBoardSize.rows});
    for(let i = 0; i < config.defaultBoardSize.rows; i++){
        pawnLocation[i] = Array.from({length: config.defaultBoardSize.cols}).fill(null);
    }
}
