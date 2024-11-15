import { addButtonDelete } from "../apiFunctions/apiDeleteFunction.js";

let resourcePath = '/categorias'

document.addEventListener("panelCargado", function(){

    addButtonDelete(resourcePath)
})
