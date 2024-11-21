const apiURL = 'http://localhost:8080/api/encargados'

export async function getAllEncargados(){

    let response = await fetch(apiURL)

    if(response.status == 200){
        return response.json()
    }
}