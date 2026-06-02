package nl.vtek.names.game.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private GameState state = GameState.CREATING;

    private int spyScore;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private int playCount;

    @OneToMany(mappedBy = "game")
    private List<Card> cards;

    @OneToMany(mappedBy = "game")
    private List<Hint> hints;

    private String gameModeName;


    public Game() {
    }

    public Game(Long id, GameState state, int spyScore, LocalDateTime createdAt, int playCount) {
        this.id = id;
        this.state = state;
        this.spyScore = spyScore;
        this.createdAt = createdAt;
        this.playCount = playCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public int getSpyScore() {
        return spyScore;
    }

    public void setSpyScore(int spyScore) {
        this.spyScore = spyScore;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<Hint> getHints() {
        return hints;
    }

    public void setHints(List<Hint> hints) {
        this.hints = hints;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public String getGameModeName() {
        return gameModeName;
    }

    public void setGameModeName(String gameModeName) {
        this.gameModeName = gameModeName;
    }
}


