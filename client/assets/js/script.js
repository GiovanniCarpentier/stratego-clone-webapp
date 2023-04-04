"use strict";
/* DISCLAIMER */
/*
* This file aims to help you get started. It demonstrates how to connect to the server, but is by no means the most efficient way of
* handling these steps. Neither does the interface need to meet these steps, please follow your wireframes. Use this code as
* a reference when you create your own version.
*
* Feel free to extend the config with other variables of your choice
*
* */

let _gameConfig;

document.addEventListener('DOMContentLoaded',init);

async function init(){
    fetchFromServer(`${config.getAPIUrl()}/versions`,'GET').then(response => console.log(response));

    _gameConfig = await createConfiguration();
    createConfiguration();

    fetchFromServer(`${config.getAPIUrl()}/games/${_gameConfig.gameId}/configuration`,'GET').then(response => console.log(response));

    movePawn();

    fetchFromServer(`${config.getAPIUrl()}/games/${_gameConfig.gameId}/configuration`,'GET').then(response => console.log(response));

}