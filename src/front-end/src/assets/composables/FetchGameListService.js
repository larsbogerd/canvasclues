import ApiClient from "@/assets/composables/ApiClient.js";

export async function fetchGameListService() {
    const response = await ApiClient.get(`/game/list`);
    console.log(response);
    return response.data;
}
