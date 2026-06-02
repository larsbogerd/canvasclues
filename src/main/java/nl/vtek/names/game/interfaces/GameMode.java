package nl.vtek.names.game.interfaces;

import nl.vtek.names.game.model.Card;
import nl.vtek.names.game.model.CardType;
import nl.vtek.names.game.model.Session;

import java.util.List;

public interface GameMode {

    String gameMode();

    int boardSize();

    List<CardType> getCardDistribution();

    void handleSpyScore(Long gameId, Integer spyScore);

    int calcOperativeScore(int currentScore, int comboStreak);

    double getMultiplier(int streak);

    void recordGuess(Session session, Card card);
}
