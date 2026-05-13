import axios from "axios";


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
