package nl.vtek.names.game.service;

import nl.vtek.names.game.dto.HintResponse;
import nl.vtek.names.game.exception.GameNotFoundException;
import nl.vtek.names.game.mapper.HintMapper;
import nl.vtek.names.game.model.Game;
import nl.vtek.names.game.model.Hint;
import nl.vtek.names.game.repository.GameRepository;
import nl.vtek.names.game.repository.HintRepository;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class HintService {

    private final HintRepository hintRepository;
    private final GameRepository gameRepository;


    public HintService(HintRepository hintRepository, GameRepository gameRepository) {
        this.hintRepository = hintRepository;
        this.gameRepository = gameRepository;
    }


    public void createHint(Long gameId, String content) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException(gameId));

        Hint hint = HintMapper.toEntity(content, game);
        validateHint(hint);
        hintRepository.save(hint);
    }


    public Optional<HintResponse> getHintByGameId(Long gameId) {
        return hintRepository.findByGame_Id(gameId).map(HintMapper::toHintResponse);
    }


    private void validateHint(Hint hint) {
        if (hint == null || hint.getContent().isBlank()) {
            throw new InputMismatchException("Hint mag niet leeg zijn");
        }

        if (!hint.getContent().matches("[a-zA-Z]+")) {
            throw new InputMismatchException("Hint moet één woord zijn en mag geen cijfers bevatten");
        }
    }

    public List<HintResponse> findAllHints() {
        return hintRepository.findAll().stream().map(HintMapper::toHintResponse).toList();
    }

    public Map<String, Integer> countHints() {
        List<HintResponse> hints = findAllHints();

        Map<String, Integer> counts = new HashMap<>();

        for (HintResponse hint : hints) {
            String content = hint.content();
            counts.put(content, counts.getOrDefault(content, 0) + 1);
        }
        return counts;
    }


    public void putHintDataInFile() {
        Map<String, Integer> counts = countHints();
        try (FileWriter myWriter = new FileWriter("hintData.csv")) {
            for (Map.Entry<String, Integer> entry : counts.entrySet()) {
                myWriter.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<HintResponse> getGameHints() {
        List<Hint> hints = hintRepository.findAll();
        return hints.stream().map(HintMapper::toHintResponse).toList();
    }
}
