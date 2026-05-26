package nl.vtek.names.game.service;

import nl.vtek.names.game.dto.SessionResponse;
import nl.vtek.names.game.exception.GameNotFoundException;
import nl.vtek.names.game.exception.SessionAlreadyFinishedException;
import nl.vtek.names.game.exception.SessionNotFoundException;
import nl.vtek.names.game.mapper.SessionMapper;
import nl.vtek.names.game.model.Game;
import nl.vtek.names.game.model.Hint;
import nl.vtek.names.game.model.Session;
import nl.vtek.names.game.model.SessionState;
import nl.vtek.names.game.repository.GameRepository;
import nl.vtek.names.game.repository.HintRepository;
import nl.vtek.names.game.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SessionService {

    private final GameRepository gameRepository;
    private final SessionRepository sessionRepository;
    private final HintRepository hintRepository;

    public SessionService(GameRepository gameRepository,
                          SessionRepository sessionRepository,
                          HintRepository hintRepository) {
        this.gameRepository = gameRepository;
        this.sessionRepository = sessionRepository;
        this.hintRepository = hintRepository;
    }

    public SessionResponse start(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));

        game.setPlayCount(game.getPlayCount() + 1);
        gameRepository.save(game);

        Hint hint = hintRepository.findByGame_Id(gameId).orElse(null);
        Session session = sessionRepository.save(new Session(game, hint));

        return SessionMapper.toSessionResponse(session, game.getCards(), hint);
    }

    public Session getActiveSession(UUID sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new SessionNotFoundException(sessionId));
        if (session.getState() == SessionState.FINISHED) {
            throw new SessionAlreadyFinishedException(sessionId);
        }
        return session;
    }

    public void finish(UUID sessionId) {
        Session session = getActiveSession(sessionId);

        if (session.getWrongGuesses() == 0 && session.getAssassinGuesses() == 0) {
            session.setScore(session.getScore() + 20);
        }

        session.setFinishedAt(LocalDateTime.now());
        session.setState(SessionState.FINISHED);
        sessionRepository.save(session);
    }
}
