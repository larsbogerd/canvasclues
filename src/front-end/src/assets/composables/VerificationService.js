import axios from "axios";
import router from "../../router.js";


export async function getSelectedCards(selectedCards){
    try{
        const response = await axios.post("http://localhost:8082/api/v1/game/checkcards",
            selectedCards.value
        );
        return response.data;
    } catch(error){
        return error.status
    }
}

export async function getGameId(){
    return router.currentRoute.value.params.gameId;
}

export async function getGrid() {
    try {
        const id = await getGameId()
        const response = await axios.get(`http://localhost:8082/api/v1/game/${id}`);
        return response.data;
    } catch(error) {
        console.log(error);
    }
}
