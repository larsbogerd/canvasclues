package nl.vtek.names.game.service;

import nl.vtek.names.game.dto.CardResponse;
import nl.vtek.names.game.dto.GameResponse;
import nl.vtek.names.game.mapper.CardMapper;
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

    public Game createGame() {
        return gameRepository.save(GameMapper.toEntity());
    }

    public List<CardResponse> getGame(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));
        game.setPlayCount(game.getPlayCount() + 1);
        gameRepository.save(game);

        return CardMapper.toCardResponse(game.getCards());
    }

    public void markReady(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));
        game.setState(GameState.READY);
        gameRepository.save(game);
    }

    public void updateMaxScore(Long gameId, Integer maxScore) {
        if (maxScore == null) {
            return;
        }
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));
        game.setMaxScore(maxScore);
        gameRepository.save(game);
    }

    public List<GameResponse> getGameList() {
        List<Game> games = gameRepository.findByState(GameState.READY);
        return games.stream()
                .map(GameMapper::toGameResponse)
                .toList();
    }

}
