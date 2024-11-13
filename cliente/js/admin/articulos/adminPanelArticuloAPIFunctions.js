import { addButtonDelete } from "../apiFunctions/apiDeleteFunction.js";


let resourcePath = '/articulos'

document.addEventListener("panelCargado", function(){

    addButtonDelete(resourcePath)
})
