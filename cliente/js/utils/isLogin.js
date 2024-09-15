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
            headerLogin.href = baseURL + '/cliente/templates/user/user_me.html';
        })
       
 
    }
}
