import { crearCards } from './utils/crearCards.js';


const apiURL = "http://localhost:8080/api/articulos/activos";

async function generarCards() {
    let response = await fetch(apiURL);

    if (response.status === 200) {

        let json = await response.json();
        crearCards(json); 
    }
}

document.addEventListener('DOMContentLoaded', async function(){
    await generarCards()

    document.dispatchEvent(new Event('cardsCargadas'));

});
