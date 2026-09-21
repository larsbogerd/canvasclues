package nl.vtek.names.game.mapper;

import nl.vtek.names.art.model.Artwork;
import nl.vtek.names.art.source.ArtSourceRegistry;
import nl.vtek.names.game.dto.CardResponse;
import nl.vtek.names.game.model.Card;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CardMapper {

    private final ArtSourceRegistry artSources;

    public CardMapper(ArtSourceRegistry artSources) {
        this.artSources = artSources;
    }

    public List<CardResponse> toCardResponse(List<Card> cards) {
        List<CardResponse> responses = new ArrayList<>();
        for (Card card : cards) {
            Artwork artwork = card.getArtwork();
            responses.add(new CardResponse(
                    card.getId(),
                    card.getGame().getId(),
                    card.getType(),
                    artSources.imageUrlTemplate(artwork),
                    artwork.getTitle(),
                    artwork.getArtistDisplay(),
                    artwork.getId(),
                    "%s — %s, %s".formatted(artwork.getTitle(), artwork.getArtistDisplay(), artwork.getDateDisplay())
            ));
        }

        return responses;
    }
}
