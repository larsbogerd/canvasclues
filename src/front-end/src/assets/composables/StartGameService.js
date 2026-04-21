 import axios from "axios";

 export async function startGameCall() {
   try {
     const response = await axios.post('http://localhost:8082/api/v1/game/start');
     return response.data;
   } catch(error) {
     console.log(error);
   }
 }

