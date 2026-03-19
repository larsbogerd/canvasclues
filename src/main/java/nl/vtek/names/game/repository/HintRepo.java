package nl.vtek.names.game.repository;

import nl.vtek.names.game.model.Hint;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface HintRepo extends JpaRepository<Hint, Integer> {


    Optional<Hint> findByGameId(int gameId);


}