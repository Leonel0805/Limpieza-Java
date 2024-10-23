
const apiURL = 'http://localhost:8080/api/articulos/activos'



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
            id: articulo.id,
            name: articulo.nombre,
            cantidad: 1,
            image: articulo.imageUrl
        }
        
        console.log('mi articuloObjeto', articuloObjeto)
        return articuloObjeto        
    })

}

// guardar Carrito en Local Storage
export function guardarCarrito(carrito){
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

    let carritoContainer = document.querySelector('.carrito__container')
    carritoContainer.style.display = 'flex'
    carritoContainer.classList.add('container--minheight')
    mostrarCarrito()

}

// Obtener carrito
export function obtenerCarrito(){

    let carrito = localStorage.getItem('carrito')

    if (carrito){
        return  JSON.parse(carrito)
    }else{
        return []
    }
}

// Mostrar carrito, generar cards y manipular input
async function mostrarCarrito(){

    let carrito = obtenerCarrito()

    if (!verificarButtons()){
        console.log('carrito no vacio ajsdfkasdjf')
        let articulosDB = await obtenerDatosById(carrito)
        generarCarritoCards(articulosDB, carrito)
        manipularInput()
    } 

    contarCarrito(carrito)
}

// Vaciar carrito y no mostrar contenido
export async function vaciarCarrito(){

    let carrito = []
    localStorage.setItem('carrito', JSON.stringify(carrito))
    
    let carritoContent = document.querySelector('.carrito__content')
    carritoContent.innerHTML = ''
    let carritoContainer = document.querySelector('.carrito__container')
    carritoContainer.classList.remove('container--minheight')

}

// Verificar que botones aparecen en el contenido del carrito
function verificarButtons(){

    let carrito = obtenerCarrito()
    let carritoButton = document.querySelector('#carritoVaciarAgregar')
    let carritoComprar = document.querySelector('#carritoComprar')
    let carritoContent = document.querySelector('.carrito__content')

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

        carritoContent.style.display = 'flex'
        carritoComprar.style.display = 'block'

        
        return false
    }

}

// generamos las cards para poner en el contenido del carrito
function generarCarritoCards(articulosDB, carrito){

    let carritoContent = document.querySelector('.carrito__content')
    carritoContent.innerHTML = ''

    articulosDB.forEach(articuloDB => {

        let articuloExist = carrito.find(articulo => articulo.id == articuloDB.id )
        console.log(articuloExist,  'este es mi articulo para generar en el carrito')

        carritoContent = document.querySelector('.carrito__content')

        // creamos nuestros elementos para mostrarlos en el carrito
        let carritoCard = document.createElement('div')
        carritoCard.className = 'carrito__card'
        carritoCard.setAttribute('data-id', articuloDB.id) 

        // creamos el container del img
        let carritoImgContainer = document.createElement('div')
        carritoImgContainer.classList.add('carrito__imgContainer')
    
        // creamos img
        let carritoImg = document.createElement('img')
        carritoImg.className = 'carrito__img'
        carritoImg.setAttribute('alt', 'Imagen')
        carritoImg.src = articuloDB.imageUrl
    
        // creamos eltitle
        let carritoTitle = document.createElement('h3')
        carritoTitle.classList = 'carrito__title'
        carritoTitle.innerHTML = articuloDB.nombre
    
        // creamos input
        let carritoCantidad = document.createElement('input')
        carritoCantidad.className = 'carrito__cantidad'
        carritoCantidad.type ='number'
        carritoCantidad.value = articuloExist.cantidad
        carritoCantidad.min = 1
        carritoCantidad.max = articuloDB.stock


        // agregamos todo al  card y al container
        carritoImgContainer.appendChild(carritoImg)
        carritoCard.appendChild(carritoImgContainer)
        carritoCard.appendChild(carritoTitle)
        carritoCard.appendChild(carritoCantidad)

        carritoContent.appendChild(carritoCard)

    })

}


// contar cuantos articulos hay en carrito
function contarCarrito(carrito){

    let count = carrito.length
    let span = document.querySelector('.carrito__icon__count')

    console.log(count)
    if(count === 0){
        console.log('carrito vacio')
        span.style.display = 'none'

    } else{
        span.innerHTML = count
        span.style.display = 'block'
    
    }



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

    mostrarCarrito()

});


// escuchamso una vez que estan cargadas las cards
document.addEventListener('cardsCargadas', async function(){

    let articuloObjeto = buttonAgregar()

    if (articuloObjeto){
        agregarCarrito(articuloObjeto)
    }


})



