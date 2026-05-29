import ApiClient from "@/assets/composables/ApiClient.js";

export async function getArtworkStatsList() {
    const response = await ApiClient.get(`/artwork/statslist`);
    console.log(response);
    return response.data;
}

export async function getArtworkStats(id){
    const response = await ApiClient.get(`/artwork/${id}/stats`);
    console.log(response);
    return response.data;
}