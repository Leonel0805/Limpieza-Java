import { init } from "../adminPanelFunctions.js";
import { viewHidePanel } from "../../utils/viewHideEditPanel.js";
import { getObjectById } from "../adminPanelFunctions.js";
import { crearMessage } from "../apiFunctions/crearMessage.js";

const apiURL = 'http://localhost:8080/api/categorias'
const baseURL = localStorage.getItem('baseURL')
const jwt = localStorage.getItem('jwt')
const resourcePath = '/categorias';

document.addEventListener("DOMContentLoaded", function(){
    addCategoria();

});

function addCategoria(){

    let addIcon = document.querySelector('.panelAdminIcon');

    addIcon.addEventListener('click', async function(){
        
        // Hardcodeamos, tiene que ser los fields
        let id = 5

        let categoriaFields = await getObjectById(id, resourcePath);

        await cargarEdit(categoriaFields)

        viewHidePanel('editPanel', 'editPanel__container')

        // se llama para poder enviarlo el form
        enviarForm()
    })

}


// cargamos edit
async function cargarEdit(categoriaFields){

    let response = await fetch(baseURL + '/cliente/templates/admin/editPanel.html')
    let data = await response.text();
  
    //parseamos para que sea un document
    let parser = new DOMParser();
    let doc = parser.parseFromString(data, 'text/html')

    // manipulamos el doc obtenido
    crearForm(doc, categoriaFields)

    // Lo agregamos al formulario
    let edit = document.querySelector('.editPanel')
    edit.innerHTML = doc.documentElement.innerHTML

    edit.style.display = 'block'

}

// creamos el form dinamico
function crearForm(doc, categoriaFields){

    const ignoreKeys = ['id']

    let editPanel = doc.querySelector('#editPanel__idObject')
    let editPanelButton = doc.querySelector('.editPanel__button')


    // editPanel.innerHTML = categoriaDB.id + '-' + categoriaDB.nombre

    let editForm = doc.querySelector('.editPanel__form')


    for (let [key, value] of Object.entries(categoriaFields)) {
        if (!ignoreKeys.includes(key)) {
            // Crear el input y label de forma asíncrona
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
    
    return [input, label]
}

function enviarForm(){

    let form = document.querySelector('.editPanel__form');


    form.addEventListener('submit', async function(event){

        event.preventDefault();

        let data = {}

        let inputs = document.querySelectorAll('.editPanel__input')
        // let selects = document.querySelectorAll('select')

        // asignamos en data el input mas su valor
        inputs.forEach(input => {

            if(input.id != 'imageUrl'){
                data[input.id] = input.value
            }
        })


        await crearCategoria(data)

    })
}



// actualizar articulo en la database
async function crearCategoria(data){

    let bodyData = JSON.stringify(data)

    // request
    let response = await fetch(apiURL,{
        method: 'POST',
        headers: {
            'Content-Type':'application/json',
            'Authorization': 'Bearer '+ jwt
        },
        body: bodyData

    })
   

    if (response.status == 201){
        let json = await response.json()
        
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