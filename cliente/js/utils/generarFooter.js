const baseURL = localStorage.getItem('baseURL')

export async function cargarFooter(){

    const response = await fetch(baseURL + '/cliente/templates/utils/footer.html');
    const data = await response.text();

    let parser = new DOMParser();
    let doc = parser.parseFromString(data, 'text/html')
    

    let footerLogo = doc.querySelector('.footer__logo-img')
    footerLogo.src = baseURL + '/cliente/resources/images/gota-logo.png'

    // lo cargamos
    let headerHtml = document.querySelector('#footer');
    headerHtml.innerHTML = doc.documentElement.innerHTML;

}
