export async function isLogin(){
    const baseURL = window.location.origin;

    let username = localStorage.getItem('username');
    if(username){
        
        // manipulamos el DOM
        let headerLogin = document.querySelector('.header__session');   
        console.log(headerLogin) 
        headerLogin.textContent = username;
        headerLogin.href = baseURL + '/cliente/templates/user/user_me.html';
    }
}
