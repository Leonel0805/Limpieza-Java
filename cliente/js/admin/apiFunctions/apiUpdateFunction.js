const apiURL = 'http://localhost:8080/api/articulos'
const jwt = localStorage.getItem('jwt')
const baseURL = localStorage.getItem('baseURL')

// llamaos al evento cargado para ponerle la funcion
document.addEventListener("articulosCargados", function(){

    // Ahora puedes acceder a tus elementos con querySelectorAll
    let iconsUpdate = document.querySelectorAll('.fa-pen-to-square');

    iconsUpdate.forEach(icon => {

        icon.addEventListener('click', async function(event){
            
            await cargarEdit()
            // accedemos al td de mi icon
            let targetParent = event.target.closest('td');

            // accedemos al padre proximo
            let targetRow = targetParent.parentElement;

            // buscamos dentro de nuestro tr al td con el id
            let id = targetRow.querySelector('.value__id').getAttribute('data-id')

            console.log(id)
            let articuloDB = await obtenerArticulo(id);
            // window.location.href = './articulosPanel.html'
        })
    })
});



async function cargarEdit(){

    let response = await fetch(baseURL + '/cliente/templates/admin/articulos/articuloEditPanel.html')
    let data = await response.text();
  
    //parseamos para que sea un document
    let parser = new DOMParser();
    let doc = parser.parseFromString(data, 'text/html')

    // manipulamos el doc obtenido
    crearForm(doc)

    // Lo agregamos al formulario
    let edit = document.querySelector('.editPanel')
    edit.innerHTML = doc.documentElement.innerHTML
    edit.style.display = 'block'
    
}

function crearForm(doc){


}


async function obtenerArticulo(id){

    return await fetch(apiURL + `/${id}`,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer '+ jwt
        }
    })
    .then(response => {

        if (response.status == 200){
            return response.json()
        }
    })
    .then(json => {
        return json
    })
}


async function actualizarArticulo(id){

    await fetch(apiURL + `/${id}`,{
        method: 'UPDATE',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer '+ jwt
        }
    })
    .then(response => {

        if (response.status == 200){
            return response.json()
        }
    })
    .then(json => {
        console.log(json)
    })
 
}
