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

    // manipulamos el iniciar sesion
    let hrefLogin = doc.querySelector('.header__session')
    hrefLogin.href = baseURL + '/cliente/templates/auth/login.html'

    let hrefUser = doc.querySelector('.header__button-menu')
    hrefUser.href = baseURL + '/cliente/templates/user/user_me.html'

    let headerHtml = document.querySelector('#header');
    headerHtml.innerHTML = doc.documentElement.innerHTML;

}
