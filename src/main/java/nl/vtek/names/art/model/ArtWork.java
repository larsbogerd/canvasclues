package nl.vtek.names.art.model;

import jakarta.persistence.Id;

public class ArtWork {

    @Id
    private int id;
    private String imageId;
    private String title;
    private String artistDisplay;
    private String dateDisplay;
    private String mediumDisplay;
    private String placeOfOrigin;

    public ArtWork() {
    }

    public ArtWork(int id, String imageId, String title, String artistDisplay, String dateDisplay,
                   String mediumDisplay, String placeOfOrigin) {
        this.id = id;
        this.imageId = imageId;
        this.title = title;
        this.artistDisplay = artistDisplay;
        this.dateDisplay = dateDisplay;
        this.mediumDisplay = mediumDisplay;
        this.placeOfOrigin = placeOfOrigin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtistDisplay() {
        return artistDisplay;
    }

    public void setArtistDisplay(String artistDisplay) {
        this.artistDisplay = artistDisplay;
    }

    public String getDateDisplay() {
        return dateDisplay;
    }

    public void setDateDisplay(String dateDisplay) {
        this.dateDisplay = dateDisplay;
    }

    public String getMediumDisplay() {
        return mediumDisplay;
    }

    public void setMediumDisplay(String mediumDisplay) {
        this.mediumDisplay = mediumDisplay;
    }

    public String getPlaceOfOrigin() {
        return placeOfOrigin;
    }

    public void setPlaceOfOrigin(String placeOfOrigin) {
        this.placeOfOrigin = placeOfOrigin;
    }

}
