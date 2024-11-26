import { getAllDatabase } from "../adminPanelFunctions.js";
import { crearArticulosRow } from "../adminPanelFunctions.js"


let apiURL = 'http://localhost:8080/api/articulos'


document.addEventListener('creamosTablaFilters', function(){

    let selectCategory = document.querySelector('#selectCategoria') 
    let inputPrecio = document.querySelector('#inputPrecio')
    let selectIsActive = document.querySelector('#selectIsActive')

    let params = {}

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

async function filter({ categoriaValue, precioValue, isActiveValue }){

    let query = []

    console.log(categoriaValue, 'asdfasdfasdff eeeee')
    if (categoriaValue != null && categoriaValue != 'all'){
        console.log('si es diferente de null')
        query.push(`categoria=${categoriaValue}`)
    }
    if (precioValue != null){
        query.push(`precio=${precioValue}`)
    }
    if (isActiveValue != null && isActiveValue != 'null'){
        query.push(`isActive=${isActiveValue}`)
    }

    console.log(query)

    let paramsURL = ''
    if (query.length > 0 ){
        paramsURL += '?' + query.join('&')
    }

    console.log(apiURL + paramsURL)
    let response = await fetch(apiURL + paramsURL)

    let articulos
    if (response.status == 200){
        articulos = await response.json()
    }

    console.log(articulos)
    let tbody = document.querySelector('#tbody');
    tbody.innerHTML = ''

    await crearArticulosRow(articulos)

}
