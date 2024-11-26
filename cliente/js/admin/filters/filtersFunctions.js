import { crearArticulosRow } from "../adminPanelFunctions.js"


let apiURL = 'http://localhost:8080/api/articulos'

document.addEventListener('creamosTablaFilters', function(){

    // Seleccionamos cada td
    let selectCategory = document.querySelector('#selectCategoria') 
    let inputPrecio = document.querySelector('#inputPrecio')
    let selectIsActive = document.querySelector('#selectIsActive')

    let params = {}

    // obtenemos cada value y o guardamos en un objeto params
    selectCategory.addEventListener('change', function(event){
        let opcionCategoria = event.target.value
        params['categoriaValue'] = opcionCategoria
        console.log(opcionCategoria)
        filter(params)

    })

    inputPrecio.addEventListener('input', function(event){
        let opcionPrecio = event.target.value
        params['precioValue'] = opcionPrecio
        console.log(opcionPrecio)
        filter(params)

    })

    selectIsActive.addEventListener('change', function(event){
        let opcionIsActive = event.target.value
        params['isActiveValue'] = opcionIsActive
        console.log(opcionIsActive)
        filter(params)

    })


})

// Filter --> funcion que desglosa el params {} obteniendo los valores y haciendo petición correspondiente de cada valor
async function filter({ categoriaValue, precioValue, isActiveValue }){

    let query = []

    // obtenemos cada valor y guardamos en query
    if (categoriaValue != null && categoriaValue != 'all'){
        query.push(`categoria=${categoriaValue}`)
    }
    if (precioValue != null){
        query.push(`precio=${precioValue}`)
    }
    if (isActiveValue != null && isActiveValue != 'null'){
        query.push(`isActive=${isActiveValue}`)
    }

// cada item de query lo juntamos para hacer la url query
    let paramsURL = ''
    if (query.length > 0 ){
        paramsURL += '?' + query.join('&')
    }

    console.log(apiURL + paramsURL)
    // hacemos petición con los params enviados por url
    let response = await fetch(apiURL + paramsURL)

    let articulos
    if (response.status == 200){
        articulos = await response.json()
    }

    // vaciamos la tabla y mostramos nuevamente los objetos obtenidos
    let tbody = document.querySelector('#tbody');
    tbody.innerHTML = ''

    await crearArticulosRow(articulos)

    // disparamos el evento para poder editar
    document.dispatchEvent(new Event('panelCargado'));

}
