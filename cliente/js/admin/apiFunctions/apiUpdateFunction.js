const apiURL = 'http://localhost:8080/api/articulos'
const jwt = localStorage.getItem('jwt')
const baseURL = localStorage.getItem('baseURL')

// llamaos al evento cargado para ponerle la funcion
document.addEventListener("articulosCargados", function(){

    // Ahora puedes acceder a tus elementos con querySelectorAll
    let iconsUpdate = document.querySelectorAll('.fa-pen-to-square');

    iconsUpdate.forEach(icon => {

        icon.addEventListener('click', async function(event){
            
            // accedemos al td de mi icon
            let targetParent = event.target.closest('td');

            // accedemos al padre proximo
            let targetRow = targetParent.parentElement;

            // buscamos dentro de nuestro tr al td con el id
            let id = targetRow.querySelector('.value__id').getAttribute('data-id')

            console.log(id)
            let articuloDB = await obtenerArticulo(id);
            await cargarEdit(articuloDB)

            // window.location.href = './articulosPanel.html'
        })
    })
});



async function cargarEdit(articuloDB){

    let response = await fetch(baseURL + '/cliente/templates/admin/articulos/articuloEditPanel.html')
    let data = await response.text();
  
    //parseamos para que sea un document
    let parser = new DOMParser();
    let doc = parser.parseFromString(data, 'text/html')

    // manipulamos el doc obtenido
    crearForm(doc, articuloDB)

    // Lo agregamos al formulario
    let edit = document.querySelector('.editPanel')
    edit.innerHTML = doc.documentElement.innerHTML
    edit.style.display = 'block'
}

function crearForm(doc, articuloDB){

    const ignoreKeys = ['id']

    let editPanel = doc.querySelector('#editPanel__idObject')


    editPanel.innerHTML = articuloDB.id + '-' + articuloDB.nombre

    Object.entries(articuloDB).forEach(([key, value]) => {
        
        if(!ignoreKeys.includes(key)){

            // creamso los input 
            let [input, label] = crearInput(key, value)
          
            console.log('valor de for value' +value)
            // agregamos al doc
            let editForm = doc.querySelector('.editPanel__form')
            editForm.appendChild(label)
            editForm.appendChild(input)

            console.log('mi input por fuera' + input.value)

        }

    })
}


function crearInput(key, value){

    // label
    let label = document.createElement('label')
    label.setAttribute('for', key)
    label.textContent = key + ': '


    // input
    let input = document.createElement('input')
    input.setAttribute('value', value)
    input.id = key
    input.type= 'text'

    console.log('Valor en input.value después de asignación:', input.value);

    console.log('mis keys y value desde crear input ' +key + value)

    return [input, label]
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
