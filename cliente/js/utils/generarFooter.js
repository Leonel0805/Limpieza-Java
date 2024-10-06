export async function cargarFooter(){

    let nombreApp = '/Limpieza-Java'
    let baseURL = window.location.origin


    //   // si estamos en produccion
    if(baseURL.includes('github.io')){
        baseURL += nombreApp
    }

    const response = await fetch(baseURL + '/cliente/templates/utils/footer.html');
    const data = await response.text();

    let parser = new DOMParser();
    let doc = parser.parseFromString(data, 'text/html')
    
    // lo cargamos
    let headerHtml = document.querySelector('#footer');
    headerHtml.innerHTML = doc.documentElement.innerHTML;

}
