import { getAllDatabase } from "../adminPanelFunctions.js";


let resourcePath = '/categorias'

let categorias = await getAllDatabase(resourcePath)

console.log('mis categorias', categorias)

