"use strict";
/*
* Use this file to partially abstract communication with the server.
* Feel free to extend it as you see fit (TIP: URLs should probably be collected somewhere. This is one of several valid options)
* */

function fetchFromServer(url, httpVerb, requestBody){
    const options= {};
    options.method = httpVerb;

    options.headers = {};
    options.headers["Content-Type"] = "application/json";

    if(typeof  _gameConfig !== 'undefined') {
        options.headers["Authorization"] = "Bearer " + _gameConfig.playerToken;
    }
    // Don't forget to add data to the body when needed
    options.body = JSON.stringify(requestBody);

    return fetch(url, options)
        .then((response) => {
            if (!response.ok) {
                console.error('%c%s','background-color: red;color: white','! An error occurred while calling the API');
                console.table(response);
            }
            return response.json();
        })
        .then((jsonresponsetoparse) => {
            return jsonresponsetoparse;
        });
}
