

const apiURL = "https://miraculous-warmth-production.up.railway.app/api/articulos"
let articuloContainer = document.querySelector(".articulos__container")

console.log(articuloContainer)

fetch(apiURL)
    .then(response => {

        let status = response.status

        console.log(status)

        if(status == 200){
            return response.json()
        }

    })
    .then(json => {
        crearCards(json)
    })

// Crear cards
function crearCards(articulos){

    for (let i=0; i < Math.min(6, articulos.length); i++){


        // // creamos container card
        let articuloCard = document.createElement('div')
        articuloCard.className = 'articulo__card'

        // // creamos ahref
        let articuloHref = document.createElement('a')
        articuloHref.href = './templates/articulo_detail.html'

        // // creamos container imange
        let articuloImageContainer = document.createElement('div')
        articuloImageContainer.className = 'articulo__image__container'


        // // creamos image
        let articuloImage = document.createElement('img')
        articuloImage.className = 'articulo__image'
        // articuloImage.src = "./re"

        // // creamos Articulo Content
        let articuloContent = document.createElement('div')
        articuloContent.className = 'articulo__content'

        // // Creamos articulo title
        let articuloTitle = document.createElement('h3')
        articuloTitle.className = 'articulo__title'
        articuloTitle.innerText = articulos[i].nombre

        // Creamos articulo precio
        let articuloPrecio = document.createElement('p')
        articuloPrecio.className = 'articulo__precio'
        articuloPrecio.innerText = articulos[i].precio

        // Creamos botton agregar
        let articuloButton = document.createElement('button')
        articuloButton.className = 'articulo__button'
        articuloButton.innerText = 'Agregar'

        // añadimos todo
        articuloImageContainer.appendChild(articuloImage)

        articuloContent.appendChild(articuloTitle)
        articuloContent.appendChild(articuloPrecio)

        articuloHref.appendChild(articuloImageContainer)
        articuloHref.appendChild(articuloContent)

        articuloCard.appendChild(articuloHref)
      
        articuloCard.appendChild(articuloButton)


        // añadimos al articulos container la card
        articuloContainer.appendChild(articuloCard)

    }
}