import ApiClient from "@/assets/composables/ApiClient.js";

export async function submitGuess(sessionId, cardId) {
    try {
        const response = await ApiClient.post(
            `/session/${sessionId}/guess/${cardId}`
        );
        return response.data;
    } catch (error) {
        console.log(error.response?.data ?? error);
    }
}
