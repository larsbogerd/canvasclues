package nl.vtek.names.game.service;

import nl.vtek.names.art.model.Artwork;
import nl.vtek.names.game.exception.CardNotFoundException;
import nl.vtek.names.game.exception.CardNotInSessionGameException;
import nl.vtek.names.game.interfaces.GameMode;
import nl.vtek.names.game.model.Card;
import nl.vtek.names.game.model.CardType;
import nl.vtek.names.game.model.Game;
import nl.vtek.names.game.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.UUID;

@Service
public class CardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> buildBoard(Game game, List<Artwork> artworks, GameMode gamemode) {
        List<CardType> cardTypes = gamemode.getCardDistribution();
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < artworks.size(); i++) {
            Card card = new Card(game, cardTypes.get(i));
            card.setArtwork(artworks.get(i));
            cards.add(card);
        }
        sendCardsToDatabase(cards);
        return cards;
    }

    private void sendCardsToDatabase(List<Card> deck) {
        cardRepository.saveAll(deck);
    }


    public void updateCards(List<UUID> cardIds, Boolean isSpymasterPick) {
        List<Card> cards = cardRepository.findAllById(cardIds);
        for (Card card : cards) {
            if (isSpymasterPick != null) {
                if (isSpymasterPick && card.getType() == CardType.ASSASSIN) {
                    throw new InputMismatchException("Wrong Card Type");
                }

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
