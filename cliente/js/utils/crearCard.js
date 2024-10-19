
export function crearCard(articulo){
    
        let baseURL = localStorage.getItem('baseURL')
        // // creamos container card
        let articuloCard = document.createElement('div')
        articuloCard.className = 'articulo__card'
        articuloCard.setAttribute('data-id', articulo.id);

        // // creamos ahref
        let articuloHref = document.createElement('a')
        articuloHref.href = baseURL + '/cliente/templates/articulos/articulo_detail.html' + '?id=' + articulo.id
        articuloHref.className = 'articulo__href'

        // // creamos container imange
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

        // Creamos botton agregar if el stock > 0
        let articuloButton;

        if (articulo.stock > 0){
                articuloButton = document.createElement('button')
                articuloButton.className = 'articulo__button'
                articuloButton.innerText = 'Agregar'

        } else {
                articuloButton = document.createElement('p')
                articuloButton.className = 'articulo__button--NoStock'
                articuloButton.innerHTML = 'Sin Stock'
        }
 
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