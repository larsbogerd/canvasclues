import ApiClient from "@/assets/composables/ApiClient.js";

export async function startGameCall(source, selectedGameMode) {
  try {
    const query = new URLSearchParams();
    if (source) query.set('source', source);
    query.set('gameMode', selectedGameMode);
    const response = await ApiClient.post(`/game/start?${query}`);

    return response.data;
  } catch (error) {
    console.log(error);
  }
}
