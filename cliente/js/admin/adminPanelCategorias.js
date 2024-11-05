import { getAllDatabase } from "./adminPanelFunctions.js";
import { crearHeadTable } from "./adminPanelFunctions.js";
import { crearArticulosRow } from "./adminPanelFunctions.js";


let resourcePath = '/categorias'


async function init() {

    // hacemos un Get All a la database
    let json = await getAllDatabase(resourcePath);
    console.log(json)

    // creamos los head de la table, usando un objeto como referencia
    crearHeadTable(json[0])

    // creamos cada una de las rows
    crearArticulosRow(json)

    
}

// Llamar a la función principal
init();