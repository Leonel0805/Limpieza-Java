import { decodeJwt } from "../utils/decodeJWT.js";

const apiURL = 'http://localhost:8080/auth/login';
const baseURL = localStorage.getItem('baseURL');

let username = document.querySelector('#inputUsername');
let password = document.querySelector('#inputPassword');

let formLogin = document.querySelector('.login__form');

formLogin.addEventListener('submit', async function(event){

    event.preventDefault();

    let usernameValue = username.value;
    let PasswordValue = password.value;

    let jwt = await authenticate(usernameValue, PasswordValue);

    if (jwt){
        console.log(jwt)
        let decodeJWT = decodeJwt(jwt)

        let authorities = decodeJWT.authorities;
        
        if (authorities.includes('ROLE_ADMIN')){
            
            window.location.href = baseURL + '/cliente/templates/admin/adminPanel.html';
        }
        else{
            console.error('No es admin');
            
        }
    }
    
})


function authenticate(username, password){

    let auth = {
        username:username, 
        password:password
    }

    return fetch(apiURL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(auth)
    })
    .then(response => {

        if (response.status == 200){
            return response.json();
        }
    })
    .then(json => {

        localStorage.setItem('jwt', json.jwt);
        return json.jwt;
    })

}