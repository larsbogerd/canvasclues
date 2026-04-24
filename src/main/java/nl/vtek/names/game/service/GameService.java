package nl.vtek.names.game.service;

import nl.vtek.names.art.model.Artwork;
import nl.vtek.names.art.repository.ArtworkRepository;
import nl.vtek.names.art.service.ArtworkService;
import nl.vtek.names.game.dto.GameResponse;
import nl.vtek.names.game.model.Card;
import nl.vtek.names.game.model.CardType;
import nl.vtek.names.game.model.Game;
import nl.vtek.names.game.dto.CardResponse;
import nl.vtek.names.game.model.GameState;
import nl.vtek.names.game.repository.CardRepository;
import nl.vtek.names.game.repository.GameRepository;
import nl.vtek.names.game.mapper.CardMapper;
import nl.vtek.names.game.mapper.GameMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
public class GameService {

    private final ArtworkService artworkService;
    private final ArtworkRepository artworkRepository;
    private final CardRepository cardRepository;
    private final GameRepository gameRepository;

    public GameService(ArtworkService artworkService,
                       ArtworkRepository artworkRepository,
                       CardRepository cardRepository,
                       GameRepository gameRepository) {
        this.artworkService = artworkService;
        this.artworkRepository = artworkRepository;
        this.cardRepository = cardRepository;
        this.gameRepository = gameRepository;
    }

    public static final int BOARD_SIZE = 16;

    public List<CardResponse> startGame() {
        var game = GameMapper.toEntity();
        game = gameRepository.save(game);

        List<Artwork> artworks = artworkService.fetchAndSaveArtworks(BOARD_SIZE);

        Random rand = new Random();
        int[] gameOverLoop = {0, rand.nextInt(16)};
        List<Card> cards = new ArrayList<>();
        for (Artwork artwork : artworks) {
            Card card = new Card(game, typeRandomizer(gameOverLoop));
            card.setArtwork(artwork);
            cards.add(card);
            gameOverLoop[0]++;
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

    public void updateCards(List<UUID> cardIds, Boolean isSpymasterPick) {
        List<Card> cards = cardRepository.findAllById(cardIds);
        for (Card card : cards) {
            if (isSpymasterPick != null) {
                card.setSpymasterPick(isSpymasterPick);
            }
        }
        cardRepository.saveAll(cards);
    }

    public void updateMaxScore(int gameId, Integer maxScore) {
        if (maxScore == null) {
            return;
        }
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found: " + gameId));
        game.setMaxScore(maxScore);
        gameRepository.save(game);
    }

    public Map<UUID, Boolean> checkCards(List<UUID> cardIds) {
        List<Card> cards = cardRepository.findAllById(cardIds);
        Map<UUID, Boolean> results = new HashMap<>();
        for (Card card : cards) {
            results.put(card.getId(), card.isSpymasterPick());
        }
        return results;
    }

    public void submitMetadata(int gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found: " + gameId));

        LocalDateTime now = LocalDateTime.now();
        List<Artwork> toUpdate = new ArrayList<>();

        for (Card card : game.getCards()) {
            Artwork artwork = card.getArtwork();
            artwork.setTimesLoaded(artwork.getTimesLoaded() + 1);

            if (artwork.getFirstUsedAt() == null) {
                artwork.setFirstUsedAt(now);
            }
            artwork.setLastUsedAt(now);

            if (card.isSpymasterPick()) {
                artwork.setTimesSpymasterPick(artwork.getTimesSpymasterPick() + 1);
            }
            toUpdate.add(artwork);
        }
        artworkRepository.saveAll(toUpdate);
    }

    public List<GameResponse> getGameList() {
        List<Game> games = gameRepository.findByState(GameState.READY);
        return games.stream()
                .map(GameMapper::toGameResponse)
                .toList();
    }

    private CardType typeRandomizer(int[] assassinPosition) {
        CardType[] possibleTypeValues = CardType.values();
        Random randomizer = new Random();
        int randomNumber = randomizer.nextInt(possibleTypeValues.length - 1);
        return assassinPosition[0] == assassinPosition[1] ? possibleTypeValues[3] : possibleTypeValues[randomNumber];
    }

}
