import axios from "axios";
import {getGameId} from "@/assets/composables/VerificationService.js";


export async function patchCards(selectedCards, gameId) {
    try {
        const response = await axios.patch('http://localhost:8082/api/v1/game/updatecards', {
            cardIds: selectedCards.value,
            spymasterPick: true,
            maxScore: selectedCards.value.length,
            gameId: gameId
        })

        console.log("Patched cards:", response.data);
        return response.status;
    } catch (error) {
        console.log(error);
        return error.status;
    }
}