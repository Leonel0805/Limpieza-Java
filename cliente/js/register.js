
// Obtener username, email, password
let inputUsername = document.querySelector('#username');
let inputEmail = document.querySelector('#email');
let inputPassword = document.querySelector('#password');

let registerForm = document.querySelector('.login__form')


// enviamos el formulario y accedemos a los valores

registerForm.addEventListener('submit', function(event) {

    event.preventDefault();

    console.log('asdfasdf')
    let usernameValue = inputUsername.value;
    let emailValue = inputEmail.value;
    let passwordValue = password.value;

    // creamos el objeto para enviarlo por body
    let auth = {
        username: usernameValue,
        email: emailValue,
        password: passwordValue
    }

    localStorage.setItem('authLogin', JSON.stringify(auth));
    console.log(auth, + 'enviado')

    const baseURL = window.location.origin;
    window.location.href = baseURL + '/cliente/templates/pages/admin_encargado.html';

})

