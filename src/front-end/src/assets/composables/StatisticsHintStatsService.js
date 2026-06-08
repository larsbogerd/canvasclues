import ApiClient from "@/assets/composables/ApiClient.js";

export async function getStatisticsHints() {
    const response= await ApiClient.get("/hints/stats");
    return response.data;
}