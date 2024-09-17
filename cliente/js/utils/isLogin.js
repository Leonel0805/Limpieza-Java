export async function isLogin(){
    const baseURL = window.location.origin;

    let apiURL = 'http://localhost:8080/api/me'
    let jwtToken = localStorage.getItem('jwt');
    if(jwtToken){
        
        fetch(apiURL, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + jwtToken
            }
        })

        .then(response => {

            if (response.status == 200){
                return response.json()
            }
        })
        .then(json =>{
            // manipulamos el DOM
            let headerLogin = document.querySelector('.header__session');  
            headerLogin.textContent = json.username;
      
            headerLogin.addEventListener('click', function(event){
            // anulamos el href
                event.preventDefault()

                // mostramos el menu
                let menu = document.querySelector('.header__menu')

                // condicionamos si esta vacio o es none, entonces se vuelve block
                if (menu.style.display === '' || menu.style.display === 'none') {
                    menu.style.display = 'block';
                } else {
                    menu.style.display = 'none';
                }
            })
        })
       
 
    }
}
