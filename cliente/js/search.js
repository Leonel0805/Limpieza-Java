import { crearCards } from './utils/crearCards.js';
import { buttonAgregar } from './carrito/carrito.js';

let articuloContainer = document.querySelector(".articulos__container")

// aca tengo que hacer fetch a la ruta de la api Search
let apiURL = 'http://localhost:8080/api/articulos/search'

// creamos nuestro urlParams
const urlParams = new URLSearchParams(window.location.search);

// Obtenemos nuestro param Query
const query = urlParams.get('query');

// Hacemos peticion a la api
async function cargarBusquedas(){

    let response = await fetch(apiURL + '?query=' + query)
   
    if (response.status == 200){
        let json = await response.json()
        crearCards(json)
    
    } else if (response.status == 404){

        let json = await response.json()
        sendNotFound(json['message'])
        console.log(asdf)
    }

        

}

// Enviar mensaje de error
function sendNotFound(message){

    let errorMessage = message;

    let error = document.createElement('h3');
    error.innerText = errorMessage;

    articuloContainer.appendChild(error)
}


async function init(){

    await cargarBusquedas()
    buttonAgregar()
}


await init()

