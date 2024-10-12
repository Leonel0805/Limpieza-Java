import  { buttonAgregar } from '../js/carrito/carrito.js';

// obtener el id enviado por queryparams
const urlParams = new URLSearchParams(window.location.search);

// Obtenemos el container
let articuloContainer = document.querySelector(".articulos__container")

// Obtenemos nuestro param Query
let id = urlParams.get('id');

const APIUrl = 'http://localhost:8080/api/articulos'
console.log(id)

async function obtenerArticuloById(id){

    return fetch(APIUrl + `/${id}`)
    .then(response => {
        
        if(response.status == 200){
            return response.json()
        }
    })
    .then(articulo => {

        let articuloCard = crearCard(articulo)

        articuloContainer.appendChild(articuloCard)
    })

}


export function crearCard(articulo){
    
    // // creamos container card
    let articuloCard = document.createElement('div')
    articuloCard.className = 'articulo__card'
    articuloCard.setAttribute('data-id', articulo.id);

    // // creamos container image
    let articuloImageContainer = document.createElement('div')
    articuloImageContainer.className = 'articulo__image__container'


    // // creamos image
    let articuloImage = document.createElement('img')
    articuloImage.className = 'articulo__image'
    articuloImage.setAttribute('alt','Imagen')

    // // creamos Articulo Content
    let articuloContent = document.createElement('div')
    articuloContent.className = 'articulo__content'

    // // Creamos articulo title
    let articuloTitle = document.createElement('h3')
    articuloTitle.className = 'articulo__title'
    articuloTitle.innerText = articulo.nombre

    // Creamos articulo precio
    let articuloPrecio = document.createElement('p')
    articuloPrecio.className = 'articulo__precio'
    articuloPrecio.innerText = '$' + articulo.precio

    
    // creamos la descrpcion
    let articuloDescripcion = document.createElement('p')
    articuloDescripcion.className ='articulo__descripcion'
    articuloDescripcion.innerHTML = articulo.descripcion

    // creamos el div para poner los 2 buttons
    let articuloDivButtons = document.createElement('div')
    articuloDivButtons.className = 'articulo__buttons'


    // Creamos botton agregar
    let articuloButton = document.createElement('button')
    articuloButton.classList.add('articulo__button')
    articuloButton.innerText = 'Agregar'

    // añadimos todo
    articuloImageContainer.appendChild(articuloImage)

    articuloContent.appendChild(articuloTitle)
    articuloContent.appendChild(articuloPrecio)

    articuloDivButtons.appendChild(articuloButton)
    articuloContent.appendChild(articuloDivButtons)

    articuloContent.appendChild(articuloDescripcion)

  
    articuloCard.appendChild(articuloImageContainer)
    articuloCard.appendChild(articuloContent)


    return articuloCard;

}


async function init(){

    await obtenerArticuloById(id);
    buttonAgregar()
}

init()





