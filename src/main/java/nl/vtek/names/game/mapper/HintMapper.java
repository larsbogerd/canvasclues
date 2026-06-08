package nl.vtek.names.game.mapper;

import nl.vtek.names.game.dto.HintResponse;
import nl.vtek.names.game.dto.HintStatsListResponse;
import nl.vtek.names.game.model.Game;
import nl.vtek.names.game.model.Hint;

public class HintMapper {
    private HintMapper() {
        /* This utility class should not be instantiated */
    }

    public static Hint toEntity(String content, Game game) {
        Hint hint = new Hint();
        hint.setGame(game);
        hint.setContent(content.toLowerCase());
        return hint;
    }

    public static HintResponse toHintResponse(Hint hint) {
        return new HintResponse(
                hint.getId(),
                hint.getGame().getId(),
                hint.getContent()
        );
    }

    public static HintStatsListResponse toHintStatsListResponse(String hint, Long frequency) {
        return new HintStatsListResponse(
                hint,
                frequency
        );
    }
}
