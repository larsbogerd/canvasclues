package nl.vtek.names.game.mapper;

import nl.vtek.names.art.model.Artwork;
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
            Artwork artwork = card.getArtwork();
            CardResponse response = new CardResponse();
            response.setId(card.getId());
            response.setGameId(card.getGame().getId());
            response.setImageUrl(card.getImageUrl());
            response.setTitle(artwork.getTitle());
            response.setArtistDisplay(artwork.getArtistDisplay());
            response.setDateDisplay(artwork.getDateDisplay());
            response.setMediumDisplay(artwork.getMediumDisplay());
            response.setPlaceOfOrigin(artwork.getPlaceOfOrigin());

            response.setAltText("%s — %s, %s".formatted(
                    artwork.getTitle(), artwork.getArtistDisplay(), artwork.getDateDisplay()));
            response.setSpymasterPick(card.isSpymasterPick());
            response.setType(card.getType());

            responses.add(response);
        }

        return responses;
    }
}
