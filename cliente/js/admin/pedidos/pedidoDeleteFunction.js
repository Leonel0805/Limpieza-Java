import { addButtonDelete } from "../apiFunctions/apiDeleteFunction.js";

let resourcePath = '/pedidos'

document.addEventListener("panelCargado", function(){

    addButtonDelete(resourcePath)
})
