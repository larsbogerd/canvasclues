package nl.vtek.names.game.service;

import nl.vtek.names.art.model.Artwork;
import nl.vtek.names.art.repository.ArtworkRepository;
import nl.vtek.names.art.service.ArtworkService;
import nl.vtek.names.game.dto.GameResponse;
import nl.vtek.names.game.model.Card;
import nl.vtek.names.game.model.Game;
import nl.vtek.names.game.dto.CardResponse;
import nl.vtek.names.game.model.GameState;
import nl.vtek.names.game.repository.GameRepository;
import nl.vtek.names.game.mapper.CardMapper;
import nl.vtek.names.game.mapper.GameMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    private final ArtworkService artworkService;
    private final ArtworkRepository artworkRepository;
    private final CardService cardService;
    private final GameRepository gameRepository;

    public GameService(ArtworkService artworkService,
                       ArtworkRepository artworkRepository,
                       CardService cardService,
                       GameRepository gameRepository) {
        this.artworkService = artworkService;
        this.artworkRepository = artworkRepository;
        this.cardService = cardService;
        this.gameRepository = gameRepository;
    }

    public static final int BOARD_SIZE = 16;

    private Game createGame(){
        var game = GameMapper.toEntity();
        gameRepository.save(game);
        return game;
    }

    public List<CardResponse> startGame() {
        Game game = createGame();
        List<Artwork> artworks = artworkService.fetchAndSaveArtworks(BOARD_SIZE);
        List<Card> deck = cardService.fetchDeck(game, artworks, BOARD_SIZE);
        return CardMapper.toCardResponse(deck);
    }

    public List<CardResponse> getGame(int gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found: " + gameId));
        game.setPlayCount(game.getPlayCount() + 1);
        gameRepository.save(game);

        return CardMapper.toCardResponse(game.getCards());
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
}
