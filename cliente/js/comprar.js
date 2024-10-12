import { obtenerCarrito } from '../js/carrito/carrito.js';
import { guardarCarrito } from '../js/carrito/carrito.js';


let carrito = localStorage.getItem('carrito')
const apiURL = 'http://localhost:8080/api/articulos'


export function obtenerArticulosCarrito(carrito){

    let articulosCarrito = JSON.parse(carrito)

    return articulosCarrito
}


async function obtenerDatosById(articulos) {

    let articulosDB = [];

    for (let articulo of articulos) {
        let response = await fetch(apiURL + `/${articulo.id}`);
        if (response.status == 200) {
            let data = await response.json();
            // agregamos a la lista
            articulosDB.push(data);
        }
    }

    return articulosDB; 
}

function generarCards(articulosList){

    if (articulosList && articulosList.length > 0) {
        articulosList.forEach(articulo => {
            crearCard(articulo);
        });
    } else {
        console.error('articulosList está vacío o es undefined');
    }
}


function crearCard(articulo){

    console.log('entramos a ccrear card')
    let articulosCarrito = obtenerArticulosCarrito(carrito)

    let existArticulo = articulosCarrito.find(articuloCarrito => articuloCarrito.id == articulo.id)

    let tbody = document.querySelector('#tbody')

    // creamos nuestro tr
    let comprarCard = document.createElement('tr')
    comprarCard.className = 'comprar__card'
    comprarCard.setAttribute('data-id', articulo.id)

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
    cardPrecio.innerHTML = '$ ' + articulo.precio


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
    cardSubTotal.classList.add('comprar__item', 'subtotal')
    cardSubTotal.innerHTML ='$ ' + articulo.precio * inputCantidad.value

    // recalculamos subtotal
    inputCantidad.addEventListener('input', function() {
        cardSubTotal.innerHTML = '$ ' + (articulo.precio * inputCantidad.value);
    });

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

function obtenerSubtotalesDOM(){

    let subtotales = document.querySelectorAll('.subtotal')

    let arraySubtotales = []

    for (let subtotal of subtotales){

        let numberSubtotal = subtotal.textContent.split('$ ')[1]
        arraySubtotales.push(parseInt(numberSubtotal))
    }

    return arraySubtotales;
}

function resumen(){
    
    let resumenSubtotal = document.querySelector('.comprar__resumen__subtotal')
    let resumenEnvio = document.querySelector('.comprar__resumen__envioValue')
    let resumenTotal = document.querySelector('.comprar__resumen__totalValue')

    let arraySubtotales =  obtenerSubtotalesDOM()
    
    let subTotal = 0

    for (let subtotal of arraySubtotales){
        subTotal += subtotal;
    }

    resumenSubtotal.innerHTML = '$' + subTotal
    // sumar total + envio
    resumenTotal.innerHTML = '$ '+ (subTotal + parseInt(resumenEnvio.innerHTML.split('$ ')[1]))
}


function manipularInputComprar(){

    let carrito = obtenerCarrito() 
    let inputNumbers = document.querySelectorAll('.comprar__cantidad')

    // manipulamos cada input
    inputNumbers.forEach(input => {

        input.addEventListener('input', async function(event) {

            // obtenemos el nuevo valor actualizado por input
            console.log('evento actualizado: ', event.target.value, 'type', typeof(event.target.value))
            // obtenemos la card del que se actualizo valor por input
            let carritoCard = event.target.closest('.comprar__card')

            // obtenemos new cantidad y paseamos a int
            let newCantidad = parseInt(event.target.value, 10)

            // obtenmos id del articulo actualizado
            let carritoArticuloID = carritoCard.getAttribute('data-id')

            let existObject = carrito.find(articulo => articulo.id == carritoArticuloID)

            // si existe reemplazamos la cantidad 
            if (existObject){
                console.log('actualizamoscnatidad')
                existObject.cantidad = newCantidad;
        
            } 
            // guardamos el carrito
            await guardarCarrito(carrito)
            resumen()
        
        })
    })
}


// INIT
async function init(){

    let articulosCarrito = await obtenerArticulosCarrito(carrito) 
    let articulosDB = await  obtenerDatosById(articulosCarrito)

    generarCards(articulosDB)
    manipularInputComprar()
    resumen()
}

await init()
