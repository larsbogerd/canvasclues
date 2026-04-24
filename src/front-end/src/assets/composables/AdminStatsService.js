import axios from "axios";

export async function getArtworkStatsList() {
    const response = await axios.get(`http://localhost:8082/api/v1/artwork/statslist`);
    console.log(response);
    return response.data;
}

export async function getArtworkStats(id){
    const response = await axios.get(`http://localhost:8082/api/v1/artwork/${id}/stats`);
    console.log(response);
    return response.data;
}