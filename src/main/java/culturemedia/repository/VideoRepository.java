package culturemedia.repository;

import culturemedia.exception.VideoNotFoundException;
import culturemedia.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByTitle(String title) throws VideoNotFoundException;
    List<Video> findByDurationBetween(double fromDuration, double toDuration) throws VideoNotFoundException;

}
