package nl.vtek.names.game.service;

import nl.vtek.names.art.dto.ArtWorkResponse;
import nl.vtek.names.art.service.ArtWorkService;
import nl.vtek.names.game.model.GameCard;
import nl.vtek.names.game.repository.GameCardRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class GameService {

    private final ArtWorkService artWorkService;
    private final GameCardRepository gameCardRepository;

    public GameService(ArtWorkService artWorkService, GameCardRepository gameCardRepository) {
        this.artWorkService = artWorkService;
        this.gameCardRepository = gameCardRepository;
    }

    public List<GameCard> startGame() {
        int gameId = ThreadLocalRandom.current().nextInt(1, 999999);

        List<ArtWorkResponse> artworks = artWorkService.getRandomArtworks();

        List<GameCard> cards = new ArrayList<>();
        for (ArtWorkResponse artwork : artworks) {
            cards.add(new GameCard(gameId, artwork.id(), artwork.imageUrl(), artwork.altText()));
        }

        gameCardRepository.saveAll(cards);

        return cards;
    }

    public List<GameCard> getGame(int gameId) {
        return gameCardRepository.findByGameIdOrderByPositionAsc(gameId);
    }
}