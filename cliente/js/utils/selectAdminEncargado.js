
let buttonAdmin = document.querySelector('#buttonAdmin');
let buttonEncargado = document.querySelector('#buttonEncargado');

const apiURL = 'http://localhost:8080/auth/register';

buttonAdmin.addEventListener('click', function(event){
    event.preventDefault()
    console.log('se presiono button admin')

    // obtener el objeto auth del localStorage
    let authLocal = localStorage.getItem('authRegister');
    if (authLocal) {
        // parseamos y obtenemos el objeto
        let authObject = JSON.parse(authLocal);

        authObject.roles = ['ADMIN'];
        
        register(authObject)
        console.log('registro completado')
    }

})

buttonEncargado.addEventListener('click', function(event){
    event.preventDefault()
    console.log('se presiono button Encargado')


    // obtener el objeto auth del localStorage
    let authLocal = localStorage.getItem('authRegister');
    if (authLocal) {
        // parseamos y obtenemos el objeto
        let authObject = JSON.parse(authLocal);

        authObject.roles = ['ENCARGADO'];
        
        console.log(authObject)
        register(authObject)
        console.log('registro completado')
    }

})


function register(auth){

    fetch(apiURL,{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(auth)
    })
    .then(response => {

        if (response.status == 201){
            return response.json()
        }
    })
    .then(json => {

        console.log(json.jwt)
        
        localStorage.removeItem('authRegister')
        let baseURL = window.location.origin
        window.location.href = baseURL + '/cliente/templates/auth/login.html';
    })
}

