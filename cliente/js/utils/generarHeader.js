export async function cargarHeader(){

    let baseURL = localStorage.getItem('baseURL')

    const response = await fetch(baseURL + '/cliente/templates/utils/header.html');
    const data = await response.text();

    // quiero manipular el dom
    let parser = new DOMParser();
    let doc = parser.parseFromString(data, 'text/html')
    
    // manipulamos el doc y editamos para que siempre pueda llevar al index
    let hrefIndex = doc.querySelector('#logo__href')
    hrefIndex.href = baseURL +'/index.html'

    let headerLogo = doc.querySelector('.header__logo')
    headerLogo.src = baseURL + '/cliente/resources/images/gota-logo.png'

    // manipulamos el iniciar sesion
    let hrefLogin = doc.querySelector('.header__session')
    hrefLogin.href = baseURL + '/cliente/templates/auth/login.html'

    let hrefUser = doc.querySelector('.header__button-menu')
    hrefUser.href = baseURL + '/cliente/templates/user/user_me.html'

    let hrefComprar = doc.querySelector('#carritoComprar')
    hrefComprar.href = baseURL + '/cliente/templates/pages/comprar.html'

    let hrefProductos = doc.querySelector('#productos')
    hrefProductos.href = baseURL + '/index.html'

    let headerHtml = document.querySelector('#header');
    headerHtml.innerHTML = doc.documentElement.innerHTML;

}
