
export function showPassword(){

    // obtenemos el icon y nuestro input
    let icon = document.querySelector('.login__icon')
    console.log('se ejecuto show')

    icon.addEventListener('click', function(){

        console.log(' se hizo click')
        if(inputPassword.type == 'password'){
            inputPassword.type = 'text';
            icon.classList.remove('fa-eye')
            icon.classList.add('fa-eye-slash')
        } else {
            inputPassword.type = 'password';
            icon.classList.remove('fa-eye-slash')
            icon.classList.add('fa-eye')
        }

    })
}
