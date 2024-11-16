import { init } from "../adminPanelFunctions.js";
import { viewHidePanel } from "../../utils/viewHideEditPanel.js";
import { getObjectById } from "../adminPanelFunctions.js";
import { crearMessage } from "../apiFunctions/crearMessage.js";

import { StatusEnum } from "./pedidosEstado.js";

const apiURL = 'http://localhost:8080/api/pedidos'
const baseURL = localStorage.getItem('baseURL')
const jwt = localStorage.getItem('jwt')
const resourcePath = '/pedidos';

document.addEventListener("panelCargado", function(){

    addIconUpdateFuntion();

});

function addIconUpdateFuntion(){

    let iconsUpdate = document.querySelectorAll('.fa-pen-to-square');

    iconsUpdate.forEach(icon => {

        icon.addEventListener('click', async function(event){
            
            // accedemos al td de mi icon
            let targetParent = event.target.closest('td');

            // accedemos al padre proximo
            let targetRow = targetParent.parentElement;

            // buscamos dentro de nuestro tr al td con el id
            let id = targetRow.querySelector('.value__id').getAttribute('data-id')

            console.log(id)

            let pedidoDB = await getObjectById(id, resourcePath);

            console.log('mi pedido' +pedidoDB)

            await cargarEdit(pedidoDB)

            viewHidePanel('editPanel', 'editPanel__container')

            // se llama para poder enviarlo el form
            enviarForm(id)
        })
    })
}


// cargamos edit
async function cargarEdit(pedidoDB){

    let response = await fetch(baseURL + '/cliente/templates/admin/editPanel.html')
    let data = await response.text();
  
    //parseamos para que sea un document
    let parser = new DOMParser();
    let doc = parser.parseFromString(data, 'text/html')

    // manipulamos el doc obtenido
    crearForm(doc, pedidoDB)

    // Lo agregamos al formulario
    let edit = document.querySelector('.editPanel')
    edit.innerHTML = doc.documentElement.innerHTML

    edit.style.display = 'block'

}

// creamos el form dinamico
function crearForm(doc, pedidoDB){

    const ignoreKeys = ['id']

    let editPanel = doc.querySelector('#editPanel__idObject')
    let editPanelButton = doc.querySelector('.editPanel__button')


    editPanel.innerHTML = pedidoDB.id 

    let editForm = doc.querySelector('.editPanel__form')


    for (let [key, value] of Object.entries(pedidoDB)) {
        if (!ignoreKeys.includes(key)) {
            // Crear el input y label de forma asíncrona
            console.log(key, value)
            let [input, label] = crearInput(key, value);

            // Agregar los elementos al formulario
            editForm.insertBefore(label, editPanelButton);
            editForm.insertBefore(input, editPanelButton);

        }
    }
}

function crearInput(key, value){

    // label
    let label = document.createElement('label')
    label.setAttribute('for', key)
    label.textContent = key + ': '

    // input
    let input;

    input = document.createElement('input');
    input.classList.add('editPanel__input')
    input.setAttribute('value', value)
    input.id = key
    input.type= 'text'

    if(key == 'estado'){
        input = document.createElement('select')
        input.id = key
        input.name = key


        Object.values(StatusEnum).forEach(value => {

            let optionHtml = document.createElement('option')
            optionHtml.value = value
            optionHtml.innerHTML = value
            input.appendChild(optionHtml);
        });

    }
    
    return [input, label]
}

function enviarForm(id){

    let form = document.querySelector('.editPanel__form');


    form.addEventListener('submit', async function(event){

        event.preventDefault();

        let data = {}

        let inputs = document.querySelectorAll('.editPanel__input')
        let selects = document.querySelectorAll('select')

        // asignamos en data el input mas su valor
        inputs.forEach(input => {

            if(input.id != 'imageUrl'){
                data[input.id] = input.value
            }
        })

        selects.forEach(select => {

            if(select.id == 'estado'){
                data[select.id] = select.value
            }
        })

        await actualizarArticulo(id, data)

    })
}



// actualizar articulo en la database
async function actualizarArticulo(id,data){

    let bodyData = JSON.stringify(data)

    console.log(bodyData)

    // request
    let response = await fetch(apiURL + `/${id}`,{
        method: 'PUT',
        headers: {
            'Content-Type':'application/json',
            'Authorization': 'Bearer '+ jwt
        },
        body: bodyData

    })
   

    if (response.status == 200){
        let json = await response.json()
        console.log('pasamos 200',json.message)
        
        let thead = document.querySelector('#thead')
        let tbody = document.querySelector('#tbody')

        thead.innerHTML = ''
        tbody.innerHTML = ''
        await init(resourcePath)
        crearMessage(json.message)
        document.dispatchEvent(new Event('panelCargado'));
        

    } else {
        let json =  await response.json()
        return json.message
    }

}