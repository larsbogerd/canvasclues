package nl.vtek.names.game.mapper;

import nl.vtek.names.game.dto.CardResponse;
import nl.vtek.names.game.model.Card;

import java.util.ArrayList;
import java.util.List;

public class CardMapper {
    private CardMapper() {
        /* This utility class should not be instantiated */
    }

    public static List<CardResponse> toCardResponse(List<Card> cards) {
        List<CardResponse> responses = new ArrayList<>();
        for (Card card : cards) {
            CardResponse response = new CardResponse();
            response.setId(card.getId());
            response.setGameId(card.getGame().getId());
            response.setImageUrl(card.getImageUrl());
            response.setAltText(card.getAltText());
            response.setSpymasterPick(card.isSpymasterPick());
            response.setType(card.getType());

            responses.add(response);
        }

        return responses;
    }
}
