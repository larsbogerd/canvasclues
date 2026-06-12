package nl.vtek.names.game.mapper;

import nl.vtek.names.art.model.Artwork;
import nl.vtek.names.art.util.IiifUrlBuilder;
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
            responses.add(new CardResponse(
                    card.getId(),
                    card.getGame().getId(),
                    card.getType(),
                    IiifUrlBuilder.forArtwork(artwork.getId()),
                    artwork.getTitle(),
                    artwork.getArtistDisplay(),
                    artwork.getId(),
                    "%s — %s, %s".formatted(artwork.getTitle(), artwork.getArtistDisplay(), artwork.getDateDisplay())
            ));
        }

        return responses;
    }
}
