import axios from "axios";
import {getGameId} from "@/assets/composables/VerificationService.js";

export async function getHint() {
    try {
        const id = await getGameId();
        const response = await axios.get(`http://localhost:8082/api/v1/hints/${id}`);
        return response.data;
    } catch(error) {
        console.log(error.status);
    }
}
