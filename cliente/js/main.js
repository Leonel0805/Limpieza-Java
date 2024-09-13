import { crearCards } from './utils/crearCards.js';

const apiURL = "https://miraculous-warmth-production.up.railway.app/api/articulos"
let articuloContainer = document.querySelector(".articulos__container")


fetch(apiURL)
    .then(response => {

        let status = response.status

        console.log(status)

        if(status == 200){
            return response.json()
        }

    })
    .then(json => {
        crearCards(json, articuloContainer)
    })

// search

let inputSearch = document.querySelector('#inputSearch')
console.log(inputSearch)

let formSearch = document.querySelector('#formSearch')
console.log(formSearch)

formSearch.addEventListener('submit', function(event){

    event.preventDefault();
    console.log("Formulario al enviar")
    let searchValue = inputSearch.value;

    if (searchValue){
        window.location.href = `/cliente/templates/pages/search.html?query=${encodeURIComponent(searchValue)}`;
    }else{
        console.log("no se pudo redireccionar")
    }


});
