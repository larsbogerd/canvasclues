package nl.vtek.names.game.mapper;

import nl.vtek.names.game.dto.HintRequest;
import nl.vtek.names.game.dto.HintResponse;
import nl.vtek.names.game.model.Game;
import nl.vtek.names.game.model.Hint;

public class HintMapper {
    private HintMapper() {
        /* This utility class should not be instantiated */
    }

    public static Hint toEntity(HintRequest request, Game game) {
        Hint hint = new Hint();
        hint.setGame(game);
        hint.setContent(request.getContent());
        return hint;
    }

    public static HintResponse toHintResponse(Hint hint) {
        HintResponse response = new HintResponse();
        response.setId(hint.getId());
        response.setGameId(hint.getGame().getId());
        response.setContent(hint.getContent());
        return response;
    }
}
