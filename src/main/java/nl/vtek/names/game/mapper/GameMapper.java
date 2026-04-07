package nl.vtek.names.game.mapper;

import nl.vtek.names.game.dto.GameResponse;
import nl.vtek.names.game.model.Game;
import nl.vtek.names.game.model.GameState;

public class GameMapper {
    private GameMapper() {
        /* This utility class should not be instantiated */
    }

    public static Game toEntity() {
        Game game = new Game();
        game.setState(GameState.CREATING);
        return game;
    }

    public static GameResponse toGameResponse(Game game) {
        String hint = game.getHints().isEmpty()
                ? null
                : game.getHints().getFirst().getContent();
        GameResponse response = new GameResponse();
        response.setGameId(game.getId());
        response.setHint(hint);
        response.setMaxScore(game.getMaxScore());
        response.setCreatedAt(game.getCreatedAt());
        response.setPlayCount(game.getPlayCount());
        return response;
    }


}
