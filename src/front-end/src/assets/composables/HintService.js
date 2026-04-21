import axios from "axios";
import {getGameId} from "@/assets/composables/VerificationService.js";

export async function updateHint(input, gameId) {
    try {
        const response = await axios.post('http://localhost:8082/api/v1/hints', {
            content: input,
            gameId: gameId
        })
        console.log("Posting now")
        return response.status;
    } catch (error) {
        return error.status;
    }
}

export async function getHint() {
    try {
        const id = await getGameId();
        const response = await axios.get(`http://localhost:8082/api/v1/hints/${id}`);
        return response.data;
    } catch(error) {
        console.log(error.status);
    }
}