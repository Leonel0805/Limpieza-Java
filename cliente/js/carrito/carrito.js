
// articulo tiene que ser objeto
export function agregar(){

    console.log('funcion agregar')
    // obtenemos todos los botones
    let articuloButton = document.querySelectorAll('.articulo__button')

    console.log(articuloButton)
    articuloButton.forEach(button => {

        button.addEventListener('click', async function(event){
    
            // obtenemos el articuloHTML de ese button
            console.log('se hizo clic')
            const articuloCard = event.target.parentElement;

            crearArticulo(articuloCard)
        })
    })

}

// creamos el objeto obteniendo de la database por id
function crearArticulo(articuloCard){

    let apiURL = 'http://localhost:8080/api/articulos'
    let id = articuloCard.getAttribute('data-id')
    
    // realizamos fetch a la data base
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

    let carritoButton = document.querySelector('#carritoVaciarAgregar')
    if(carritoButton){
        carritoButton.innerHTML = 'Vaciar carrito'
        carritoButton.classList.remove('button--green', 'button--vacio')
        carritoButton.classList.add('button--red')
    }

    let  carritoComprar = document.querySelector('#carritoComprar')
    carritoComprar.style.display = 'block'

    let carritoContainer = document.querySelector('.carrito__container')
    carritoContainer.style.display = 'flex'


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

    if (carrito.length === 0){
        console.log('carritovacio')
        let carritoButton = document.querySelector('.carrito__button')
        let carritoComprar = document.querySelector('#carritoComprar')

        carritoButton.innerHTML = 'Agrega articulos!'
        carritoButton.classList.remove('button--red')
        carritoButton.classList.add('button--vacio', 'button--green')

        carritoComprar.style.display ='none'
    } else{

        generarCarritoCards(carrito)


    }

}

function vaciarCarrito(){

    let carrito = []
    localStorage.setItem('carrito', JSON.stringify(carrito))
    
    let carritoContent = document.querySelector('.carrito__content')
    carritoContent.innerHTML = ''

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


    // Habilitamos el contenido del carrito
    carritoIcon.addEventListener('click', function (){

        console.log('sehizo click en containericon')
        if (carritoContainer.style.display == 'none' || carritoContainer.style.display == ''){
            carritoContainer.style.display = 'flex'
        } else{
            carritoContainer.style.display = 'none'
        }
    })

    // vaciamos el carrito haciendo click en el button
    let vaciarButton = document.querySelector('.carrito__button');

    if (vaciarButton) {
        vaciarButton.addEventListener('click', function(event){
            event.preventDefault();
            vaciarCarrito(); 
            mostrarCarrito();
        });
    }

    mostrarCarrito()

});


document.addEventListener('DOMContentLoaded', function(){

    console.log('cargamoscarritojs')
})


