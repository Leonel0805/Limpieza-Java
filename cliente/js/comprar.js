import { obtenerCarrito } from '../js/carrito/carrito.js';
import { guardarCarrito } from '../js/carrito/carrito.js';


const apiURL = 'http://localhost:8080/api/articulos/activos'
const baseURl = localStorage.getItem('baseURL')
let carrito = localStorage.getItem('carrito')


export function obtenerArticulosCarrito(carrito){

    let articulosCarrito = JSON.parse(carrito)

    return articulosCarrito
}


export async function obtenerDatosById(articulos) {

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

export function generarCards(articulosList){


    if (articulosList && articulosList.length > 0) {
        articulosList.forEach(articulo => {
            crearCard(articulo);
        });
    } else {
        console.error('articulosList está vacío o es undefined');
        let tbody = document.querySelector('#tbody')
        tbody.innerHTML = ''
    
    }
}


function crearCard(articulo){

    let tbody = document.querySelector('#tbody')

    let articulosCarrito = obtenerArticulosCarrito(carrito)

    let existArticulo = articulosCarrito.find(articuloCarrito => articuloCarrito.id == articulo.id)

    console.log('mi', existArticulo)

    // creamos nuestro tr
    let comprarCard = document.createElement('tr')
    comprarCard.className = 'comprar__card'
    comprarCard.setAttribute('data-id', articulo.id)

    // creamos nuestro td ImageContainer
    let cardImgContainer = document.createElement('td')
    cardImgContainer.className = 'comprar__cardImg__Container'

    // creamos la imagen
    let cardImage = document.createElement('img')
    cardImage.classList.add('comprar__cardImg')
    cardImage.alt = 'Imagen de ' + articulo.nombre
    cardImage.src = articulo.imageUrl

    // creamos nuestro nombre de Card
    let cardTitle = document.createElement('td')
    cardTitle.classList.add('comprar__cardTtitle', 'comprarNombre')

    // creamos nuestro href
    let cardHref = document.createElement('a')
    cardHref.href = baseURl + '/cliente/templates/articulos/articulo_detail.html?id=' + articulo.id
    cardHref.className = 'comprar__cardHref'
    cardHref.innerHTML = articulo.nombre

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
    inputCantidad.max = articulo.stock //ponemos valor maximo cantidad de stock disponible en DB
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
    cardTitle.appendChild(cardHref)

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

    console.log('manipulamos inputs')
    let carrito = obtenerCarrito() 
    let inputNumbers = document.querySelectorAll('.comprar__cantidad')

    // manipulamos cada input
    inputNumbers.forEach(input => {

        input.addEventListener('input', async function(event) {

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
export async function init(){

    let carrito = localStorage.getItem('carrito')
    let articulosCarrito = await obtenerArticulosCarrito(carrito) 
    let articulosDB = await  obtenerDatosById(articulosCarrito)

    generarCards(articulosDB)
    manipularInputComprar()
    resumen()
}

await init()
