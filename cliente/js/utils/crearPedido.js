import { obtenerArticulosCarrito } from '../comprar.js';
import { vaciarCarrito } from '../carrito/carrito.js';
import { obtenerDatosById } from '../comprar.js';
import { init } from '../comprar.js';


// jwt local
const jwt = localStorage.getItem('jwt');
const apiURL = 'http://localhost:8080/api/pedidos/create'


// obtenemos botton finalizar Compra
let buttonComprar = document.querySelector('.comprar__resumen__button')


// Creamos el pedido
function crearPedido(articulosCarrito){

    let articulosBody = crearDetalles(articulosCarrito)
    let requestBody = {
        pedido: {},
        detalles: articulosBody
    }

// Hacemos la request
    fetch (apiURL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer '+ jwt
        },
        body: JSON.stringify(
            requestBody
        )
    })
    .then(response => {

        if (response.status == 201){
            return response.json()
        } else{
            
            return response.json().then(errorData => {

                // lo que ponemos del error
                let overlay = document.querySelector('.overlay')
                let overlayText = document.querySelector('.overlay__parrafo')
                let overlayContainer = document.querySelector('.overlay__container')

                overlay.style.display = 'flex'
                overlayContainer.classList.add('overlay--error')

                overlayText.innerHTML = errorData.message

            })
        }
        // mandar error mensaje
    })
    .then(json => {

        console.log(json.message)
        let overlay = document.querySelector('.overlay')
        overlay.style.display = 'flex'

        vaciarCarrito()
        init()

    })
}


// creamos los articulos para pasar por body del request, medio en vano
function crearDetalles(articulos){

    let articulosArray = []

    for(let articulo of articulos){

        let articuloBody = {
            cantidad: articulo.cantidad,
            articulo_name: articulo.name
        }
        articulosArray.push(articuloBody)
    }

    console.log('mis articulos', articulosArray)
    return articulosArray
}


// agregamos todo del carrito al pedido creado
// function agregarDetallesPedido(id, articulos){

    
//     let articulosBody = crearDetalles(articulos)

//     // AGREGAMOS cada articulo al pedido
//     for (let articulo of articulosBody){

//         fetch(apiURL + `/${id}` + '/detalle',{
//             method: 'POST',
//             headers:{
//                 'Content-Type': 'application/json',
//                 'Authorization': 'Bearer '+ jwt
//             },
//             body: JSON.stringify({
//                 // cantidad: 5,
//                 // articulo_name: 'teclado5' //forzamos para test
//                 ...articulo //seria lo de arriba declaramos sus propiedades
//             })
//         })

//         .then(response => {

//             console.log(response.status)
//             if (response.status == 200){
//                 console.log('agrgamos detalle')
//                 return response.json()
            
//             } else {

//                 return response.json().then(errorData => {

//                     // lo que ponemos del error
//                     let overlay = document.querySelector('.overlay')
//                     let overlayText = document.querySelector('.overlay__parrafo')
//                     let overlayContainer = document.querySelector('.overlay__container')

//                     overlay.style.display = 'flex'
//                     overlayContainer.classList.add('overlay--error')

//                     overlayText.innerHTML = errorData.message

//                 })
//             }
//         })
//         .then(json => {
//             console.log(json.message)

//             let overlay = document.querySelector('.overlay')
//             overlay.style.display = 'flex'

//         })
//     }
// }


function finalizarComprarErrorOrSuccess(){

    let buttonFinish = document.querySelector('.overlay__button')
   
    buttonFinish.addEventListener('click', async function(){

        let overlay = document.querySelector('.overlay')
        overlay.style.display = 'none'


    })
}


// si hacemos click

async function buttonComprarFunction(){

    // carrito Local
    let carrito = localStorage.getItem('carrito');
    let articulosCarrito = await obtenerArticulosCarrito(carrito) 

    console.log(articulosCarrito.length, 'mis articulos')

    if (articulosCarrito.length > 0){

        buttonComprar.addEventListener('click', async function(){
            console.log('entramos para crear pedido')
            crearPedido(articulosCarrito)
            finalizarComprarErrorOrSuccess() 
        })
    } else {
                   
        console.log('carrito vacio paaa')
        let buttonFinalizar = document.querySelector('.comprar__resumen__button')
        buttonFinalizar.innerHTML = 'Carrito vacio'
    }

}

buttonComprarFunction()




