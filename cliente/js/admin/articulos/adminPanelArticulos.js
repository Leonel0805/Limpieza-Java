import { init } from "../adminPanelFunctions.js";

let resourcePath = '/articulos'

document.addEventListener("DOMContentLoaded", async () => {

    await init(resourcePath); // Llamar a la función principal después de que el DOM esté cargado
    document.dispatchEvent(new Event('panelCargado'));
});