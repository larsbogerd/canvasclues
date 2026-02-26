package nl.vtek.names.game.dto;

import java.util.List;
import java.util.UUID;

public class CardUpdateRequest {

    private List<UUID> cardIds;
    private Boolean spymasterPick;
    private Boolean isRevealed;

    public List<UUID> getCardIds() {
        return cardIds;
    }

    public void setCardIds(List<UUID> cardIds) {
        this.cardIds = cardIds;
    }

    public Boolean getSpymasterPick() {
        return spymasterPick;
    }

    public void setSpymasterPick(Boolean spymasterPick) {
        this.spymasterPick = spymasterPick;
    }

    public Boolean getRevealed() {
        return isRevealed;
    }

    public void setRevealed(Boolean revealed) {
        isRevealed = revealed;
    }
}
