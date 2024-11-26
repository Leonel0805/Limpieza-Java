import { getAllDatabase } from "../adminPanelFunctions.js"

let divFilter = document.querySelector('.panelAdmin__filters')

let categorias = []
async function cargarCategorias() {
    categorias = await getAllDatabase('/categorias'); 
    console.log( 'asdfasdf', categorias)

}

divFilter.classList.add('filterContainer')

function crearTabla(){

    // seleccionamos
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
        td.classList.add('tdFilterBody')

        if (head == 'categoria'){
            let select = document.createElement('select')
            select.id = 'selectCategoria'
            select.name = 'categorias'

            let option = document.createElement('option')
            option.setAttribute('value', 'all')
            option.innerHTML = 'Todos'
            select.appendChild(option)


            for (let categoria of categorias){
                console.log('cargamos cada cateogoria', categoria)
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
            input.id = 'inputPrecio'
            input.placeholder = 'Precio máximo'
            input.type = 'number'
            input.setAttribute('step', '0.01')

            td.appendChild(input)

        }

        if (head == 'is active'){
            let select = document.createElement('select')
            select.id = 'selectIsActive'
            select.name = 'isActive'

            let option = document.createElement('option')
            option.setAttribute('value', null)
            option.innerHTML = '-'
            select.appendChild(option)

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

    document.dispatchEvent(new Event('panelCargado'));


}


document.addEventListener('DOMContentLoaded', async function(){
    
    // cargamos categorias
    await cargarCategorias()
    crearTabla()

    document.dispatchEvent(new Event('creamosTablaFilters'));


})
