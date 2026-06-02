package nl.vtek.names.game.orchestrator;

import nl.vtek.names.art.model.Artwork;
import nl.vtek.names.art.service.ArtworkService;
import nl.vtek.names.game.dto.CardResponse;
import nl.vtek.names.game.interfaces.GameMode;
import nl.vtek.names.game.mapper.CardMapper;
import nl.vtek.names.game.model.Card;
import nl.vtek.names.game.model.Game;
import nl.vtek.names.game.service.CardService;
import nl.vtek.names.game.service.GameModeRegistry;
import nl.vtek.names.game.service.GameService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StartGameOrchestrator {

    private final GameService gameService;
    private final ArtworkService artworkService;
    private final CardService cardService;
    private final GameModeRegistry gameModeRegistry;

    public StartGameOrchestrator(GameService gameService,
                                 ArtworkService artworkService,
                                 CardService cardService,
                                 GameModeRegistry gameModeRegistry) {
        this.gameService = gameService;
        this.artworkService = artworkService;
        this.cardService = cardService;
        this.gameModeRegistry = gameModeRegistry;
    }

    public List<CardResponse> startGame(String modeName) {
        GameMode gameMode = gameModeRegistry.getMode(modeName);
        Game game = gameService.createGame(modeName);
        List<Artwork> artworks = artworkService.fetchAndSaveArtworks(gameMode.boardSize());
        List<Card> cards = cardService.buildBoard(game, artworks, gameMode);
        return CardMapper.toCardResponse(cards);
    }
}
