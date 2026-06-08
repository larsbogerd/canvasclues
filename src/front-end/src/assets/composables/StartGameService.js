import ApiClient from "@/assets/composables/ApiClient.js";

let selectedGameMode;

export function setGameMode(gameMode) {
  selectedGameMode = gameMode;
}

export async function startGameCall() {
  try {
    const response = await ApiClient.post(`/game/start?gameMode=${selectedGameMode}`);

    return response.data;
  } catch (error) {
    console.log(error);
  }
}
