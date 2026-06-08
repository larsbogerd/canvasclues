package nl.vtek.names.game.strategy;


import nl.vtek.names.game.interfaces.GameMode;
import nl.vtek.names.game.model.CardType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component("gemiddeld")
public class MediumModeStrategy implements GameMode {

    @Override
    public String gameMode() {
        return "gemiddeld";
    }

    @Override
    public int boardSize() {
        return 16;
    }

    @Override
    public List<CardType> getCardDistribution() {
        List<CardType> cardTypes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            cardTypes.add(CardType.PLAYABLE);
        }
        for (int i = 0; i < 6; i++) {
            cardTypes.add(CardType.ASSASSIN);
        }
        Collections.shuffle(cardTypes);
        return cardTypes;
    }
}
