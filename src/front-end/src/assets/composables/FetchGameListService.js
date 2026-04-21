import axios from "axios";

export async function fetchGameListService() {
    const response = await axios.get(`http://localhost:8082/api/v1/game/list`);
    console.log(response);
    return response.data;
}
