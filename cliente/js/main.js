import { crearCards } from './utils/crearCards.js';
import { sendFormSearchParam } from './utils/sendFormSearchParam.js';

const apiURL = "https://miraculous-warmth-production.up.railway.app/api/articulos"

async function generarCards() {
    let response = await fetch(apiURL);

    if (response.status === 200) {

        let json = await response.json();
        crearCards(json); 
    }
}


async function init(){

    sendFormSearchParam()

    // generamos las cards con su debitas rutas
    await generarCards()

    let token = localStorage.getItem('token')
    console.log(token)

    // el local storage mantiene ahi hasta que le digamos
    let username = localStorage.getItem('username')
    console.log(username)
}

init()





