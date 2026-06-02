package nl.vtek.names.game.service;


import nl.vtek.names.game.exception.GameNotFoundException;
import nl.vtek.names.game.exception.MinimumSelectedException;
import nl.vtek.names.game.exception.NullScoreException;
import nl.vtek.names.game.interfaces.GameMode;
import nl.vtek.names.game.model.Card;
import nl.vtek.names.game.model.CardType;
import nl.vtek.names.game.model.Game;
import nl.vtek.names.game.model.Session;
import nl.vtek.names.game.repository.GameRepository;
import nl.vtek.names.game.repository.SessionRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component("hard")
public class HardGameModeService implements GameMode {

    GameRepository gameRepository;
    SessionRepository sessionRepository;

    public HardGameModeService(GameRepository gameRepository,
                               SessionRepository sessionRepository) {
        this.gameRepository = gameRepository;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public String gameMode() {
        return "hard";
    }

    @Override
    public int boardSize() {
        return 16;
    }

    @Override
    public List<CardType> getCardDistribution() {
        List<CardType> cardTypes = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            cardTypes.add(CardType.PLAYABLE);
        }
        for (int i = 0; i < 8; i++) {
            cardTypes.add(CardType.ASSASSIN);
        }
        Collections.shuffle(cardTypes);
        return cardTypes;
    }

    public void calcSpyScore(Long gameId, Integer spyScore) {
        if (spyScore == null) {
            throw new NullScoreException();
        }
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));
        game.setSpyScore(spyScore * 20);
        gameRepository.save(game);
    }

    public void validateSpyScore(int selectedCardCount) {
        if (selectedCardCount < 2) {
            throw new MinimumSelectedException();
        }
    }

    @Override
    public void handleSpyScore(Long gameId, Integer spyScore) {
        if (spyScore == null) {
            throw new NullScoreException();
        }
        validateSpyScore(spyScore);
        calcSpyScore(gameId, spyScore);
    }

    @Override
    public int calcOperativeScore(int currentScore, int comboStreak) {
        double multiplier = getMultiplier(comboStreak);
        return currentScore + (int) (20 * multiplier);
    }

    @Override
    public double getMultiplier(int streak) {
        if (streak >= 4) {
            return 3.0;
        }
        if (streak == 3) {
            return 2.0;
        }
        if (streak == 2) {
            return 1.5;
        }
        return 1.0;
    }

    @Override
    public void recordGuess(Session session, Card card) {
        if (card.isSpymasterPick()) {
            int newStreak = session.getComboStreak() + 1;
            session.setComboStreak(newStreak);
            int newScore = calcOperativeScore(session.getScore(), newStreak);
            session.setScore(newScore);
        } else if (card.getType() == CardType.ASSASSIN) {
            session.setComboStreak(0);
            session.setAssassinGuesses(session.getAssassinGuesses() + 1);
        } else {
            session.setComboStreak(0);
            session.setWrongGuesses(session.getWrongGuesses() + 1);
        }
        sessionRepository.save(session);
    }
}
