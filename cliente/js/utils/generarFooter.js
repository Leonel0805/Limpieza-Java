export async function cargarFooter(){

    let baseURL = window.location.origin

    const response = await fetch(baseURL + '/cliente/templates/utils/footer.html');
    const data = await response.text();

    let parser = new DOMParser();
    let doc = parser.parseFromString(data, 'text/html')
    
    // lo cargamos
    let headerHtml = document.querySelector('#footer');
    headerHtml.innerHTML = doc.documentElement.innerHTML;

}
