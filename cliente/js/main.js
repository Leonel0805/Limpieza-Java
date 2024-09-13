import { crearCards } from './utils/crearCards.js';
import { sendFormSearchParam } from './utils/sendFormSearchParam.js';

const apiURL = "https://miraculous-warmth-production.up.railway.app/api/articulos"

async function generarCards() {
    let response = await fetch(apiURL);

    if (response.status === 200) {

        let json = await response.json();
        await crearCards(json); 
    }
}


async function init(){

    sendFormSearchParam()

    // generamos las cards con su debitas rutas
    await generarCards()

}

init()





