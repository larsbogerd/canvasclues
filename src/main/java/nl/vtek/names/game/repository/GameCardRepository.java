package nl.vtek.names.game.repository;

import nl.vtek.names.game.model.GameCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GameCardRepository extends JpaRepository<GameCard, UUID> {

    List<GameCard> findByGameId(int gameId);
}
