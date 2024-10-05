
// articulo tiene que ser objeto
export function agregar(){

    let articulosIDs = document.querySelectorAll('.articulo__card')
    let articuloButton = document.querySelectorAll('.articulo__button')

    console.log(articulosIDs)
    console.log(articuloButton)

    articuloButton.forEach(button => {

        button.addEventListener('click', async function(event){
    
            const articuloCard = event.target.parentElement;

            crearArticulo(articuloCard)
        })
    })

}

// creamos el objeto obteniendo de la database
function crearArticulo(articuloCard){

    let apiURL = 'http://localhost:8080/api/articulos'
    let id = articuloCard.getAttribute('data-id')
    
    // obtenemos de la database
    fetch(apiURL + `/${id}`)
    .then(response => {

        if (response.status == 200){
            return response.json()
        }
    })
    .then(articulo => {

        // obtuvimos el articulo de la database
        let articuloObjeto = {
            id : articulo.id,
            name : articulo.nombre,
            cantidad : 1
        }
        
        agregarCarrito(articuloObjeto)
        
    })


}

function agregarCarrito(articuloObjeto){

    let carrito = obtenerCarrito()

    let existObject = carrito.find(articulo => articulo.id == articuloObjeto.id)

    if (existObject){
        // si existe le aumentamos la cantidad en 1
        existObject.cantidad += 1;

    } else{
        carrito.push(articuloObjeto)
    }

    // convertimos nuestro carrito para poder guardarlo en json en el localstorage
    let carritoString = JSON.stringify(carrito)
    localStorage.setItem('carrito', carritoString)

    mostrarCarrito()

}

function obtenerCarrito(){

    let carrito = localStorage.getItem('carrito')

    if (carrito){
        return  JSON.parse(carrito)
    }else{
        return []
    }
}

function mostrarCarrito(){
    
    let carrito  = obtenerCarrito()

    console.log(carrito)


    generarCarritoCards(carrito)

}

function vaciarCarrito(){

    let carrito = []
    localStorage.setItem('carrito', JSON.stringify(carrito))
}


function generarCarritoCards(carrito){

    let carritoContent = document.querySelector('.carrito__content')
    carritoContent.innerHTML = ''

    carrito.forEach(articulo => {

        carritoContent = document.querySelector('.carrito__content')

        // creamos nuestros elementos para mostrarlos en el carrito
        let carritoCard = document.createElement('div')
        carritoCard.className = 'carrito__card'
    
        // creamos img
        let carritoImg = document.createElement('img')
        carritoImg.className = 'carrito__img'
        carritoImg.setAttribute('alt', 'Imagen')
    
        // creamos eltitle
        let carritoTitle = document.createElement('h3')
        carritoTitle.classList = 'carrito__title'
        carritoTitle.innerHTML = articulo.name
    
        // creamos input
        let carritoCantidad = document.createElement('input')
        carritoCantidad.className = 'carrito__cantidad'
        carritoCantidad.type ='number'
        carritoCantidad.value = articulo.cantidad


        // agregamos todo al  card y al container
        carritoCard.appendChild(carritoImg)
        carritoCard.appendChild(carritoTitle)
        carritoCard.appendChild(carritoCantidad)

        carritoContent.appendChild(carritoCard)

    })

    

}

// mostramos y vaciamos despues de cargar el header
document.addEventListener('headerFooterCargados', function() {

    let carritoIcon = document.querySelector('.carrito__icon')
    let carritoContainer = document.querySelector('.carrito__container')


    carritoIcon.addEventListener('click', function (){

        console.log('sehizo click en containericon')
        if (carritoContainer.style.display == 'none' || carritoContainer.style.display == ''){
            carritoContainer.style.display = 'block'
        } else{
            carritoContainer.style.display = 'none'
        }
    })


    mostrarCarrito()

    let vaciarButton = document.querySelector('.carrito__button');

    if (vaciarButton) {
        vaciarButton.addEventListener('click', function(event){
            event.preventDefault();
            console.log('Se hizo click en vaciar');
            vaciarCarrito(); 
            mostrarCarrito()
        });
    }
});


document.addEventListener('DOMContentLoaded', function(){

    console.log('cargamoscarritojs')
})


