import ApiClient from "@/assets/composables/ApiClient.js";

export async function startGameCall(selectedGameMode) {
  try {
    const response = await ApiClient.post(`/game/start?gameMode=${selectedGameMode}`);

    return response.data;
  } catch (error) {
    console.log(error);
  }
}
