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
            window.location.href = `./cliente/templates/pages/search.html?query=${encodeURIComponent(searchValue)}`;
        }else{
            console.log("no se pudo redireccionar")
        }


    });
}