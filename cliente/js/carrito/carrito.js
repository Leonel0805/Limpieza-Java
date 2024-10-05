let carrito = []

// articulo tiene que ser objeto
export function agregar(){

    let articulosIDs = document.querySelectorAll('.articulo__card')
    let articuloButton = document.querySelectorAll('.articulo__button')

    console.log(articulosIDs)
    console.log(articuloButton)

    articuloButton.forEach(button => {

        button.addEventListener('click', function(event){
    
            const articuloCard = event.target.parentElement;

            crearArticulo(articuloCard)
            console.log('se hizo click')
            mostrarCarrito()
        })
    })

}

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
            name : articulo.nombre
        }
        
        agregarCarrito(articuloObjeto)
    })


}

function agregarCarrito(articuloObjeto){

    carrito.push(articuloObjeto)
}

function mostrarCarrito(){
    console.log(carrito)
}