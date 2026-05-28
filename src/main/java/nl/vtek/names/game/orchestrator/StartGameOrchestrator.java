package nl.vtek.names.game.orchestrator;

import nl.vtek.names.art.model.Artwork;
import nl.vtek.names.art.service.ArtworkService;
import nl.vtek.names.game.dto.CardResponse;
import nl.vtek.names.game.mapper.CardMapper;
import nl.vtek.names.game.model.Card;
import nl.vtek.names.game.model.Game;
import nl.vtek.names.game.service.CardService;
import nl.vtek.names.game.service.GameService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StartGameOrchestrator {

    public static final int BOARD_SIZE = 16;

    private final GameService gameService;
    private final ArtworkService artworkService;
    private final CardService cardService;

    public StartGameOrchestrator(GameService gameService, ArtworkService artworkService, CardService cardService) {
        this.gameService = gameService;
        this.artworkService = artworkService;
        this.cardService = cardService;
    }

    public List<CardResponse> startGame() {
        Game game = gameService.createGame();
        List<Artwork> artworks = artworkService.fetchAndSaveArtworks(BOARD_SIZE);
        List<Card> cards = cardService.buildBoard(game, artworks, BOARD_SIZE);
        return CardMapper.toCardResponse(cards);
    }
}
