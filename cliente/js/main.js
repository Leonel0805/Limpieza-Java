import { crearCards } from './utils/crearCards.js';
import { sendFormSearchParam } from './utils/sendFormSearchParam.js';
import { cargarHeader } from './utils/generarHeader.js';
import { isLogin } from './utils/isLogin.js';


const apiURL = "https://miraculous-warmth-production.up.railway.app/api/articulos";


async function generarCards() {
    let response = await fetch(apiURL);

    if (response.status === 200) {

        let json = await response.json();
        crearCards(json); 
    }
}

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

    let header = document.querySelector('.header__session')
    console.log(header)
    header.addEventListener('click', function(){
        console.log('hice click')
    })

    
    sendFormSearchParam()

    // generamos las cards con su debitas rutas
    await generarCards()

    isLogin()

    logout()
}

document.addEventListener('DOMContentLoaded', function(){
    init()

    

});






