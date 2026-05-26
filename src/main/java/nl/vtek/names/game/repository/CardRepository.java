package nl.vtek.names.game.repository;

import nl.vtek.names.game.model.Card;
import nl.vtek.names.game.model.CardType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {

    List<Card> findByGame_Id(Long gameId);

}
