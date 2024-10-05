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



async function init(){

    await cargarHeader()
    await cargarFooter()

    sendFormSearchParam()

    isLogin()
    logout()
}

document.addEventListener('DOMContentLoaded', async function(){
    init()
    console.log('termianamos de cargar el main ')

});









