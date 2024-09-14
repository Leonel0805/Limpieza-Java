// search
export function sendFormSearchParam(){
    
    let inputSearch = document.querySelector('#inputSearch')
    console.log(inputSearch)

    let formSearch = document.querySelector('#formSearch')
    console.log(formSearch)

    formSearch.addEventListener('submit', function(event){

        event.preventDefault();
        console.log("Formulario al enviar")
        let searchValue = inputSearch.value;

        if (searchValue){
    
            let baseUrl = `${window.location.origin}/cliente/templates/pages/search.html`;
            window.location.href = `${baseUrl}?query=${encodeURIComponent(searchValue)}`;
        }else{
            console.log("no se pudo redireccionar")
        }


    });
}