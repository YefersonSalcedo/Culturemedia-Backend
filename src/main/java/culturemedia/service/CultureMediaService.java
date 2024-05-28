package culturemedia.service;

import culturemedia.exception.VideoNotFoundException;
import culturemedia.model.Video;
import culturemedia.model.View;

import java.util.List;

public interface CultureMediaService {
    List<Video> findAll() throws VideoNotFoundException;
    Video save(Video video);
    List<Video> findByTitle(String title) throws VideoNotFoundException;
    List<Video> findByDuration(double fromDuration, double toDuration) throws VideoNotFoundException;
    List<Video> save(List<Video> videos);
    View save(View view);
}
