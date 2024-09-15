import { cargarHeader } from './utils/generarHeader.js';
import { isLogin } from './utils/isLogin.js';
import { sendFormSearchParam } from './utils/sendFormSearchParam.js';

const apiURL = 'http://localhost:8080/api/me'
const jwt = localStorage.getItem('jwt')

function obtenerDatos(){

    fetch(apiURL, {
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
    .then(json => {

        cargarDatos(json)
    })
    
}

function cargarDatos(datos){

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
    await cargarHeader();
    isLogin();
    obtenerDatos()
    sendFormSearchParam();
}

document.addEventListener('DOMContentLoaded', init);