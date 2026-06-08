package nl.vtek.names.game.mapper;

import nl.vtek.names.game.dto.GameResponse;
import nl.vtek.names.game.model.Game;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {
    private GameMapper() {
        /* This utility class should not be instantiated */
    }

    public static GameResponse toGameResponse(Game game) {
        String hint = game.getHints().isEmpty()
                ? null
                : game.getHints().getFirst().getContent();
        return new GameResponse(
                game.getId(),
                hint,
                game.getSpyScore(),
                game.getCreatedAt(),
                game.getPlayCount(),
                calculateQualityRatio(game),
                game.getGameMode()
        );
    }

    private static Double calculateQualityRatio(Game game) {
        int positive = game.getLikes();
        int negative = game.getDislikes();
        int total = positive + negative;
        return total == 0 ? null : Math.round((positive / (double) total) * 1000) / 10.0;
    }
}
