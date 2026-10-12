import ApiClient from "@/assets/composables/ApiClient.js";

// Throws on failure: the caller decides what the player sees.
export async function startGameCall(source, selectedGameMode) {
  const query = new URLSearchParams();
  if (source) query.set('source', source);
  query.set('gameMode', selectedGameMode);
  const response = await ApiClient.post(`/game/start?${query}`);
  return response.data;
}
