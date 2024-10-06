const apiURL = 'http://localhost:8080/api/me'
let jwt = localStorage.getItem('jwt')
const baseURL = localStorage.getItem('baseURL')

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

    parrafoHTML.className = 'detalles__parrafo'

    let spanHTML = document.createElement('span')
    spanHTML.className = 'detalles__bold'
    spanHTML.innerHTML = key 


    const textValue = document.createTextNode(`: ${value}`);

    parrafoHTML.appendChild(spanHTML)
    parrafoHTML.appendChild(textValue)

    divDetalles.appendChild(parrafoHTML)
}


async function init() {

    // En caso de no tener un
    if (await obtenerDatos(jwt)){
        cargarDatos(); 

    } else{
        window.location.href = baseURL + '/index.html';
    }

}


// convertir primera letra
export function capitalizeFistLetter(text){

    if (text != null && text != ''){
        return text[0].toUpperCase() + text.slice(1)
    }
}


init()