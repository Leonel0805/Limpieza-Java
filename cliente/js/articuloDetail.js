// obtener el id enviado por queryparams
const urlParams = new URLSearchParams(window.location.search);

// Obtenemos el container
let articuloContainer = document.querySelector(".articulos__container")

// Obtenemos nuestro param Query
let id = urlParams.get('id');

const APIUrl = 'http://localhost:8080/api/articulos'
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
    articuloPrecio.innerText = articulo.precio


    // creamos el div para poner los 2 buttons
    let articuloDivButtons = document.createElement('div')
    articuloDivButtons.className = 'articulo__buttons'


    // creamos el primer boton de cantidad
    let inputCantidad = document.createElement('input')
    inputCantidad.className = 'articulo__button--cantidad'
    inputCantidad.setAttribute('type', 'number')
    inputCantidad.setAttribute('min', '1')
    inputCantidad.setAttribute('max', '99')
    inputCantidad.value = 1


    // Creamos botton agregar
    let articuloButton = document.createElement('button')
    articuloButton.className = 'articulo__button--agregar'
    articuloButton.innerText = 'Agregar'

    // añadimos todo
    articuloImageContainer.appendChild(articuloImage)

    articuloContent.appendChild(articuloTitle)
    articuloContent.appendChild(articuloPrecio)

    articuloDivButtons.appendChild(inputCantidad)
    articuloDivButtons.appendChild(articuloButton)
    articuloContent.appendChild(articuloDivButtons)

  
    articuloCard.appendChild(articuloImageContainer)
    articuloCard.appendChild(articuloContent)


    return articuloCard;

}





