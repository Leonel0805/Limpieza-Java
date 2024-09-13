import { crearCards } from './utils/crearCards.js';
import { sendFormSearchParam } from './utils/sendFormSearchParam.js';


const apiURL = "https://miraculous-warmth-production.up.railway.app/api/articulos"


fetch(apiURL)
    .then(response => {

        let status = response.status

        console.log(status)

        if(status == 200){
            return response.json()
        }

    })
    .then(json => {
        crearCards(json)
    })


    sendFormSearchParam()

