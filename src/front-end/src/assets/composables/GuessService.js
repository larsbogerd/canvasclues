import axios from "axios";

export async function submitGuess(sessionId, cardId) {
    try {
        const response = await axios.post(
            `http://localhost:8082/api/v1/session/${sessionId}/guess/${cardId}`
        );
        return response.data;
    } catch (error) {
        console.log(error.response?.data ?? error);
    }
}
