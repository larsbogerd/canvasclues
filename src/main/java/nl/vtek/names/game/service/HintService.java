package nl.vtek.names.game.service;

import nl.vtek.names.game.dto.HintRequest;
import nl.vtek.names.game.dto.HintResponse;
import nl.vtek.names.game.mapper.HintMapper;
import nl.vtek.names.game.model.Game;
import nl.vtek.names.game.model.GameState;
import nl.vtek.names.game.model.Hint;
import nl.vtek.names.game.repository.GameRepository;
import nl.vtek.names.game.repository.HintRepo;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.Optional;

@Service
public class HintService {

    private final HintRepo hintRepo;
    private final GameRepository gameRepository;


    public HintService(HintRepo hintRepo, GameRepository gameRepository) {
        this.hintRepo = hintRepo;
        this.gameRepository = gameRepository;
    }


    public void createHint(HintRequest request) {
        Game game = gameRepository.findById(request.getGameId())
                .orElseThrow(() -> new RuntimeException("Game not found"));

        Hint hint = HintMapper.toEntity(request, game);
        validateHint(hint);
        hintRepo.save(hint);

        game.setState(GameState.READY);
        gameRepository.save(game);
    }


    public Optional<HintResponse> getHintByGameId(int gameId) {
        return hintRepo.findByGame_Id(gameId)
                .map(HintMapper::toHintResponse);
    }


    private void validateHint(Hint hint) {
        if (hint == null || hint.getContent().isBlank()) {
            throw new InputMismatchException("Hint mag niet leeg zijn");
        }

        if (!hint.getContent().matches("[a-zA-Z]+")) {
            throw new InputMismatchException("Hint moet één woord zijn en mag geen cijfers bevatten");
        }
    }


}
