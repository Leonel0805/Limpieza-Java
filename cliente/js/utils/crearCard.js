
export function crearCard(articulo){
    
        // // creamos container card
        let articuloCard = document.createElement('div')
        articuloCard.className = 'articulo__card'
        articuloCard.setAttribute('data-id', articulo.id);

        // // creamos ahref
        let articuloHref = document.createElement('a')
        articuloHref.href = './templates/articulos/articulo_detail.html' + '?id=' + articulo.id
        articuloHref.className = 'articulo__href'

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
        articuloTitle.innerText = articulo.nombre

        // Creamos articulo precio
        let articuloPrecio = document.createElement('p')
        articuloPrecio.className = 'articulo__precio'
        articuloPrecio.innerText = articulo.precio

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


        return articuloCard;

}