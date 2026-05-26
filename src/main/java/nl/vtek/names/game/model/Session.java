package nl.vtek.names.game.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ManyToOne
    @JoinColumn(name = "hint_id")
    private Hint hint;

    @CreationTimestamp
    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;

    private int score;

    @Enumerated(EnumType.STRING)
    private SessionState state = SessionState.IN_PROGRESS;

    private int comboStreak;
    private int wrongGuesses;
    private int assassinGuesses;


    public Session() {
    }

    public Session(Game game, Hint hint) {
        this.game = game;
        this.hint = hint;
    }



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Hint getHint() {
        return hint;
    }

    public void setHint(Hint hint) {
        this.hint = hint;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public SessionState getState() {
        return state;
    }

    public void setState(SessionState state) {
        this.state = state;
    }

    public int getComboStreak() {
        return comboStreak;
    }

    public void setComboStreak(int comboStreak) {
        this.comboStreak = comboStreak;
    }

    public int getWrongGuesses() {
        return wrongGuesses;
    }

    public void setWrongGuesses(int wrongGuesses) {
        this.wrongGuesses = wrongGuesses;
    }

    public int getAssassinGuesses() {
        return assassinGuesses;
    }

    public void setAssassinGuesses(int assassinGuesses) {
        this.assassinGuesses = assassinGuesses;
    }
}
