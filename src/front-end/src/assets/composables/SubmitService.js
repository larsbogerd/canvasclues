import axios from "axios";

export async function submitSpymasterTurn(gameId, { cardIds, spyScore, hintContent }) {
    try {
        const response = await axios.post(
            `http://localhost:8082/api/v1/game/${gameId}/submit`,
            { cardIds, spyScore, hintContent}
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
