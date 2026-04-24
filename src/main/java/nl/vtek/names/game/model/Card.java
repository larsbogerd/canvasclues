package nl.vtek.names.game.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.UUID;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @Enumerated(EnumType.STRING)
    private CardType type;
    private String imageUrl;
    private String title;
    private String artistDisplay;
    private String dateDisplay;
    private String mediumDisplay;
    private String placeOfOrigin;
    private boolean isSpymasterPick;

    public Card() {
    }

    public Card(Game game, String imageUrl, String title, String artistDisplay, String dateDisplay,
                String mediumDisplay, String placeOfOrigin, CardType type) {
        this.game = game;
        this.imageUrl = imageUrl;
        this.title = title;
        this.artistDisplay = artistDisplay;
        this.dateDisplay = dateDisplay;
        this.mediumDisplay = mediumDisplay;
        this.placeOfOrigin = placeOfOrigin;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtistDisplay() {
        return artistDisplay;
    }

    public void setArtistDisplay(String artistDisplay) {
        this.artistDisplay = artistDisplay;
    }

    public String getDateDisplay() {
        return dateDisplay;
    }

    public void setDateDisplay(String dateDisplay) {
        this.dateDisplay = dateDisplay;
    }

    public boolean isSpymasterPick() {
        return isSpymasterPick;
    }

    public void setSpymasterPick(boolean spymasterPick) {
        isSpymasterPick = spymasterPick;
    }

    public String getMediumDisplay() {
        return mediumDisplay;
    }

    public void setMediumDisplay(String mediumDisplay) {
        this.mediumDisplay = mediumDisplay;
    }

    public String getPlaceOfOrigin() {
        return placeOfOrigin;
    }

    public void setPlaceOfOrigin(String placeOfOrigin) {
        this.placeOfOrigin = placeOfOrigin;
    }

}
