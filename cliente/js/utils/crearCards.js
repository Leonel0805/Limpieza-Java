import { crearCard } from "./crearCard.js"

// Obtenemos el container
let articuloContainer = document.querySelector(".articulos__container")

// Crear cards
export function crearCards(articulos){

    for (let i=0; i < Math.min(8, articulos.length); i++){

        let articuloCard = crearCard(articulos[i])

        articuloContainer.appendChild(articuloCard)

    }
}