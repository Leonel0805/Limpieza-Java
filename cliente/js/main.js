import { sendFormSearchParam } from './utils/sendFormSearchParam.js';
import { cargarHeader } from './utils/generarHeader.js';
import { cargarFooter } from './utils/generarFooter.js';

import { isLogin } from './utils/isLogin.js';

function logout(){
    
    console.log("cargamos logout")
    let buttonLogout = document.querySelector('#button-logout')

    buttonLogout.addEventListener('click', function(){
        // 
        console.log('se hizo click')
        localStorage.clear();

        window.location.href = window.location.origin
        console.log('cerraste sesion')
    })
}

function setBaseURL(){

    let nombreApp = '/Limpieza-Java'
    let baseURL = window.location.origin

    console.log('base url' + baseURL)
    // si estamos en produccion
    if(baseURL.includes('github.io')){
        baseURL += nombreApp
    }

    localStorage.setItem('baseURL', baseURL)
}



async function init(){

    setBaseURL()

    await cargarHeader()
    await cargarFooter()

    document.dispatchEvent(new Event('headerFooterCargados'));

    sendFormSearchParam()

    isLogin()
    logout()
}

document.addEventListener('DOMContentLoaded', async function(){
    await init()
    console.log('termianamos de cargar el main ')

});









