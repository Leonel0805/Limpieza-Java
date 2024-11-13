const apiURL = 'http://localhost:8080/api'

const jwt = localStorage.getItem('jwt')

// llamaos al evento cargado para ponerle la funcion

export function addButtonDelete(resourcePath){
    

        // Ahora puedes acceder a tus elementos con querySelectorAll
        let iconsDelete = document.querySelectorAll('.fa-trash-can');
        console.log(iconsDelete);
    
        iconsDelete.forEach(icon => {
    
            icon.addEventListener('click', async function(event){
                
                // accedemos al td de mi icon
                let targetParent = event.target.closest('td');
    
                // accedemos al padre proximo
                let targetRow = targetParent.parentElement;
    
                // buscamos dentro de nuestro tr al td con el id
                let id = targetRow.querySelector('.value__id').getAttribute('data-id')
    
                console.log(id)
                await eliminarArticulo(id, resourcePath);
                window.location.href = './articulosPanel.html'
            })
        })
    
}



async function eliminarArticulo(id, resourcePath){

    await fetch(apiURL + resourcePath +`/${id}`,{
        method: 'DELETE',
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