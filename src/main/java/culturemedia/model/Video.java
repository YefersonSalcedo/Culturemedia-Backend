package culturemedia.model;

import jakarta.persistence.*;

import java.text.MessageFormat;


@Entity
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;
    private String title;
    private String description;
    private Double duration;

    // Constructor
    public Video(String title, String description, Double duration) {
        this.title = title;
        this.description = description;
        this.duration = duration;
    }
    public Video() {}

    // Getters y Setters
    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String toString() {
        return MessageFormat.format("Video({0}, {1}, {2})", title, description, duration);
    }


}
