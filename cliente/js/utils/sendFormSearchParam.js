// search
export function sendFormSearchParam(){
    
    let baseUrl = localStorage.getItem('baseURL')
    
    let inputSearch = document.querySelector('#inputSearch')
    console.log(inputSearch)

    let formSearch = document.querySelector('#formSearch')
    console.log(formSearch)

    formSearch.addEventListener('submit', function(event){

        event.preventDefault();
        console.log("Formulario al enviar")
        let searchValue = inputSearch.value;

        if (searchValue){
    
            let pathUrl = `${baseUrl}/cliente/templates/pages/search.html`;
            window.location.href = `${pathUrl}?query=${encodeURIComponent(searchValue)}`;
            console.log('se usa el sendfgorm')
        }else{
            console.log("no se pudo redireccionar")
        }


    });
}