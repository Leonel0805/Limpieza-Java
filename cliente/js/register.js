import { showPassword } from "./utils/showPassword.js";


// Obtener username, email, password
let inputUsername = document.querySelector('#username');
let inputEmail = document.querySelector('#email');
let inputPassword = document.querySelector('#inpuPassword');
let inputPasswordRepeat = document.querySelector('#inputPaswordConfirm');


let registerForm = document.querySelector('.login__form')
const baseURL = localStorage.getItem('baseURL');


// enviamos el formulario y accedemos a los valores

registerForm.addEventListener('submit', function(event) {

    event.preventDefault();

    let usernameValue = inputUsername.value;
    let emailValue = inputEmail.value;
    let passwordValue = inputPassword.value;
    let passwordRepeatValue = inputPasswordRepeat.value;

    // validamos y confirmamos la contraseña

    let auth = {}

    if (passwordValue == passwordRepeatValue){
       
        auth = {
            username: usernameValue,
            email: emailValue,
            password: passwordValue
        }

        localStorage.setItem('authRegister', JSON.stringify(auth));
        window.location.href = baseURL + '/cliente/templates/pages/admin_encargado.html';
    
    } else{

        console.error('error')
    }


})

document.addEventListener('DOMContentLoaded', function() {
    showPassword(); // Llamamos a la función aquí
});