export function crearMessage(message){

    console.log('mostramos message')
    let divmessage = document.querySelector('.message')
    let pmessage = document.querySelector('.message__parrafo')

    pmessage.innerHTML = message
    
    let editPanel = document.querySelector('.editPanel__container')
    let editOverlay = document.querySelector('.editPanel__overlay')

    if (editOverlay){
        editOverlay.style.display = 'none'
    }
    if(editPanel){
        editPanel.style.display = 'none'
    }

    divmessage.style.display = 'block'

}
