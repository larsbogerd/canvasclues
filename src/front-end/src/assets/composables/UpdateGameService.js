import axios from "axios";

export async function patchCards(selectedCards) {
    try {
        const response = await axios.patch('http://localhost:8082/api/v1/game/updatecards', {
            cardIds: selectedCards.value,
            spymasterPick: true
        })

        console.log("Patched cards:", response.data);
        return response.status;
    } catch (error) {
        console.log(error);
        return error.status;
    }
}