package nl.vtek.names.game.strategy;

import nl.vtek.names.game.interfaces.GameMode;
import nl.vtek.names.game.model.CardType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class GameModeStrategyTest {

    static Stream<Arguments> modes() {
        return Stream.of(
                Arguments.of(new EasyModeStrategy(), "makkelijk", 4),
                Arguments.of(new MediumModeStrategy(), "gemiddeld", 6),
                Arguments.of(new HardModeStrategy(), "moeilijk", 8)
        );
    }

    @ParameterizedTest
    @MethodSource("modes")
    void distribution_fillsTheBoardWithTheModesAssassinCount(GameMode mode, String name, int assassins) {
        List<CardType> distribution = mode.getCardDistribution();

        assertThat(mode.gameMode()).isEqualTo(name);
        assertThat(distribution).hasSize(mode.boardSize());
        assertThat(Collections.frequency(distribution, CardType.ASSASSIN)).isEqualTo(assassins);
        assertThat(Collections.frequency(distribution, CardType.PLAYABLE)).isEqualTo(mode.boardSize() - assassins);
    }
}
