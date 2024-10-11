
// Obtenemos el username y el password
let inputUsername = document.querySelector('#username');
let inputPassword = document.querySelector('#password');

const APIUrl = 'http://localhost:8080/auth/login'
const baseURL = localStorage.getItem('baseURL')

// Obtenemos el form
let loginForm = document.querySelector('.login__form')

loginForm.addEventListener('submit', function(event) {

    event.preventDefault();

    console.log('formulario al ser enviado')

    let username = inputUsername.value;
    let password = inputPassword.value;

    authenticate(username, password)

})


function authenticate(username, password){

    const auth = {
        username: username,
        password: password
    };


    fetch(APIUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(auth)
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
        window.location.href = baseURL + '/index.html';
    })
}


export function showPassword(){

    // obtenemos el icon y nuestro input
    let icon = document.querySelector('.login__icon')
    let inputPassword = document.querySelector('#inputPassword')

    icon.addEventListener('click', function(){

        if(inputPassword.type == 'password'){
            inputPassword.type = 'text';
            icon.classList.remove('fa-eye')
            icon.classList.add('fa-eye-slash')
        } else {
            inputPassword.type = 'password';
            icon.classList.remove('fa-eye-slash')
            icon.classList.add('fa-eye')
        }

    })
}

showPassword()