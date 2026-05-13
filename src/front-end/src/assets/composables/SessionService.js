import axios from "axios";
import router from "../../router.js";

async function getGameId() {
    return router.currentRoute.value.params.gameId;
}

export async function startSession() {
    try {
        const id = await getGameId();
        const response = await axios.post(`http://localhost:8082/api/v1/session/${id}/start`);
        return response.data;
    } catch (error) {
        console.log(error);
    }
}

export async function finishSession(sessionId, score) {
    try {
        await axios.post(
            `http://localhost:8082/api/v1/session/${sessionId}/finish`,
            { score }
        );
    } catch (error) {
        console.log(error);
    }
}
