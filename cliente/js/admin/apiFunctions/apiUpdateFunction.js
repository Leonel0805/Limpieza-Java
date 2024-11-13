const apiURL = 'http://localhost:8080/api/articulos'
const jwt = localStorage.getItem('jwt')
const baseURL = localStorage.getItem('baseURL')

let categorias = await obtenerCategorias()


// llamaos al evento cargado para ponerle la funcion
document.addEventListener("articulosCargados", function(){

    console.log('articulos asdfas')
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

            // se llama para poder enviarlo el form
            let message = enviarForm(id)

        })
    })
});




// cargamos edit
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

// creamos el form dinamico
function crearForm(doc, articuloDB){

    const ignoreKeys = ['id']

    let editPanel = doc.querySelector('#editPanel__idObject')


    editPanel.innerHTML = articuloDB.id + '-' + articuloDB.nombre

    let editForm = doc.querySelector('.editPanel__form')

    console.log(editForm)

    for (let [key, value] of Object.entries(articuloDB)) {
        if (!ignoreKeys.includes(key)) {
            // Crear el input y label de forma asíncrona
            console.log(key, value)
            let [input, label] = crearInput(key, value);

            // Agregar los elementos al formulario
            editForm.appendChild(label);
            editForm.appendChild(input);

            console.log('mi input por fuera ' + input.value);
        }
    }
}


// creamos los labels e inputs del form de forma dinamica
function crearInput(key, value){

    // label
    let label = document.createElement('label')
    label.setAttribute('for', key)
    label.textContent = key + ': '

    // input
    let input;


    if(key == 'imageUrl'){
        input = document.createElement('input');
        input.classList.add('editPanel__input')
        input.setAttribute('value', value)
        input.id = key
        input.type= 'file'

    } else{
        input = document.createElement('input');
        input.classList.add('editPanel__input')
        input.setAttribute('value', value)
        input.id = key
        input.type= 'text'
    }

    if(key == 'categoria'){
        input = document.createElement('select')
        input.id = key
        input.name = key

        console.log(categorias)

        categorias.forEach(categoria => {
            let option = document.createElement('option')
            option.value = categoria.name
            option.innerHTML = categoria.name
            input.appendChild(option); // Agregar cada opción al select
        })

    }

    return [input, label]
}

// Obtener todas las categorias para el form Select
async function obtenerCategorias() {

    const apiURL = 'http://localhost:8080/api/categorias'

    return await fetch(apiURL, {
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


// Obtener articulo por id de la database
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


// enviar form
function enviarForm(id){

    let form = document.querySelector('.editPanel__form');


    form.addEventListener('submit', async function(event){

        event.preventDefault();

        let data = {}

        let inputs = document.querySelectorAll('.editPanel__input')
        let select = document.querySelector('select')

        // asignamos en data el input mas su valor
        inputs.forEach(input => {

            if(input.id != 'imageUrl'){
                data[input.id] = input.value
            }
        })

        // asignamos a data la categoria con su valor 
        data[select.id] = {
            name: select.value
        } 

        let fileData = document.querySelector('#imageUrl')

        let file = fileData.files[0];


        await actualizarArticulo(id, data, file)

    })
}



// actualizar articulo en la database
async function actualizarArticulo(id,data, file){

    // creamos formData para poder enviar tanto el articulo como el archivo dentro del body
    let formData = new FormData();

    // especificar que se envia como un application/json
    formData.append('data', new Blob([JSON.stringify(data)], { type: "application/json" }));

    // si tengo el archivo enviarlo en mi request, sino no
    if(file){
        formData.append('file', file);
    }

    // request
    let response = await fetch(apiURL + `/${id}`,{
        method: 'PUT',
        headers: {
            'Authorization': 'Bearer '+ jwt
        },
        body: formData

    })
   

    if (response.status == 200){
        let json = await response.json()
        console.log('pasamos 200',json.message)
        crearMessage(json.message)

    } else {
        let json =  response.json()
        crearMessage(json.message)
    }

}


function crearMessage(message){

    console.log('mostramos message')
    let divmessage = document.querySelector('.message')
    let pmessage = document.querySelector('.message__parrafo')

    pmessage.innerHTML = message
    
    let editPanel = document.querySelector('.editPanel__container')
    let editOverlay = document.querySelector('.editPanel__overlay')
    editOverlay.style.display = 'none'
    editPanel.style.display = 'none'

    divmessage.style.display = 'block'

}