
// Agregar funcion click agregar y obtener la card
export function buttonAgregar(){

    // obtenemos todos los botones
    let articuloButtons = document.querySelectorAll('.articulo__button')

    articuloButtons.forEach(button => {

        button.addEventListener('click', async function(event){
    
            // obtenemos el articuloCard de ese button
            const articuloCard = event.target.closest('.articulo__card');
            console.log(articuloCard)

            let articuloCreado = await crearArticulo(articuloCard)

            agregarCarrito(articuloCreado)
        })
    })

}

// creamos el objeto obteniendo de la database por id
function crearArticulo(articuloCard){

    let apiURL = 'http://localhost:8080/api/articulos'
    let id = articuloCard.getAttribute('data-id')
    
    // realizamos fetch a la data base
    return fetch(apiURL + `/${id}`)
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
        
        console.log('mi articuloObjeto', articuloObjeto)
        return articuloObjeto        
    })

}

// guardar Carrito en Local Storage
function guardarCarrito(carrito){
    let carritoString = JSON.stringify(carrito)
    localStorage.setItem('carrito', carritoString)
}

// Agregar articulo al carrito guardarlo y mostrar el carrito actualizado
function agregarCarrito(articuloObjeto){

    let carrito = obtenerCarrito()

    let existObject = carrito.find(articulo => articulo.id == articuloObjeto.id)

    if (existObject){
        // si existe le aumentamos la cantidad en 1
        existObject.cantidad += 1;

    } else{
        carrito.push(articuloObjeto)
    }

    guardarCarrito(carrito)

    mostrarCarrito()
}

// Obtener carrito
function obtenerCarrito(){

    let carrito = localStorage.getItem('carrito')

    if (carrito){
        return  JSON.parse(carrito)
    }else{
        return []
    }
}

// Mostrar carrito, generar cards y manipular input
function mostrarCarrito(){

    let carrito = obtenerCarrito()

    if (!verificarButtons()){
        console.log('carrito no vacio ajsdfkasdjf')
        generarCarritoCards(carrito)
        manipularInput()
    }
}

// Vaciar carrito y no mostrar contenido
function vaciarCarrito(){

    let carrito = []
    localStorage.setItem('carrito', JSON.stringify(carrito))
    
    let carritoContent = document.querySelector('.carrito__content')
    carritoContent.innerHTML = ''

}

// Verificar que botones aparecen en el contenido del carrito
function verificarButtons(){

    let carrito = obtenerCarrito()
    let carritoButton = document.querySelector('#carritoVaciarAgregar')
    let carritoComprar = document.querySelector('#carritoComprar')
    let carritoContainer = document.querySelector('.carrito__container')

    if (carrito.length === 0){

        carritoButton.innerHTML = 'Agregá articulos!'
        carritoButton.classList.remove('button--red')
        carritoButton.classList.add('button--vacio', 'button--green')

        carritoComprar.style.display ='none'

        return true

    } else{

        carritoButton.innerHTML = 'Vaciar carrito'
        carritoButton.classList.remove('button--green', 'button--vacio')
        carritoButton.classList.add('button--red')

        carritoContainer.style.display = 'flex'
        carritoComprar.style.display = 'block'

        
        return false
    }

}

// generamos las cards para poner en el contenido del carrito
function generarCarritoCards(carrito){

    let carritoContent = document.querySelector('.carrito__content')
    carritoContent.innerHTML = ''

    carrito.forEach(articulo => {

        carritoContent = document.querySelector('.carrito__content')

        // creamos nuestros elementos para mostrarlos en el carrito
        let carritoCard = document.createElement('div')
        carritoCard.className = 'carrito__card'
        carritoCard.setAttribute('data-id', articulo.id) 
    
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
        carritoCantidad.min = 1
        carritoCantidad.max = 99


        // agregamos todo al  card y al container
        carritoCard.appendChild(carritoImg)
        carritoCard.appendChild(carritoTitle)
        carritoCard.appendChild(carritoCantidad)

        carritoContent.appendChild(carritoCard)

    })

}

// manipular el input de la card del carrito para actualizar el carrito del localStorage
function manipularInput(){

    let carrito = obtenerCarrito() 
    let inputNumbers = document.querySelectorAll('.carrito__cantidad')

    // manipulamos cada input
    inputNumbers.forEach(input => {

        input.addEventListener('input', function(event) {

            // obtenemos el nuevo valor actualizado por input
            console.log('evento actualizado: ', event.target.value, 'type', typeof(event.target.value))
            // obtenemos la card del que se actualizo valor por input
            let carritoCard = event.target.parentElement

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
            guardarCarrito(carrito)
        
            
        })
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

    document.addEventListener('DOMContentLoaded', function(){

        console.log('cargamoooooooooooooooooooo')
        let carritoContainer = document.querySelector('.carrito__container')
        carritoContainer.style.display = 'none'
    })

    mostrarCarrito()


});


// escuchamso una vez que estan cargadas las cards
document.addEventListener('cardsCargadas', async function(){

    let articuloObjeto = buttonAgregar()

    if (articuloObjeto){
        agregarCarrito(articuloObjeto)
    }


})



