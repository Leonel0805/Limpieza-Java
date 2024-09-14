
// Obtenemos el username y el password
let inputUsername = document.querySelector('#username');
let inputPassword = document.querySelector('#password');

const APIUrl = 'http://localhost:8080/auth/login'

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

        let jwt = json.jwt
        let username = json.username

        console.log(jwt)
        localStorage.setItem('token', jwt)
        localStorage.setItem('username', username)
        
        let baseURL = window.location.origin
        window.location.href = baseURL + '/index.html';
    })
}
