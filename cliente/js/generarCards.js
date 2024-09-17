import { crearCards } from './utils/crearCards.js';


const apiURL = "https://miraculous-warmth-production.up.railway.app/api/articulos";

async function generarCards() {
    let response = await fetch(apiURL);

    if (response.status === 200) {

        let json = await response.json();
        crearCards(json); 
    }
}

await generarCards()