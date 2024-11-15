const apiURL = 'http://localhost:8080/api';

function getAllDatabase(resourcePath){

    return fetch(apiURL + resourcePath)
    .then(response => {

        if (response.status == 200){
            return response.json()
        }

    })
    .then(json => {

        return json
    })
}

function crearHeadTable(objectsDB){

    let theadTable = document.querySelector('#thead')

    let listHead = document.createElement('tr')
    listHead.classList.add('table__listHead')

    Object.keys(objectsDB).forEach(key => {

        let tHead = document.createElement('th')
        tHead.classList.add('table__itemHead')

        let newkey = key.replace('_', ' ')
        tHead.innerHTML = newkey

        listHead.appendChild(tHead)
    })


    // creamos los th add para editar y eliminar
    let listHeadDefault = ['Editar', 'Eliminar']

    listHeadDefault.forEach(element => {
        let theadADD = document.createElement('th')
        theadADD.classList.add('table__itemHead')
        theadADD.innerHTML = element
        listHead.appendChild(theadADD)

    })

    theadTable.appendChild(listHead)
}

async function crearArticulosRow(allObjectsDB){

    let tbody = document.querySelector('#tbody');

    // iteramos por cada objecto de la lista
    allObjectsDB.forEach(objectDB => {

        let articuloRow = document.createElement('tr')
        articuloRow.classList.add('tableRow')

        // iteramos los valores de cada objecto
        Object.entries(objectDB).forEach(([key, value]) => {

            let tdato = document.createElement('td')
            tdato.classList.add('table__value')

            if(key == 'id'){
                tdato.setAttribute('data-id', value)
                tdato.classList.add('value__id')
            } 
            

            tdato.innerHTML = value
            
            if(key == 'imageUrl' ){

                tdato.innerHTML = ''
                let href = document.createElement('a')
                href.setAttribute('href', value)
                href.setAttribute('target', '_blank')
                href.textContent = value
                tdato.appendChild(href)
            }

            if(key == 'categoria'){
                tdato.innerHTML = value.name
            }
    
            // agregamos a la row cada uno de los valores
            articuloRow.appendChild(tdato)
    
        })


        let listIconsDefault = [
            {
                icon: 'fa-pen-to-square'
            },
            {
                icon: 'fa-trash-can'
            }
        ]

        listIconsDefault.forEach(object => {
        
            let tdato = document.createElement('td')
            tdato.classList.add('table__value')

            let icon = document.createElement('i')
            icon.classList.add('fa-regular','table__icon', object.icon)

            tdato.appendChild(icon)
            // agregamos a la row cada uno de los valores
            articuloRow.appendChild(tdato)
        })

        tbody.appendChild(articuloRow)

    })

  
}

export async function init(resourcePath) {

    // hacemos un Get All a la database
    let json = await getAllDatabase(resourcePath);
    console.log(json)

    // creamos los head de la table, usando un objeto como referencia
    await crearHeadTable(json[0])

    // creamos cada una de las rows
    await crearArticulosRow(json)

}