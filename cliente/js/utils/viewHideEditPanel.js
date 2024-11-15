
export function viewHidePanel(classNamePanelHide, classNameContainerClick, ){

    // seteamos para poder buscar por class
    let pointNamePanel = '.'+ classNamePanelHide
    let pointNameContainer = '.'+ classNameContainerClick

    // buscamos el container al que vamos a dar click por fuera
    let editContainer = document.querySelector(pointNameContainer)        

    // agregamos la funcion click al document 
    document.addEventListener('click', function(event){

        let edit = document.querySelector(pointNamePanel)

        // mostramos el edit 
        if (editContainer.contains(event.target)){
            edit.style.display = 'block'
        } else{
            edit.style.display = 'none'

        }
    })
}