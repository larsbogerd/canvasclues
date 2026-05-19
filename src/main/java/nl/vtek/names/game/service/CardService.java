package nl.vtek.names.game.service;

import nl.vtek.names.art.model.Artwork;
import nl.vtek.names.game.exception.CardNotFoundException;
import nl.vtek.names.game.exception.CardNotInSessionGameException;
import nl.vtek.names.game.model.Card;
import nl.vtek.names.game.model.CardType;
import nl.vtek.names.game.model.Game;
import nl.vtek.names.game.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final Random randomizer = new Random();

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> buildBoard(Game game, List<Artwork> artworks, int boardSize) {
        int[] gameOverLoop = {0, randomizer.nextInt(boardSize)};
        List<Card> cards = new ArrayList<>();
        for (Artwork artwork : artworks) {
            Card card = new Card(game, cardTypeRandomizer(gameOverLoop));
            card.setArtwork(artwork);
            cards.add(card);
            gameOverLoop[0]++;
        }
        sendCardsToDatabase(cards);
        return cards;
    }

    private void sendCardsToDatabase(List<Card> deck) {
        cardRepository.saveAll(deck);
    }

    private CardType cardTypeRandomizer(int[] assassinPosition) {
        CardType[] possibleTypeValues = CardType.values();
        int randomNumber = randomizer.nextInt(possibleTypeValues.length - 1);
        return assassinPosition[0] == assassinPosition[1] ? possibleTypeValues[3] : possibleTypeValues[randomNumber];
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

    public Card getCardInGame(UUID cardId, Long gameId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(cardId));
        if (!card.getGame().getId().equals(gameId)) {
            throw new CardNotInSessionGameException(cardId, gameId);
        }
        return card;
    }
}
