import axios from "axios";

export async function submitSpymasterTurn(gameId, { cardIds, maxScore, hintContent }) {
    try {
        const response = await axios.post(
            `http://localhost:8082/api/v1/game/${gameId}/submit`,
            { cardIds, maxScore, hintContent }
        );
        console.log("Submitted spymaster turn:", response.status);
        return response.status;
    } catch (error) {
        console.log(error);
        return error.status;
    }
}
