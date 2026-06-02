package nl.vtek.names.game.service;

import nl.vtek.names.game.exception.UnknownGameModeException;
import nl.vtek.names.game.interfaces.GameMode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GameModeRegistry {

    private final Map<String, GameMode> modes;

    public GameModeRegistry(List<GameMode> gameModes) {
        this.modes = gameModes.stream()
                .collect(Collectors.toMap(GameMode::gameMode, m -> m));
    }

    public GameMode getMode(String name) {
        GameMode mode = modes.get(name);
        if (mode == null) {
            throw new UnknownGameModeException();
        }
        return mode;
    }
}
