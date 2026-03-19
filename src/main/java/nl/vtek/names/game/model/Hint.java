package nl.vtek.names.game.model;

import jakarta.persistence.*;

@Entity
public class Hint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hintId;

    @Column(nullable = false, length = 50)
    private String hintContent;

    private int gameId;


    public Hint() {}

    public Hint(String hintContent, int gameId) {
        this.hintContent = hintContent;
        this.gameId = gameId;
    }


    public String getHintContent() {
        return hintContent;
    }

    public void setHintContent(String hintContent) {
        this.hintContent = hintContent;
    }


    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getHintId() {
        return hintId;
    }

    public void setHintId(int hintId) {
        this.hintId = hintId;
    }
}