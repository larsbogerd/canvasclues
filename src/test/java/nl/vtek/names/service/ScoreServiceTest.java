//package nl.vtek.names.service;
//
//import nl.vtek.names.game.exception.GameNotFoundException;
//import nl.vtek.names.game.exception.MinimumSelectedException;
//import nl.vtek.names.game.exception.NullScoreException;
//import nl.vtek.names.game.model.Card;
//import nl.vtek.names.game.model.CardType;
//import nl.vtek.names.game.model.Game;
//import nl.vtek.names.game.model.Session;
//import nl.vtek.names.game.repository.GameRepository;
//import nl.vtek.names.game.repository.SessionRepository;
//import nl.vtek.names.game.service.ScoreService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.InputMismatchException;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class ScoreServiceTest {
//
//    @Mock
//    private GameRepository gameRepository;
//
//    @Mock
//    private SessionRepository sessionRepository;
//
//    @InjectMocks
//    private ScoreService scoreService;
//
//    private Game game;
//
//    @BeforeEach
//    void setUp() {
//        game = new Game();
//        game.setId(1L);
//    }
//
//    /*
//Spymaster score
// */
//
//    @Test
//    void calcSpyScore_shouldMultiplyScoreBy20AndSave() {
//        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
//
//        scoreService.calcSpyScore(1L, 5);
//
//        ArgumentCaptor<Game> captor = ArgumentCaptor.forClass(Game.class);
//        verify(gameRepository).save(captor.capture());
//        assertThat(captor.getValue().getSpyScore()).isEqualTo(100);
//    }
//
//    @Test
//    void calcSpyScore_withZero_shouldSetScoreToZero() {
//        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
//
//        scoreService.calcSpyScore(1L, 0);
//
//        ArgumentCaptor<Game> captor = ArgumentCaptor.forClass(Game.class);
//        verify(gameRepository).save(captor.capture());
//        assertThat(captor.getValue().getSpyScore()).isEqualTo(0);
//    }
//
//    @Test
//    void calcSpyScore_withNullScore_shouldThrowNullScoreException() {
//        assertThatThrownBy(() -> scoreService.calcSpyScore(1L, null))
//                .isInstanceOf(NullScoreException.class)
//                .hasMessageContaining("Spymaster score can't be null");
//
//        verify(gameRepository, never()).save(any());
//    }
//
//    @Test
//    void calcSpyScore_withUnknownGameId_shouldThrowGameNotFoundException() {
//        when(gameRepository.findById(99L)).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> scoreService.calcSpyScore(99L, 3))
//                .isInstanceOf(GameNotFoundException.class);
//
//        verify(gameRepository, never()).save(any());
//    }
//
///*
//Operative score
// */
//
//    @Test
//    void validateSpyScore_withExactlyThreeCards_shouldNotThrow() {
//        assertThatNoException().isThrownBy(() -> scoreService.validateSpyScore(3));
//    }
//
//    @Test
//    void validateSpyScore_withMoreThanThreeCards_shouldNotThrow() {
//        assertThatNoException().isThrownBy(() -> scoreService.validateSpyScore(10));
//    }
//
//    @Test
//    void validateSpyScore_withTwoCards_shouldThrowMinimumSelectedException() {
//        assertThatThrownBy(() -> scoreService.validateSpyScore(2))
//                .isInstanceOf(MinimumSelectedException.class)
//                .hasMessageContaining("Selecteer minimaal 3 kaarten");
//    }
//
//    @Test
//    void validateSpyScore_withZeroCards_shouldThrowMinimumSelectedException() {
//        assertThatThrownBy(() -> scoreService.validateSpyScore(0))
//                .isInstanceOf(MinimumSelectedException.class);
//    }
//
//    @Test
//    void handleSpyScore_withValidInput_shouldSaveCorrectScore() {
//        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
//
//        scoreService.handleSpyScore(1L, 4);
//
//        ArgumentCaptor<Game> captor = ArgumentCaptor.forClass(Game.class);
//        verify(gameRepository).save(captor.capture());
//        assertThat(captor.getValue().getSpyScore()).isEqualTo(80);
//    }
//
//    @Test
//    void handleSpyScore_withScoreBelowThree_shouldThrowBeforeSaving() {
//        assertThatThrownBy(() -> scoreService.handleSpyScore(1L, 2))
//                .isInstanceOf(MinimumSelectedException.class)
//                .hasMessageContaining("Selecteer minimaal 3 kaarten");
//
//        verify(gameRepository, never()).findById(any());
//        verify(gameRepository, never()).save(any());
//    }
//
//    @Test
//    void handleSpyScore_withNullScore_shouldThrowInputMismatchException() {
//        assertThatThrownBy(() -> scoreService.handleSpyScore(1L, null))
//                .isInstanceOf(NullScoreException.class);
//
//        verify(gameRepository, never()).save(any());
//    }
//
//    @Test
//    void getMultiplier_withStreakOfOne_shouldReturnOne() {
//        assertThat(scoreService.getMultiplier(1)).isEqualTo(1.0);
//    }
//
//    @Test
//    void getMultiplier_withStreakOfTwo_shouldReturnOnePointFive() {
//        assertThat(scoreService.getMultiplier(2)).isEqualTo(1.5);
//    }
//
//    @Test
//    void getMultiplier_withStreakOfThree_shouldReturnTwo() {
//        assertThat(scoreService.getMultiplier(3)).isEqualTo(2.0);
//    }
//
//    @Test
//    void getMultiplier_withStreakOfFourOrMore_shouldReturnThree() {
//        assertThat(scoreService.getMultiplier(4)).isEqualTo(3.0);
//        assertThat(scoreService.getMultiplier(10)).isEqualTo(3.0);
//    }
//
//    @Test
//    void calcOperativeScore_withStreakOfOne_shouldAdd20() {
//        assertThat(scoreService.calcOperativeScore(0, 1)).isEqualTo(20);
//    }
//
//    @Test
//    void calcOperativeScore_withStreakOfTwo_shouldAdd30() {
//        assertThat(scoreService.calcOperativeScore(20, 2)).isEqualTo(50);
//    }
//
//    @Test
//    void calcOperativeScore_withStreakOfThree_shouldAdd40() {
//        assertThat(scoreService.calcOperativeScore(50, 3)).isEqualTo(90);
//    }
//
//    @Test
//    void calcOperativeScore_withStreakOfFour_shouldAdd60() {
//        assertThat(scoreService.calcOperativeScore(90, 4)).isEqualTo(150);
//    }
//
//    @Test
//    void recordGuess_correct_shouldIncrementStreakAndUpdateScore() {
//        Session session = new Session();
//        session.setScore(0);
//        session.setComboStreak(0);
//        session.setWrongGuesses(0);
//
//        Card card = new Card();
//        card.setSpymasterPick(true);
//        card.setType(CardType.PLAYABLE);
//
//        scoreService.recordGuess(session, card);
//
//        assertThat(session.getComboStreak()).isEqualTo(1);
//        assertThat(session.getScore()).isEqualTo(20);
//        verify(sessionRepository).save(session);
//    }
//
//    @Test
//    void recordGuess_wrongPlayable_shouldResetStreakAndIncrementWrongGuesses() {
//        Session session = new Session();
//        session.setScore(50);
//        session.setComboStreak(3);
//        session.setWrongGuesses(0);
//
//        Card card = new Card();
//        card.setSpymasterPick(false);
//        card.setType(CardType.PLAYABLE);
//
//        scoreService.recordGuess(session, card);
//
//        assertThat(session.getComboStreak()).isEqualTo(0);
//        assertThat(session.getWrongGuesses()).isEqualTo(1);
//        assertThat(session.getScore()).isEqualTo(50);
//        verify(sessionRepository).save(session);
//    }
//
//    @Test
//    void recordGuess_assassin_shouldResetStreakAndIncrementAssassinGuesses() {
//        Session session = new Session();
//        session.setScore(50);
//        session.setComboStreak(3);
//        session.setAssassinGuesses(0);
//
//        Card card = new Card();
//        card.setSpymasterPick(false);
//        card.setType(CardType.ASSASSIN);
//
//        scoreService.recordGuess(session, card);
//
//        assertThat(session.getComboStreak()).isEqualTo(0);
//        assertThat(session.getAssassinGuesses()).isEqualTo(1);
//        assertThat(session.getScore()).isEqualTo(50);
//        verify(sessionRepository).save(session);
//    }
//
//    @Test
//    void recordGuess_correctAfterWrong_shouldRestartStreakAtOne() {
//        Session session = new Session();
//        session.setScore(0);
//        session.setComboStreak(0);
//        session.setWrongGuesses(1);
//
//        Card card = new Card();
//        card.setSpymasterPick(true);
//        card.setType(CardType.PLAYABLE);
//
//        scoreService.recordGuess(session, card);
//
//        assertThat(session.getComboStreak()).isEqualTo(1);
//        assertThat(session.getScore()).isEqualTo(20);
//    }
//}