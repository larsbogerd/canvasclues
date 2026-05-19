package nl.vtek.names.art.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import nl.vtek.names.game.model.Card;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class Artwork {

    /**
     * Uses the ArtIC API's {@code image_id} (a UUID) as our local primary key.
     * This lets us deduplicate artworks across game sessions without a separate lookup,
     * and is also the value plugged into {@link nl.vtek.names.art.util.IiifUrlBuilder}
     * to build the IIIF image URL.
     */
    @Id
    private UUID id;
    @Column(columnDefinition = "TEXT")
    private String title;
    @Column(columnDefinition = "TEXT")
    private String artistDisplay;
    @Column(columnDefinition = "TEXT")
    private String dateDisplay;
    @Column(columnDefinition = "TEXT")
    private String mediumDisplay;
    @Column(columnDefinition = "TEXT")
    private String placeOfOrigin;
    @Column(columnDefinition = "TEXT")
    private String dimensions;
    @Column(columnDefinition = "TEXT")
    private String departmentTitle;

    private int timesLoaded;
    private int timesSpymasterPick;
    private LocalDateTime firstUsedAt;
    private LocalDateTime lastUsedAt;
    private int timesCorrectGuess;
    private int timesBadGuess;

    @OneToMany(mappedBy = "artwork")
    private List<Card> cards;

    public Artwork() {
    }

    public Artwork(UUID id, String title, String artistDisplay, String dateDisplay,
                   String mediumDisplay, String placeOfOrigin, String dimensions,
                   String departmentTitle) {
        this.id = id;
        this.title = title;
        this.artistDisplay = artistDisplay;
        this.dateDisplay = dateDisplay;
        this.mediumDisplay = mediumDisplay;
        this.placeOfOrigin = placeOfOrigin;
        this.dimensions = dimensions;
        this.departmentTitle = departmentTitle;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getDepartmentTitle() {
        return departmentTitle;
    }

    public void setDepartmentTitle(String departmentTitle) {
        this.departmentTitle = departmentTitle;
    }

    public int getTimesLoaded() {
        return timesLoaded;
    }

    public void setTimesLoaded(int timesLoaded) {
        this.timesLoaded = timesLoaded;
    }

    public int getTimesSpymasterPick() {
        return timesSpymasterPick;
    }

    public void setTimesSpymasterPick(int timesSpymasterPick) {
        this.timesSpymasterPick = timesSpymasterPick;
    }

    public LocalDateTime getFirstUsedAt() {
        return firstUsedAt;
    }

    public void setFirstUsedAt(LocalDateTime firstUsedAt) {
        this.firstUsedAt = firstUsedAt;
    }

    public LocalDateTime getLastUsedAt() {
        return lastUsedAt;
    }

    public void setLastUsedAt(LocalDateTime lastUsedAt) {
        this.lastUsedAt = lastUsedAt;
    }

    public int getTimesCorrectGuess() {
        return timesCorrectGuess;
    }

    public void setTimesCorrectGuess(int timesCorrectGuess) {
        this.timesCorrectGuess = timesCorrectGuess;
    }

    public int getTimesBadGuess() {
        return timesBadGuess;
    }

    public void setTimesBadGuess(int timesBadGuess) {
        this.timesBadGuess = timesBadGuess;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
