import { obtenerArticulosCarrito } from '../comprar.js';
import { vaciarCarrito } from '../carrito/carrito.js';

// jwt local
const jwt = localStorage.getItem('jwt');
const apiURL = 'http://localhost:8080/api/pedidos'

// carrito Local
let carrito = localStorage.getItem('carrito');

// convertimos los articulos en objetos
let articulosCarrito = obtenerArticulosCarrito(carrito)

// obtenemos botton finalizar Compra
let buttonComprar = document.querySelector('.comprar__resumen__button')


// Creamos el pedido para obtener el id
async function crearPedido(){

    return fetch (apiURL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer '+ jwt
        },
        body: JSON.stringify({}) //vacio porque un admin puede crear un pedido a un encargado
    })
    .then(response => {

        if (response.status == 201){
            return response.json()
        }
        // mandar error mensaje
    })
    .then(json => {

        // retornamos el id del response
        console.log(json.data.id)
        return json.data.id

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
function agregarDetallesPedido(id, articulos){

    
    let articulosBody = crearDetalles(articulos)

    // AGREGAMOS cada articulo al pedido
    for (let articulo of articulosBody){

        console.log('mi articulo',articulo)
        console.log('mi articulobody', JSON.stringify(articulo))
        fetch(apiURL + `/${id}` + '/detalle',{
            method: 'POST',
            headers:{
                'Content-Type': 'application/json',
                'Authorization': 'Bearer '+ jwt
            },
            body: JSON.stringify({
                // cantidad: 5,
                // articulo_name: 'teclado5' //forzamos para test
                ...articulo //seria lo de arriba declaramos sus propiedades
            })
        })

        .then(response => {

            console.log(response.status)
            if (response.status == 200){
                console.log('agrgamos detalle')
                return response.json()
            
            } else {

                return response.json()
            }
        })
        .then(json => {
            console.log(json.message)

            let overlay = document.querySelector('.overlay')
            overlay.style.display = 'flex'

        })

    }


}

let buttonFinish = document.querySelector('.overlay__button')

buttonFinish.addEventListener('click', function(){

    let overlay = document.querySelector('.overlay')
    overlay.style.display = 'none'

})

// si hacemos click
buttonComprar.addEventListener('click', async function(){

    // creamos el pedido y obtenemos el id solo si el carrito tiene items
    if (articulosCarrito.length != 0){
        let id = await crearPedido()
        // agregamos
        agregarDetallesPedido(id, articulosCarrito)
        vaciarCarrito()

    } else{
        console.log('carrito vacio pa')
    }


})



