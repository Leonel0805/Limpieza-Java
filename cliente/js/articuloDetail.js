import { crearCard } from "./utils/crearCard.js"

// obtener el id enviado por queryparams
const urlParams = new URLSearchParams(window.location.search);

// Obtenemos el container
let articuloContainer = document.querySelector(".articulos__container")

// Obtenemos nuestro param Query
let id = urlParams.get('id');

const APIUrl = 'https://miraculous-warmth-production.up.railway.app/api/articulos'
console.log(id)

function obtenerArticuloById(id){

    fetch(APIUrl + `/${id}`)
    .then(response => {
        
        if(response.status == 200){
            return response.json()
        }
    })
    .then(articulo => {

        console.log(articulo.descripcion)
        let articuloCard = crearCard(articulo)

        articuloContainer.appendChild(articuloCard)
    })

}

obtenerArticuloById(id);





