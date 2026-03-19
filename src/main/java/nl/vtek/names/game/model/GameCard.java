package nl.vtek.names.game.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "game_cards")
public class GameCard {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    private int gameId;
//    @Enumerated(EnumType.STRING)
//    private GameCardType cardType;
    private String imageUrl;
    @Column(name="alt_text", length = 1000)
    private String altText;
    private boolean isSpymasterPick;

    public GameCard() {
    }

    public GameCard(int gameId, String imageUrl, String altText) {
        this.gameId = gameId;
        this.imageUrl = imageUrl;
        this.altText = altText;
        this.isSpymasterPick = false;
    }

    public UUID getId() {
        return uuid;
    }

    public void setId(UUID id) {
        this.uuid = id;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

//    public GameCardType getCardType() {
//        return cardType;
//    }
//
//    public void setCardType(GameCardType cardType) {
//        this.cardType = cardType;
//    }

    public boolean isSpymasterPick() {
        return isSpymasterPick;
    }

    public void setSpymasterPick(boolean spymasterPick) {
        this.isSpymasterPick = spymasterPick;
    }

}