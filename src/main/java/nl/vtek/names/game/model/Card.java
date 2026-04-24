package nl.vtek.names.game.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import nl.vtek.names.art.model.Artwork;

import java.util.UUID;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "artwork_id")
    private Artwork artwork;

    @Enumerated(EnumType.STRING)
    private CardType type;
    private boolean isSpymasterPick;

    public Card() {
    }

    public Card(Game game, CardType type) {
        this.game = game;
        this.isSpymasterPick = false;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public Artwork getArtwork() {
        return artwork;
    }

    @Transient
    public String getImageUrl() {
        return "https://www.artic.edu/iiif/2/%s/full/!500,500/0/default.jpg".formatted(artwork.getId());
    }

    public void setArtwork(Artwork artwork) {
        this.artwork = artwork;
    }

    public boolean isSpymasterPick() {
        return isSpymasterPick;
    }

    public void setSpymasterPick(boolean spymasterPick) {
        isSpymasterPick = spymasterPick;
    }
}
