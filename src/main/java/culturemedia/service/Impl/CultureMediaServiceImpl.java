package culturemedia.service.Impl;

import culturemedia.exception.VideoNotFoundException;
import culturemedia.model.Video;
import culturemedia.model.View;
import culturemedia.repository.VideoRepository;
import culturemedia.repository.ViewsRepository;
import culturemedia.service.CultureMediaService;


import java.util.ArrayList;
import java.util.List;

public class CultureMediaServiceImpl implements CultureMediaService {
    private final VideoRepository videoRepository;
    private final ViewsRepository viewsRepository;

    public CultureMediaServiceImpl(VideoRepository videoRepository, ViewsRepository viewsRepository) {
        this.videoRepository = videoRepository;
        this.viewsRepository = viewsRepository;

    }

    @Override
    public List<Video> findAll() throws VideoNotFoundException {
        List<Video> videos = videoRepository.findAll();
        if (videos.isEmpty()) {
            throw new VideoNotFoundException();
        }
        return videos;
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

    @Override
    public List<Video> find(String title) throws VideoNotFoundException {
        List<Video> videos = videoRepository.find(title);
        if (videos.isEmpty()) {
            throw new VideoNotFoundException();
        }
        else{
            return videos;
        }
    }

    @Override
    public List<Video> find(Double fromDuration, Double toDuration) throws VideoNotFoundException {
        List<Video> videos = videoRepository.find(fromDuration,toDuration);
        if (videos.isEmpty()) {
            throw new VideoNotFoundException();
        }
        else {
            return videos;
        }
    }
}
