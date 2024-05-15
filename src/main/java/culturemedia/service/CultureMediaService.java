package culturemedia.service;

import culturemedia.exception.VideoNotFoundException;
import culturemedia.model.Video;
import culturemedia.model.View;

import java.util.List;

public interface CultureMediaService {
    List<Video> findAll() throws VideoNotFoundException;
    List<Video> find(String title) throws VideoNotFoundException;
    List<Video> find(Double fromDuration, Double toDuration) throws VideoNotFoundException;
    Video save(Video video);
    List<Video> save(List<Video> videos);
    View save(View view);
}
