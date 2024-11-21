import { init } from "../adminPanelFunctions.js";
import { viewHidePanel } from "../../utils/viewHideEditPanel.js";
import { getObjectById } from "../adminPanelFunctions.js";
import { crearMessage } from "../apiFunctions/crearMessage.js";
import { StatusEnum } from "./pedidosEstado.js";
import { getAllEncargados } from "../../utils/getEncargados.js";

const apiURL = 'http://localhost:8080/api/pedidos'
const baseURL = localStorage.getItem('baseURL')
const jwt = localStorage.getItem('jwt')
const resourcePath = '/pedidos';


let encargados = []
async function cargarEncargados() {
    encargados = await getAllEncargados(); 
    console.log(encargados); 
}


document.addEventListener("panelCargado", function(){

    cargarEncargados()
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
async function crearForm(doc, pedidoDB){

    console.log('PedidoDB:', pedidoDB);

    const ignoreKeys = ['id']

    let editPanel = doc.querySelector('#editPanel__idObject')
    let editPanelButton = doc.querySelector('.editPanel__button')
    let edtiPanelTitle = doc.querySelector('.editPanel__title')

    edtiPanelTitle.firstChild.nodeValue = "Editar: ";

    editPanel.innerHTML = 'pedido '+ pedidoDB.id 

    let editForm = doc.querySelector('.editPanel__form')

    // Usamos un forEach para manejar la asíncronía
    await Promise.all(Object.entries(pedidoDB)
    .filter(([key]) => !ignoreKeys.includes(key))
    .map(async ([key, value]) => {
        try {
            console.log('mi key y value', key, value);
            let [input, label] = crearInput(key, value);
            console.log('Creando elementos:', input, label);

            // Insertar elementos en el DOM
            editForm.insertBefore(label, editPanelButton);
            editForm.insertBefore(input, editPanelButton);
        } catch (error) {
            console.error('Error al crear o insertar el elemento:', error);
        }
    })
    );
}

function crearInput(key, value){

    // label
    let label = document.createElement('label')
    label.setAttribute('for', key)
    label.textContent = key + ': '

    // input
    let input;


    if(key == 'estado'){
        input = document.createElement('select')
        input.id = key
        input.name = key

        Object.values(StatusEnum).forEach(optionValue => {

            let optionHtml = document.createElement('option')
            // seleccinamos la opción que tiene de estado
            if (optionValue == value){
                optionHtml.setAttribute('selected', true)
            }
            optionHtml.value = optionValue
            optionHtml.innerHTML = optionValue
            
            input.appendChild(optionHtml);
        });

    } else if(key == 'fecha_creacion'){

        input = document.createElement('input')
        let newValue = value.split('.')[0]
        console.log('mi fecha', value)
        input.id = key
        input.name = key
        input.type = 'datetime-local'
        
        input.setAttribute('value', newValue) 
        input.setAttribute('readonly', true)
 
 
    } else if(key == 'encargado'){

        let encargadoIn = value

        input = document.createElement('select')
        input.id = key
        input.name = key

        encargados.forEach(encargado => {

            
            let option = document.createElement('option')
            if (encargado.username == encargadoIn.username){
                option.setAttribute('selected', true)
            }
            // ponemos el username, en vez del id porque backend busca por username
            option.value = encargado.username
            option.innerHTML = encargado.username
            input.appendChild(option);
        });

        console.log(input)
    } 
    
    else {
        input = document.createElement('input');
        input.classList.add('editPanel__input')
        input.setAttribute('value', value)
        input.id = key
        input.type= 'text'
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

            
            if (select.id == 'encargado'){
                data['nombre_encargado'] = select.value
            } else {
                data[select.id] = select.value

            }
        })

        console.log(data)
        await actualizarPedido(id, data)

    })
}



// actualizar articulo en la database
async function actualizarPedido(id,data){

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
