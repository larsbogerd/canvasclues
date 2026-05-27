import router from "../../router.js";
import ApiClient from "@/assets/composables/ApiClient.js";

async function getGameId() {
    return router.currentRoute.value.params.gameId;
}

export async function startSession() {
    try {
        const id = await getGameId();
        const response = await ApiClient.post(`/session/${id}/start`);
        return response.data;
    } catch (error) {
        console.log(error.response?.data ?? error);
    }
}

export async function finishSession(sessionId) {
    try {
        await ApiClient.post(
            `/session/${sessionId}/finish`,
        );
    } catch (error) {
        console.log(error.response?.data ?? error);
    }
}
