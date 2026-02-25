package nl.vtek.names.game.model;

import jakarta.persistence.*;

@Entity
public class Hint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String hintContent;


    public Hint() {}

    public Hint(String hintContent) {
        this.hintContent = hintContent;
    }


    public String getHintContent() {
        return hintContent;
    }

    public void setHintContent(String hintContent) {
        this.hintContent = hintContent;
    }

}