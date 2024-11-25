import { getAllDatabase } from "../adminPanelFunctions.js"

let divFilter = document.querySelector('.panelAdmin__filters')


let categorias = await getAllDatabase('/categorias')

divFilter.classList.add('filterContainer')

function crearTabla(divContainer){

    // seleccionamos
    let table = document.querySelector('.tableFilters')
    let thead = document.querySelector('.tableFilters__thead')
    let tbody = document.querySelector('.tableFilters__tbody')

    // creamos row para Head
    let tRowHead = document.createElement('tr')
    let tRowBody = document.createElement('tr')


    let listHead = ['categoria', 'precio', 'is active']

    for (let head of listHead){
        
        let th = document.createElement('th')
        th.innerHTML = head
        th.classList.add('thFilterHead')

        tRowHead.appendChild(th)
    }

    
    for (let head of listHead){
        
        let td = document.createElement('td')
        td.classList.add('thFilterBody')

        if (head == 'categoria'){
            let select = document.createElement('select')
            
            for (let categoria of categorias){
                let option = document.createElement('option')
                console.log('for categoria', categoria.name)
                option.setAttribute('value', categoria.name)
                option.innerHTML = categoria.name
                select.appendChild(option)
            }
            
            td.appendChild(select)

        }

        if (head == 'precio'){
            let input = document.createElement('input')
            input.placeholder = 'Precio máximo'
            input.type = 'number'
            input.setAttribute('step', '0.01')

            td.appendChild(input)

        }

        
        if (head == 'is active'){
            let select = document.createElement('select')

            let options = [true, false]
            
            for (let op of options){
                let option = document.createElement('option')
                option.setAttribute('value', op)
                option.innerHTML = op
                select.appendChild(option)
            }
            
            td.appendChild(select)

        }

        tRowBody.appendChild(td)
    }





    thead.appendChild(tRowHead)
    tbody.appendChild(tRowBody)

}

crearTabla(divFilter)