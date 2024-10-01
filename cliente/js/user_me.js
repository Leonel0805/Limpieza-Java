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

async function cargarDatos(){

    let datos = await obtenerDatos(jwt)

    let usernameHTML = document.querySelector('#usernameValue')
    let emailHTML = document.querySelector('#emailValue')
    let usernameUser = document.querySelector('#usernameUser')
    let rolUser = document.querySelector('#rolUser')

    console.log(datos.roles)

    rolUser.innerHTML = datos.roles[0].rol_name
    usernameUser.innerHTML = datos.username

    usernameHTML.innerHTML = datos.username
    emailHTML.innerHTML = datos.email
}



async function init() {

    if (await obtenerDatos(jwt)){
        await cargarHeader();
        isLogin();
        cargarDatos(); // Cargar datos en la interfaz
        sendFormSearchParam(); 

    } else{
        let baseURL = window.location.origin
        window.location.href = baseURL + '/index.html';
    }

}

document.addEventListener('DOMContentLoaded', init);