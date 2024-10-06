export async function cargarHeader(){

    let nombreApp = '/Limpieza-Java'
    let baseURL = window.location.origin

    console.log('base url' + baseURL)

    // si estamos en produccion
    if(baseURL.includes('github.io')){
        baseURL += nombreApp
    }

    const response = await fetch(baseURL + '/cliente/templates/utils/header.html');
    const data = await response.text();

    // quiero manipular el dom
    let parser = new DOMParser();
    let doc = parser.parseFromString(data, 'text/html')
    
    // manipulamos el doc y editamos para que siempre pueda llevar al index
    let hrefIndex = doc.querySelector('#logo__href')
    hrefIndex.href = baseURL +'/index.html'

    let headerHtml = document.querySelector('#header');
    headerHtml.innerHTML = doc.documentElement.innerHTML;

}
