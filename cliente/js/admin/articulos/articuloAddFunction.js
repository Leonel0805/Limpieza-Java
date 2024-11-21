import { viewHidePanel } from "../../utils/viewHideEditPanel.js";
import { init } from "../adminPanelFunctions.js";
import { crearMessage } from "../apiFunctions/crearMessage.js";

const baseURL = localStorage.getItem('baseURL')
const apiURL = 'http://localhost:8080/api/articulos'
const jwt = localStorage.getItem('jwt')
const resourcePath = '/articulos'


let categorias = []
async function cargarCategorias() {
    categorias = await obtenerCategorias(); 
    console.log(categorias); 
}


// Obtener todas las categorias para el form Select
async function obtenerCategorias() {

    const apiURL = 'http://localhost:8080/api/categorias'

    let response =  await fetch(apiURL, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer '+ jwt
        }
    })

    if (response.status == 200){
        return response.json()
    }
 
}



function addArticulo(){

    let addIcon = document.querySelector('.panelAdminIcon')

    addIcon.addEventListener('click', async function(){


        let response = await fetch(apiURL + '/fields')
        let articuloFields = await response.json()

        await cargarPanel(articuloFields)

        viewHidePanel('editPanel', 'editPanel__container')

        enviarForm()

    })
}


document.addEventListener("DOMContentLoaded", function () {

    cargarCategorias()
    addArticulo()
});


async function cargarPanel(articuloFields){

    let response = await fetch(baseURL + '/cliente/templates/admin/editPanel.html')
    let data = await response.text();
  
    //parseamos para que sea un document
    let parser = new DOMParser();
    let doc = parser.parseFromString(data, 'text/html')

    // manipulamos el doc obtenido
    crearForm(doc, articuloFields)

    // Lo agregamos al formulario
    let edit = document.querySelector('.editPanel')
    edit.innerHTML = doc.documentElement.innerHTML

    edit.style.display = 'block'
}

function crearForm(doc, articuloFields){


    let ignoreFields = ['id']

    let editPanel = doc.querySelector('#editPanel__idObject')
    let editPanelButton = doc.querySelector('.editPanel__button')


    // editPanel.innerHTML = articuloDB.id + '-' + articuloFields.nombre

    let editForm = doc.querySelector('.editPanel__form')

    for (let field of articuloFields) {

        let nameField = field.name
    
        if(!ignoreFields.includes(nameField)){
            let [input, label] = crearInput(nameField)
            // Agregar los elementos al formulario
            editForm.insertBefore(label, editPanelButton);
            editForm.insertBefore(input, editPanelButton);
        }

    }
}


// creamos los labels e inputs del form de forma dinamica
function crearInput(nameField){

    // label
    let label = document.createElement('label')
    label.setAttribute('for', nameField)
    label.textContent = nameField + ': '

    // input
    let input;

    if(nameField == 'imageUrl'){
        input = document.createElement('input');
        input.classList.add('editPanel__input')
        // input.setAttribute('value', value)
        input.id = nameField
        input.type= 'file'

    } else{
        input = document.createElement('input');
        input.classList.add('editPanel__input')
        // input.setAttribute('value', value)
        input.id = nameField
        input.type= 'text'
    }

    if(nameField == 'categoria'){
        input = document.createElement('select')
        input.id = nameField
        input.name = nameField

        console.log(categorias)

        categorias.forEach(categoria => {
            let option = document.createElement('option')

    
            option.value = categoria.name
            option.innerHTML = categoria.name
            input.appendChild(option); // Agregar cada opción al select
        })

    }

    if(nameField == 'is_active'){
        input = document.createElement('select')
        input.id = nameField
        input.name = nameField

        let listOptions = ['true', 'false']

        listOptions.forEach(option => {
            let optionHtml = document.createElement('option')
            optionHtml.value = option
            optionHtml.innerHTML = option
            input.appendChild(optionHtml);
        })

    }

    return [input, label]
}

// enviar form
function enviarForm(){

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

        // asignamos a data la categoria con su valor 
        console.log(selects)

        selects.forEach(select => {

            if (select.id == 'categoria') {
                data[select.id] = {
                    name: select.value
                };
            } else {
                data[select.id] = select.value;
            }

        })
     
      

        let fileData = document.querySelector('#imageUrl')

        let file = fileData.files[0];

        console.log(data)

        await actualizarArticulo(data, file)

    })
}

async function actualizarArticulo(data, file){

    // creamos formData para poder enviar tanto el articulo como el archivo dentro del body
    let formData = new FormData();

    // especificar que se envia como un application/json
    formData.append('data', new Blob([JSON.stringify(data)], { type: "application/json" }));

    // si tengo el archivo enviarlo en mi request, sino no
    if(file){
        formData.append('file', file);
    }

    // request
    let response = await fetch(apiURL, {
        method: 'POST',
        headers: {
            'Authorization': 'Bearer '+ jwt
        },
        body: formData

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

