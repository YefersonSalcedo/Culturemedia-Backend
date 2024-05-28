package culturemedia.service.Impl;

import culturemedia.exception.VideoNotFoundException;
import culturemedia.model.Video;
import culturemedia.model.View;
import culturemedia.repository.VideoRepository;
import culturemedia.repository.ViewsRepository;
import culturemedia.service.CultureMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class CultureMediaServiceImpl implements CultureMediaService {

    @Autowired
    VideoRepository videoRepository;
    @Autowired
    ViewsRepository viewsRepository;


    @Override
    public List<Video> findAll() throws VideoNotFoundException {
        List<Video> videos = videoRepository.findAll();
        if (videos.isEmpty()) {
            throw new VideoNotFoundException();
        }
        return videos;
    }

    @Override
    public List<Video> findByTitle(String title) throws VideoNotFoundException {
        List<Video> videos = videoRepository.findByTitle(title);
        if (videos.isEmpty()) {
            throw new VideoNotFoundException();
        }
        else{
            return videos;
        }
    }

    @Override
    public List<Video> findByDuration(double fromDuration, double toDuration) throws VideoNotFoundException {
        List<Video> videos = videoRepository.findByDurationBetween(fromDuration, toDuration);
        if (videos.isEmpty()) {
            throw new VideoNotFoundException();
        } else {
            return videos;
        }
    }

    @Override
    public Video save(Video video) {
        return videoRepository.save(video);
    }

    @Override
    public List<Video> save(List<Video> videos) {
        List<Video> savedVideos = new ArrayList<>();
        for (Video video : videos) {
            savedVideos.add(videoRepository.save(video));
        }
        return savedVideos;
    }

    @Override
    public View save(View view) {
        return viewsRepository.save(view);
    }

}
