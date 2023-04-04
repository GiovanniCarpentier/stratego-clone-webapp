function init(){}

function setInfiltrator(currentVersion){
    const infiltratorSelect = document.querySelector("#infiltratorSelect");
    for(const key in pawns){
        if(pawns.hasOwnProperty(key) && currentVersion.hasOwnProperty(key)) {
            const option = document.createElement("option");
            option.setAttribute("value", key);
            option.innerText = key;
            infiltratorSelect.insertAdjacentElement("beforeend", option);
        }
    }
}

function moveInfiltrator(e){
    let clickedDiv;
    const infiltrater = document.getElementById("infiltratorSelect");
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
                "infiltrate": infiltrater.value
            }
    ).then(response => {
        console.log(response);
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