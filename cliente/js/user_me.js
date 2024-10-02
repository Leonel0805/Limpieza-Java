const apiURL = 'http://localhost:8080/api/me'
let jwt = localStorage.getItem('jwt')

export function obtenerDatos(token){

    return fetch(apiURL, {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token
        }
    })
    .then(response => {
        
        if (response.status == 200){
            return response.json()
        }
    })
    
}


// cargar datos personales form
async function cargarDatos(){

    const keysIgnore = ['id', 'roles', 'pedidos']

    let datos = await obtenerDatos(jwt)

    let rolUser = document.querySelector('#rolUser')
    let usernameUser = document.querySelector('#usernameUser')

    rolUser.innerHTML = datos.roles[0].rol_name
    usernameUser.innerHTML = datos.username

    Object.entries(datos).forEach(([key, value]) =>{

        // si no esta incluido dentro de keysIgnore creamos el parrafo
        if (!keysIgnore.includes(key)){
            crearParrafoDato(key, value)
        }
   
    })
}


function crearParrafoDato(key, value){

    let divDetalles = document.querySelector('.detalles__content')
    let parrafoHTML = document.createElement('p')

    parrafoHTML.className = key
    parrafoHTML.textContent = key + ': '
    

    let spanHTML = document.createElement('span')
    spanHTML.id = key + 'Value'
    spanHTML.innerHTML = value


    parrafoHTML.appendChild(spanHTML)
    divDetalles.appendChild(parrafoHTML)
}


async function init() {

    // En caso de no tener un
    if (await obtenerDatos(jwt)){
        cargarDatos(); 

    } else{
        let baseURL = window.location.origin
        window.location.href = baseURL + '/index.html';
    }

}

init()