import ApiClient from "@/assets/composables/ApiClient.js";

export async function submitSpymasterTurn(gameId, { cardIds, spyScore, hintContent }) {
    try {
        const response = await ApiClient.post(
            `/game/${gameId}/submit`,
            { cardIds, spyScore, hintContent }
        );
        console.log("Submitted spymaster turn:", response.status);
        return response.status;
    } catch (error) {
        console.log("FULL RESPONSE:", error.response);
        if (error.response) {
            console.log("STATUS:", error.response.status);
            console.log("DATA:", error.response.data);

            return error.response.status;

        } else {
            console.log("NO RESPONSE:", error);
            return null;
        }
    }
}

export async function submitRating(gameId, rating){
    try{
        const response = await ApiClient.patch(
            `/game/game-quality/submit`,
            { gameId, rating }
        );
        return response.status;
    }catch(error){
        console.log(error);
    }
}
