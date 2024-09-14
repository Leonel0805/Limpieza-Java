import { crearCards } from './utils/crearCards.js';
import { sendFormSearchParam } from './utils/sendFormSearchParam.js';

const apiURL = "https://miraculous-warmth-production.up.railway.app/api/articulos";

const baseURL = window.location.origin;

async function generarCards() {
    let response = await fetch(apiURL);

    if (response.status === 200) {

        let json = await response.json();
        crearCards(json); 
    }
}

function isLogin(){

    let username = localStorage.getItem('username');
    if(username){
        
        // manipulamos el DOM
        let headerLogin = document.querySelector('.header__session');    
        headerLogin.textContent = username;
        headerLogin.href = baseURL + '/cliente/templates/user/user_me.html';
    }
}


async function init(){

    // isLogin()

    sendFormSearchParam()

    // generamos las cards con su debitas rutas
    await generarCards()

    let token = localStorage.getItem('token')
    console.log(token)

}

init()





