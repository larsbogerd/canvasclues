package nl.vtek.names.game.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "game_cards")
public class GameCard {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private int gameId;
    private int position;
//    @Enumerated(EnumType.STRING)
//    private GameCardType cardType;
    private String imageUrl;
    @Column(name="alt_text", length = 1000)
    private String altText;
    private boolean isSpymasterPick;
    private boolean isRevealed;

    public GameCard() {
    }

    public GameCard(int gameId, int position, String imageUrl, String altText) {
        this.gameId = gameId;
        this.position = position;
        this.imageUrl = imageUrl;
        this.altText = altText;
        this.isSpymasterPick = false;
        this.isRevealed = false;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        this.isRevealed = revealed;
    }
}