package nl.vtek.names.game.service;

import nl.vtek.names.game.exception.GameNotFoundException;
import nl.vtek.names.game.exception.MinimumSelectedException;
import nl.vtek.names.game.exception.NullScoreException;
import nl.vtek.names.game.model.Card;
import nl.vtek.names.game.model.CardType;
import nl.vtek.names.game.model.Game;
import nl.vtek.names.game.model.Session;
import nl.vtek.names.game.repository.CardRepository;
import nl.vtek.names.game.repository.GameRepository;
import nl.vtek.names.game.repository.SessionRepository;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {


    private final GameRepository gameRepository;
    private final SessionRepository sessionRepository;


    public ScoreService(GameRepository gameRepository,
                        SessionRepository sessionRepository) {
        this.gameRepository = gameRepository;
        this.sessionRepository = sessionRepository;
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

    public void handleSpyScore(Long gameId, Integer spyScore) {
        if (spyScore == null) {
            throw new NullScoreException();
        }
        validateSpyScore(spyScore);
        calcSpyScore(gameId, spyScore);
    }

    public int calcOperativeScore(int currentScore, int comboStreak) {
        double multiplier = getMultiplier(comboStreak);
        return currentScore + (int) (20 * multiplier);
    }

    public double getMultiplier(int streak) {
        if (streak >= 4) return 3.0;
        if (streak == 3) return 2.0;
        if (streak == 2) return 1.5;
        return 1.0;
    }

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