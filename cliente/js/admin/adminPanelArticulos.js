
const apiURL = 'http://localhost:8080/api/articulos';


function obtenerArticulos(){

    return fetch(apiURL)
    .then(response => {

        if (response.status == 200){
            return response.json()
        }

    })
    .then(json => {

        return json
    })
}

function crearHeadTable(articulo){

    let listHead = document.querySelector('.articulos_listHead')

    Object.keys(articulo).forEach(key => {

        let tHead = document.createElement('th')
        tHead.classList.add('articulos_itemHead')
        tHead.innerHTML = key

        listHead.appendChild(tHead)

    })
}

function crearArticulosRow(articulos){

    let tbody = document.querySelector('#tbody');

    articulos.forEach(articulo => {

        let articuloRow = document.createElement('tr')
        articuloRow.classList.add('articuloRow')

        Object.values(articulo).forEach(value => {


            console.log(value)
            let tdato = document.createElement('td')
            tdato.classList.add('articulos_value')
            tdato.innerHTML = value
    
            articuloRow.appendChild(tdato)
    
        })

        tbody.appendChild(articuloRow)
    })

  
}




async function init() {

    let json = await obtenerArticulos();
    console.log(json);

    crearHeadTable(json[0])

    crearArticulosRow(json)

    
}

// Llamar a la función principal
init();