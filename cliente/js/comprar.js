

console.log('hola desde comprar')

let carrito = localStorage.getItem('carrito')
const apiURL = 'http://localhost:8080/api/articulos'


function obtenerArticulosCarrito(carrito){

    let articulosCarrito = JSON.parse(carrito)
    console.log('mis articulos', articulosCarrito)

    console.log('terminamos de botener carrito')
    return articulosCarrito


}


function obtenerDatosById(articulos){

    articulos.forEach(articulo => {
        
        
        fetch(apiURL + `/${articulo.id}`)
        .then(response => {
            
            if(response.status == 200){
                return response.json()
            }
        })
        .then(articulo => {

            // creamos card
            console.log('creamos card')
            console.log(articulo.descripcion)
            crearCard(articulo)

        })
    });


}


function crearCard(articulo){

    console.log('entramos a ccrear card')
    let articulosCarrito = obtenerArticulosCarrito(carrito)

    let existArticulo = articulosCarrito.find(articuloCarrito => articuloCarrito.id == articulo.id)

    let tbody = document.querySelector('#tbody')

    // creamos nuestro tr
    let comprarCard = document.createElement('tr')
    comprarCard.className = 'comprar__card'

    // creamos nuestro td ImageContainer
    let cardImgContainer = document.createElement('td')
    cardImgContainer.className = 'comprar__cardImg__Container'

    // creamos la imagen
    let cardImage = document.createElement('img')
    cardImage.alt = 'Imagen'

    // creamos nuestro nombre de Card
    let cardTitle = document.createElement('td')
    cardTitle.classList.add('comprar__cardTtitle', 'comprarNombre')
    cardTitle.innerHTML = articulo.nombre

    // creamos nuestro precio 
    let cardPrecio = document.createElement('td')
    cardPrecio.className = 'comprar__item'
    cardPrecio.innerHTML = '$' + articulo.precio


    // creamos nuestro cantidad
    let cardCantidad = document.createElement('td')
    cardCantidad.className = 'comprar__item'

    // le tenemos que poner el input a nuestra cantidad
    let inputCantidad = document.createElement('input')
    inputCantidad.className = 'comprar__cantidad'
    inputCantidad.type = 'number'
    inputCantidad.min = 1
    inputCantidad.max = 99
    inputCantidad.value = existArticulo.cantidad

    let cardSubTotal = document.createElement('td')
    cardSubTotal.className = 'comprar__item'
    cardSubTotal.innerHTML ='$' + articulo.precio * inputCantidad.value

    // ahora añadimos todo

    cardImgContainer.appendChild(cardImage)
    cardCantidad.appendChild(inputCantidad)

    comprarCard.appendChild(cardImgContainer)
    comprarCard.appendChild(cardTitle)
    comprarCard.appendChild(cardPrecio)
    comprarCard.appendChild(cardCantidad)
    comprarCard.appendChild(cardSubTotal)

    tbody.appendChild(comprarCard)

}

async function init(){

    let articulosCarrito = await obtenerArticulosCarrito(carrito) 
    obtenerDatosById(articulosCarrito)
}

init()