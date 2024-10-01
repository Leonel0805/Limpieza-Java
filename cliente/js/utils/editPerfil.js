import { obtenerDatos } from '../user_me.js';
import { obtenerDatosForm } from '../user_me.js';


let editButton = document.querySelector('.detalles__card__button')
const APIUrl = 'http://localhost:8080/api/me' 
const jwt = localStorage.getItem('jwt')

let userFormCampos = await obtenerDatosForm(jwt)
let userDatos = await obtenerDatos(jwt)

editButton.addEventListener('click', async function(){

    await cargarEdit()
    sendForm()
    
    // modificamos para poder salir de actualizar los datos
    let editContainer = document.querySelector('.edit__container')


    document.addEventListener('click', function(event){

        let edit = document.querySelector('.edit')


        if (editContainer.contains(event.target)){
            edit.style.display = 'block'
        } else{
            edit.style.display = 'none'

        }
    })

});


async function cargarEdit(){


    let baseURL = window.location.origin

    let response = await fetch(baseURL + '/cliente/templates/user/edit_me.html')
    
    let data = await response.text();
  
    //parseamos para que sea un document
    let parser = new DOMParser();
    let doc = parser.parseFromString(data, 'text/html')

    // manipulamos el doc obtenido

  
    crearInput(doc)

    // Lo agregamos al formulario


    let edit = document.querySelector('.edit')
    edit.innerHTML = doc.documentElement.innerHTML
    edit.style.display = 'block'
    
}


function crearInput(doc){
      
    let form = doc.querySelector('.edit__form')
    let submitButton = doc.querySelector('.button__principal');

    console.log('buton' + submitButton)
    Object.keys(userFormCampos).forEach(key => {


        let newLabel = document.createElement('label');
        newLabel.setAttribute('for', key)
        newLabel.textContent = key+ ':'

        let newInput = document.createElement('input');
        newInput.type = 'text';
        newInput.className = 'edit__input'
        newInput.id = key;

        newInput.classList.add('nuevo-input');


        form.insertBefore(newLabel, submitButton)
        form.insertBefore(newInput, submitButton)

    })

   // Agregamos una clase para estilo si es necesario
}

function sendForm(){

    let form = document.querySelector('.edit__form')
    
    if(form){
        form.addEventListener('submit', function(event) {
    
            event.preventDefault();

            console.log('Se envió el formulario');

            // Manejamos los datos para enviar y actualizar los datos
            let inputArrays = document.querySelectorAll('.edit__input')
            console.log(inputArrays)
            
            let datos = {}
            inputArrays.forEach(input => {
                
                datos[input.id] = input.value
            });

            console.log(datos)

            // hacemos la peticion
            actualizarDatos(datos)
        });
    }
}

// necesito actualizar los datos enviando un PATCH
async function actualizarDatos(datos){
    
    let request = {}

    // Condicionamos el rol el rol 

    if (userDatos.roles[0].rol_name == 'ADMIN'){
        request = {
            administrador:datos
        }
    } else if(userDatos.roles[0].rol_name == 'ENCARGADO'){
        
        request = {
            encargado:datos
        }
    }

    console.log(request)

    if (request){
        fetch(APIUrl,{
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + jwt
            },
            body: JSON.stringify(request)
        })
        .then(response => {
            
            if(response.status == 200){
                return response.json()
            }
        })
        .then(json => {
    
            // guardamos el jwt en localStorage
            let jwt = json.jwt
    
            localStorage.setItem('jwt', jwt)
    
            let baseURL = window.location.origin
            window.location.href = baseURL + '/cliente/templates/user/user_me.html';
        })
    }
}




