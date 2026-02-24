package nl.vtek.names.helloworld.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "test")  // map to your actual table name
public class ArtPiece {

    @Id
    private Long id;

    @Column(name = "message")  // map to your actual column
    private String artPieceName;

    public ArtPiece() {}

    public ArtPiece(Long id, String artPieceName) {
        this.id = id;
        this.artPieceName = artPieceName;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArtPieceName() {
        return artPieceName;
    }

    public void setArtPieceName(String artPieceName) {
        this.artPieceName = artPieceName;
    }

}
