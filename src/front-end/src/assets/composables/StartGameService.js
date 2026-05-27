import ApiClient from "@/assets/composables/ApiClient.js";

 export async function startGameCall() {
   try {
     const response = await ApiClient.post('/game/start');
     return response.data;
   } catch(error) {
     console.log(error);
   }
 }

