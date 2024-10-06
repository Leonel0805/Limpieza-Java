const baseURL = localStorage.getItem('baseURL')

export function logout(){
    
    let buttonLogout = document.querySelector('#button-logout')

    buttonLogout.addEventListener('click', function(){
        // 
        console.log('se hizo click')
        localStorage.clear();

        window.location.href = baseURL
        console.log('cerraste sesion')
    })
}