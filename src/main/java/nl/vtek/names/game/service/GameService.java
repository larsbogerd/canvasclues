package nl.vtek.names.game.service;

import nl.vtek.names.art.dto.ArtWorkResponse;
import nl.vtek.names.art.service.ArtWorkService;
import nl.vtek.names.game.model.GameCard;
import nl.vtek.names.game.repository.GameCardRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class GameService {

    private final ArtWorkService artWorkService;
    private final GameCardRepository gameCardRepository;

    public GameService(ArtWorkService artWorkService, GameCardRepository gameCardRepository) {
        this.artWorkService = artWorkService;
        this.gameCardRepository = gameCardRepository;
    }

    public static final int BOARD_SIZE = 16;

    public List<GameCard> startGame() {
        int gameId = ThreadLocalRandom.current().nextInt(1, 999999);
        List<ArtWorkResponse> artworks = artWorkService.searchArtworks(BOARD_SIZE);

        List<GameCard> cards = new ArrayList<>();
        for (ArtWorkResponse artwork : artworks) {
            cards.add(new GameCard(gameId, artwork.imageUrl(), artwork.altText()));
        }

        gameCardRepository.saveAll(cards);

        return cards;
    }

    public List<GameCard> getGame(int gameId) {
        return gameCardRepository.findByGameId(gameId);
    }

    public List<GameCard> updateCards(List<UUID> cardIds, Boolean isSpymasterPick) {
        List<GameCard> cards = gameCardRepository.findAllById(cardIds);

        for (GameCard card : cards) {
            if (isSpymasterPick != null) {
                card.setSpymasterPick(isSpymasterPick);
            }
        }

        return gameCardRepository.saveAll(cards);
    }

    public Map<UUID, Boolean> compareSpyOperativeChoice(List<UUID> cardIds) {
        List<GameCard> cards = gameCardRepository.findAllById(cardIds);
        Map<UUID, Boolean> results = new HashMap<>();
        for (GameCard card : cards) {
            results.put(card.getId(), card.isSpymasterPick());
        }
        return results;
    }
}