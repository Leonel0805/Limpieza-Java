export async function cargarHeader(){

    let baseURL = window.location.origin

    const response = await fetch(baseURL + '/cliente/templates/utils/header.html');
    const data = await response.text();
    
    
    let headerHtml = document.querySelector('#header');
    headerHtml.innerHTML = data;
}
