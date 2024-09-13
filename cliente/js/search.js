import { sendFormSearchParam } from './utils/sendFormSearchParam.js';
import { crearCards } from './utils/crearCards.js';

// aca tengo que hacer fetch a la ruta de la api Search
let apiURL = 'http://localhost:8080/api/articulos/search'

const urlParams = new URLSearchParams(window.location.search);

// Extrae el valor del parámetro 'query'
const query = urlParams.get('query');

console.log(query); 

fetch(apiURL + '?query=' + query)
    .then(response => {

        if (response.status == 200){
            return response.json()
        }
    })
    .then(json => {
        crearCards(json)
    });


sendFormSearchParam()