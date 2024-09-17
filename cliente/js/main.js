import { sendFormSearchParam } from './utils/sendFormSearchParam.js';
import { cargarHeader } from './utils/generarHeader.js';
import { isLogin } from './utils/isLogin.js';

function logout(){
    
    let buttonLogout = document.querySelector('#button-logout')

    buttonLogout.addEventListener('click', function(){
        localStorage.clear();

        window.location.href = window.location.origin
        console.log('cerraste sesion')
    })
}



async function init(){

    await cargarHeader()
    
    sendFormSearchParam()

    isLogin()

    logout()
}

document.addEventListener('DOMContentLoaded', async function(){
    init()

});









