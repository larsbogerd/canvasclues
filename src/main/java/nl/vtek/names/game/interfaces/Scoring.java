package nl.vtek.names.game.interfaces;

import nl.vtek.names.game.model.Card;
import nl.vtek.names.game.model.Session;

public interface Scoring {

    void handleSpyScore(Long gameId, Integer spyScore);

    int calcOperativeScore(int currentScore, int comboStreak);

    double getMultiplier(int streak);

    void recordGuess(Session session, Card card);
}
