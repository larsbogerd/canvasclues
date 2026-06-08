package nl.vtek.names.game.service;

import nl.vtek.names.game.dto.GameResponse;
import nl.vtek.names.game.mapper.GameMapper;
import nl.vtek.names.game.model.Game;
import nl.vtek.names.game.model.GameState;
import nl.vtek.names.game.exception.GameNotFoundException;
import nl.vtek.names.game.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game createGame(String modeName) {
        Game game = new Game();
        game.setGameMode(modeName);
        return gameRepository.save(game);
    }

    public void markReady(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));
        game.setState(GameState.READY);
        gameRepository.save(game);
    }

    public List<GameResponse> getGameList() {
        List<Game> games = gameRepository.findByState(GameState.READY);
        return games.stream()
                .map(GameMapper::toGameResponse)
                .toList();
    }
}
