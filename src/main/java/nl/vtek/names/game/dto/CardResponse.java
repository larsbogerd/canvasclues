package nl.vtek.names.game.dto;

import nl.vtek.names.game.model.CardType;

import java.util.UUID;

public class CardResponse {
    private UUID id;
    private int gameId;
    private CardType type;
    private String imageUrl;
    private String altText;
    private boolean isSpymasterPick;

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

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public boolean isSpymasterPick() {
        return isSpymasterPick;
    }

    public void setSpymasterPick(boolean spymasterPick) {
        isSpymasterPick = spymasterPick;
    }
}
