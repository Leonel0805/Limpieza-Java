import { cargarHeader } from './utils/generarHeader.js';
import { isLogin } from './utils/isLogin.js';
import { sendFormSearchParam } from './utils/sendFormSearchParam.js';

const apiURL = 'http://localhost:8080/api/me'
let jwt = localStorage.getItem('jwt')

export function obtenerDatos(token){

    return fetch(apiURL, {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token
        }
    })
    .then(response => {
        
        if (response.status == 200){
            return response.json()
        }
    })
    
}

export function obtenerDatosForm(jwt){

    return fetch('http://localhost:8080/api/me/update', {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + jwt
        }
    })
    .then(response => {

        if (response.status == 200){
            return response.json()
        }
    })

}
    


async function cargarDatos(){

    let datos = await obtenerDatosForm(jwt)


    Object.entries(datos).forEach(([key, value]) =>{

        console.log(key, value)
        crearParrafoDato(key, value)
    })
}


function crearParrafoDato(key, value){

    let divDetalles = document.querySelector('.detalles__content')
    let parrafoHTML = document.createElement('p')

    parrafoHTML.className = key
    parrafoHTML.textContent = key + ': '
    

    let spanHTML = document.createElement('span')
    spanHTML.id = key + 'Value'
    spanHTML.innerHTML = value


    parrafoHTML.appendChild(spanHTML)
    divDetalles.appendChild(parrafoHTML)
}


async function init() {

    // En caso de no tener un
    if (await obtenerDatos(jwt)){
        cargarDatos(); 
        sendFormSearchParam(); 

    } else{
        let baseURL = window.location.origin
        window.location.href = baseURL + '/index.html';
    }

}

init()