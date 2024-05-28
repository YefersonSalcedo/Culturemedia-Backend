package culturemedia.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
public class View {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userFullName;
    private LocalDateTime startPlayingTime;
    private Integer age;

    @ManyToOne
    private Video video;

    // Constructor
    public View(String userFullName, LocalDateTime startPlayingTime, Integer age, Video video) {
        this.userFullName = userFullName;
        this.startPlayingTime = startPlayingTime;
        this.age = age;
        this.video = video;
    }
    public View() {}

    // Getters


    public LocalDateTime getStartPlayingTime() {
        return startPlayingTime;
    }

    public Integer getAge() {
        return age;
    }

    public Video getVideo() {
        return video;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public void setStartPlayingTime(LocalDateTime startPlayingTime) {
        this.startPlayingTime = startPlayingTime;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}

