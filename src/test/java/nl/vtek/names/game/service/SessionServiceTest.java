package nl.vtek.names.game.service;

import nl.vtek.names.game.exception.GameNotFoundException;
import nl.vtek.names.game.exception.GameNotPlayableException;
import nl.vtek.names.game.exception.SessionAlreadyFinishedException;
import nl.vtek.names.game.exception.SessionNotFoundException;
import nl.vtek.names.game.mapper.SessionMapper;
import nl.vtek.names.game.model.Game;
import nl.vtek.names.game.model.GameState;
import nl.vtek.names.game.model.Session;
import nl.vtek.names.game.model.SessionState;
import nl.vtek.names.game.repository.GameRepository;
import nl.vtek.names.game.repository.HintRepository;
import nl.vtek.names.game.repository.SessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SessionServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private HintRepository hintRepository;

    @Mock
    private SessionMapper sessionMapper;

    @InjectMocks
    private SessionService sessionService;

    @Test
    void start_readyGame_countsAPlayAndOpensASession() {
        Game game = game(1L, GameState.READY);
        given(gameRepository.findById(1L)).willReturn(Optional.of(game));
        given(hintRepository.findByGame_Id(1L)).willReturn(Optional.empty());
        given(sessionRepository.save(any(Session.class))).willAnswer(call -> call.getArgument(0));

        sessionService.start(1L);

        assertThat(game.getPlayCount()).isEqualTo(1);
        verify(gameRepository).save(game);
        verify(sessionRepository).save(any(Session.class));
    }

    @Test
    void start_gameStillBeingCreated_isNotPlayable() {
        given(gameRepository.findById(1L)).willReturn(Optional.of(game(1L, GameState.CREATING)));

        assertThatThrownBy(() -> sessionService.start(1L))
                .isInstanceOf(GameNotPlayableException.class);

        verify(sessionRepository, never()).save(any());
    }

    @Test
    void start_unknownGame_throwsNotFound() {
        given(gameRepository.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> sessionService.start(1L))
                .isInstanceOf(GameNotFoundException.class);
    }

    @Test
    void randomStart_picksAReadyGameOfThatDifficulty() {
        given(gameRepository.findByStateAndGameMode(GameState.READY, "moeilijk"))
                .willReturn(List.of(game(4L, GameState.READY), game(5L, GameState.READY)));

        assertThat(sessionService.randomStart("moeilijk")).isIn(4L, 5L);
    }

    @Test
    void getActiveSession_finishedSession_isAConflict() {
        Session session = session(0, 0, 0);
        session.setState(SessionState.FINISHED);
        given(sessionRepository.findById(session.getId())).willReturn(Optional.of(session));

        assertThatThrownBy(() -> sessionService.getActiveSession(session.getId()))
                .isInstanceOf(SessionAlreadyFinishedException.class);
    }

    @Test
    void getActiveSession_unknownSession_throwsNotFound() {
        UUID id = UUID.randomUUID();
        given(sessionRepository.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> sessionService.getActiveSession(id))
                .isInstanceOf(SessionNotFoundException.class);
    }

    @Test
    void finish_withoutMistakes_addsTheTwentyPointBonus() {
        Session session = session(90, 0, 0);
        given(sessionRepository.findById(session.getId())).willReturn(Optional.of(session));
        given(sessionRepository.save(session)).willReturn(session);

        sessionService.finish(session.getId());

        assertThat(session.getScore()).isEqualTo(110);
        assertThat(session.getState()).isEqualTo(SessionState.FINISHED);
        assertThat(session.getFinishedAt()).isNotNull();
    }

    @Test
    void finish_afterAWrongGuess_addsNoBonus() {
        Session session = session(90, 1, 0);
        given(sessionRepository.findById(session.getId())).willReturn(Optional.of(session));
        given(sessionRepository.save(session)).willReturn(session);

        sessionService.finish(session.getId());

        assertThat(session.getScore()).isEqualTo(90);
    }

    @Test
    void finish_withoutFindingACard_addsNoBonus() {
        Session session = session(0, 0, 0);
        given(sessionRepository.findById(session.getId())).willReturn(Optional.of(session));
        given(sessionRepository.save(session)).willReturn(session);

        sessionService.finish(session.getId());

        assertThat(session.getScore()).isZero();
    }

    @Test
    void finishIfAssassinated_oneAssassin_keepsPlaying() {
        Session session = session(0, 0, 1);

        sessionService.finishIfAssassinated(session);

        assertThat(session.getState()).isEqualTo(SessionState.IN_PROGRESS);
        verify(sessionRepository, never()).save(any());
    }

    @Test
    void finishIfAssassinated_secondAssassin_endsTheSession() {
        Session session = session(40, 0, 2);
        given(sessionRepository.findById(session.getId())).willReturn(Optional.of(session));
        given(sessionRepository.save(session)).willReturn(session);

        sessionService.finishIfAssassinated(session);

        assertThat(session.getState()).isEqualTo(SessionState.FINISHED);
        assertThat(session.getScore()).isEqualTo(40);
    }

    private static Game game(Long id, GameState state) {
        Game game = new Game();
        game.setId(id);
        game.setState(state);
        game.setCards(List.of());
        return game;
    }

    private static Session session(int score, int wrongGuesses, int assassinGuesses) {
        Session session = new Session();
        session.setId(UUID.randomUUID());
        session.setScore(score);
        session.setWrongGuesses(wrongGuesses);
        session.setAssassinGuesses(assassinGuesses);
        return session;
    }
}
