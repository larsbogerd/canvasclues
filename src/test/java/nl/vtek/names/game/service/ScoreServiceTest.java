package nl.vtek.names.game.service;

import nl.vtek.names.game.exception.GameNotFoundException;
import nl.vtek.names.game.exception.MinimumSelectedException;
import nl.vtek.names.game.exception.NullScoreException;
import nl.vtek.names.game.model.Card;
import nl.vtek.names.game.model.CardType;
import nl.vtek.names.game.model.Game;
import nl.vtek.names.game.model.Session;
import nl.vtek.names.game.repository.GameRepository;
import nl.vtek.names.game.repository.SessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class ScoreServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private ScoreService scoreService;

    @Test
    void handleSpyScore_storesTwentyPointsPerPickedCard() {
        Game game = new Game();
        given(gameRepository.findById(1L)).willReturn(Optional.of(game));

        scoreService.handleSpyScore(1L, 4);

        assertThat(game.getSpyScore()).isEqualTo(80);
        verify(gameRepository).save(game);
    }

    @Test
    void handleSpyScore_acceptsTheMinimumOfTwoCards() {
        Game game = new Game();
        given(gameRepository.findById(1L)).willReturn(Optional.of(game));

        scoreService.handleSpyScore(1L, 2);

        assertThat(game.getSpyScore()).isEqualTo(40);
    }

    @Test
    void handleSpyScore_rejectsFewerThanTwoCardsBeforeLookingUpTheGame() {
        assertThatThrownBy(() -> scoreService.handleSpyScore(1L, 1))
                .isInstanceOf(MinimumSelectedException.class)
                .hasMessage("Selecteer minimaal 2 kaarten");

        verifyNoInteractions(gameRepository);
    }

    @Test
    void handleSpyScore_rejectsNull() {
        assertThatThrownBy(() -> scoreService.handleSpyScore(1L, null))
                .isInstanceOf(NullScoreException.class);

        verifyNoInteractions(gameRepository);
    }

    @Test
    void calcSpyScore_unknownGame_throwsNotFound() {
        given(gameRepository.findById(99L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> scoreService.calcSpyScore(99L, 3))
                .isInstanceOf(GameNotFoundException.class);

        verify(gameRepository, never()).save(any());
    }

    @ParameterizedTest
    @CsvSource({"0, 1.0", "1, 1.0", "2, 1.5", "3, 2.0", "4, 3.0", "10, 3.0"})
    void getMultiplier_growsWithTheStreakAndCapsAtFour(int streak, double expected) {
        assertThat(scoreService.getMultiplier(streak)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"0, 1, 20", "20, 2, 50", "50, 3, 90", "90, 4, 150", "150, 7, 210"})
    void calcOperativeScore_addsTwentyTimesTheMultiplier(int current, int streak, int expected) {
        assertThat(scoreService.calcOperativeScore(current, streak)).isEqualTo(expected);
    }

    @Test
    void recordGuess_spymasterPick_extendsTheStreakAndScores() {
        Session session = session(20, 1);

        scoreService.recordGuess(session, card(CardType.PLAYABLE, true));

        assertThat(session.getComboStreak()).isEqualTo(2);
        assertThat(session.getScore()).isEqualTo(50);
        assertThat(session.getWrongGuesses()).isZero();
        verify(sessionRepository).save(session);
    }

    @Test
    void recordGuess_unpickedPlayable_breaksTheStreakAndCountsAsWrong() {
        Session session = session(50, 3);

        scoreService.recordGuess(session, card(CardType.PLAYABLE, false));

        assertThat(session.getComboStreak()).isZero();
        assertThat(session.getWrongGuesses()).isEqualTo(1);
        assertThat(session.getAssassinGuesses()).isZero();
        assertThat(session.getScore()).isEqualTo(50);
    }

    @Test
    void recordGuess_assassin_breaksTheStreakAndCountsAsAssassin() {
        Session session = session(50, 3);

        scoreService.recordGuess(session, card(CardType.ASSASSIN, false));

        assertThat(session.getComboStreak()).isZero();
        assertThat(session.getAssassinGuesses()).isEqualTo(1);
        assertThat(session.getWrongGuesses()).isZero();
        assertThat(session.getScore()).isEqualTo(50);
    }

    private static Session session(int score, int streak) {
        Session session = new Session();
        session.setScore(score);
        session.setComboStreak(streak);
        return session;
    }

    private static Card card(CardType type, boolean spymasterPick) {
        Card card = new Card(new Game(), type);
        card.setSpymasterPick(spymasterPick);
        return card;
    }
}
