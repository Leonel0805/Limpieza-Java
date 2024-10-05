import { crearCards } from './utils/crearCards.js';
import { agregar } from './carrito/carrito.js';


const apiURL = "https://miraculous-warmth-production.up.railway.app/api/articulos";

async function generarCards() {
    let response = await fetch(apiURL);

    if (response.status === 200) {

        let json = await response.json();
        crearCards(json); 
    }
}

document.addEventListener('DOMContentLoaded', async function(){
    await generarCards()
    console.log('terminamos el generarcards ')

    // vamos a ponerle interaccion
    agregar()
});
