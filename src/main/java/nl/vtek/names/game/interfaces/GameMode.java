package nl.vtek.names.game.interfaces;

import nl.vtek.names.game.model.CardType;

import java.util.List;

public interface GameMode {

    String gameMode();

    int boardSize();

    List<CardType> getCardDistribution();
}
