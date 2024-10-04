import { crearCards } from './utils/crearCards.js';

let articuloContainer = document.querySelector(".articulos__container")

// aca tengo que hacer fetch a la ruta de la api Search
let apiURL = 'http://localhost:8080/api/articulos/search'

// creamos nuestro urlParams
const urlParams = new URLSearchParams(window.location.search);

// Obtenemos nuestro param Query
const query = urlParams.get('query');

// Hacemos peticion a la api
fetch(apiURL + '?query=' + query)
    .then(response => {

        if (response.status == 200){
            return response.json()
            .then(json => {
                crearCards(json)
            })
        
        } else if(response.status == 404){
            return response.json()
            .then(json => {
                console.log(json['message'])
                sendNotFound(json['message'])

            })
        }
    });



function sendNotFound(message){

    let errorMessage = message;

    let error = document.createElement('h3');
    error.innerText = errorMessage;

    articuloContainer.appendChild(error)

}
