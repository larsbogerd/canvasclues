package nl.vtek.names.game.repository;

import nl.vtek.names.game.model.Hint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HintRepository extends JpaRepository<Hint, UUID> {


    Optional<Hint> findByGame_Id(Long gameId);

    List<Hint> findAll();
}