package nl.vtek.names.game.service;

import nl.vtek.names.art.dto.ArtWorkResponse;
import nl.vtek.names.art.service.ArtWorkService;
import nl.vtek.names.game.dto.GameResponse;
import nl.vtek.names.game.model.Card;
import nl.vtek.names.game.model.Game;
import nl.vtek.names.game.dto.CardResponse;
import nl.vtek.names.game.model.GameState;
import nl.vtek.names.game.repository.CardRepository;
import nl.vtek.names.game.repository.GameRepository;
import nl.vtek.names.game.mapper.CardMapper;
import nl.vtek.names.game.mapper.GameMapper;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class GameService {

    private final ArtWorkService artWorkService;
    private final CardRepository cardRepository;
    private final GameRepository gameRepository;

    public GameService(ArtWorkService artWorkService,
                       CardRepository cardRepository,
                       GameRepository gameRepository) {
        this.artWorkService = artWorkService;
        this.cardRepository = cardRepository;
        this.gameRepository = gameRepository;
    }

    public static final int BOARD_SIZE = 16;

    public List<CardResponse> startGame() {
        var game = GameMapper.toEntity();
        game = gameRepository.save(game);

        List<ArtWorkResponse> artworks = artWorkService.searchArtworks(BOARD_SIZE);

        List<Card> cards = new ArrayList<>();
        for (ArtWorkResponse artwork : artworks) {
            cards.add(new Card(
                    game,
                    artwork.imageUrl(),
                    artwork.title(),
                    artwork.artistDisplay(),
                    artwork.dateDisplay(),
                    artwork.mediumDisplay(),
                    artwork.placeOfOrigin()
            ));
        }

        cardRepository.saveAll(cards);
        return CardMapper.toCardResponse(cards);
    }

    public List<CardResponse> getGame(int gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found: " + gameId));
        game.setPlayCount(game.getPlayCount() + 1);
        gameRepository.save(game);

        return CardMapper.toCardResponse(game.getCards());
    }

    public void updateCards(List<UUID> cardIds, Boolean isSpymasterPick, Integer maxScore, int gameId) {
        List<Card> cards = cardRepository.findAllById(cardIds);

        for (Card card : cards) {
            if (isSpymasterPick != null) {
                card.setSpymasterPick(isSpymasterPick);
            }
        }
        cardRepository.saveAll(cards);

        if (maxScore != null) {
            Game game = gameRepository.findById(gameId).orElseThrow();
            game.setMaxScore(maxScore);
            gameRepository.save(game);
        }
    }


    public Map<UUID, Boolean> checkCards(List<UUID> cardIds) {
        List<Card> cards = cardRepository.findAllById(cardIds);
        Map<UUID, Boolean> results = new HashMap<>();
        for (Card card : cards) {
            results.put(card.getId(), card.isSpymasterPick());
        }
        return results;
    }

    public List<GameResponse> getGameList() {
        List<Game> games = gameRepository.findByState(GameState.READY);
        return games.stream()
                .map(GameMapper::toGameResponse)
                .toList();
    }
}