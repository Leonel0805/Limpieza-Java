
let editButton = document.querySelector('.detalles__card__button')
const APIUrl = 'http://localhost:8080/api/me' 

editButton.addEventListener('click', async function(){

    await cargarEdit()
    sendForm()
    
});

// modificamos para poder salir de actualizar los datos
let body = document.querySelector('body')

body.addEventListener('click', function(){

    let edit = document.querySelector('.edit')

    if (edit.style.display == 'block'){
        edit.style.display = 'none'
    }
})

async function cargarEdit(){
    let baseURL = window.location.origin

    let response = await fetch(baseURL + '/cliente/templates/user/edit_me.html')
    
    let data = await response.text();
  
    //parseamos para que sea un document
    let parser = new DOMParser();
    let doc = parser.parseFromString(data, 'text/html')

    let edit = document.querySelector('.edit')
    edit.innerHTML = doc.documentElement.innerHTML
    edit.style.display = 'block'
    
}

function sendForm(){

    let form = document.querySelector('.edit__form')
    console.log(form)
    
    if(form){
        form.addEventListener('submit', function(event) {
    
            event.preventDefault();

            console.log('Se envió el formulario');

            // Manejamos los datos para enviar y actualizar los datos

            let inputArrays = document.querySelectorAll('.edit__input')
            console.log(inputArrays)
            
            datos = {}
            inputArrays.forEach(input => {
                
                datos[input.id] = input.value
            });

            console.log(datos)

            // hacemos la peticion

            actualizarDatos(datos)
        });
    }
}

function actualizarDatos(datos){
    
    const jwt = localStorage.getItem('jwt')
    
    let request = {
        administrador:datos
    }

    fetch(APIUrl,{
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + jwt
        },
        body: JSON.stringify(request)
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

        let baseURL = window.location.origin
        window.location.href = baseURL + '/cliente/templates/user/user_me.html';
    })
}


