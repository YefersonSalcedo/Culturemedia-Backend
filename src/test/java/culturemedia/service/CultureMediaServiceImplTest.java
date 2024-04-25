package culturemedia.service;

import culturemedia.exception.VideoNotFoundException;
import culturemedia.model.Video;
import culturemedia.model.View;
import culturemedia.repository.Impl.VideoRepositoryImpl;
import culturemedia.repository.Impl.ViewsRepositoryImpl;
import culturemedia.repository.VideoRepository;
import culturemedia.repository.ViewsRepository;
import culturemedia.service.Impl.CultureMediaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class CultureMediaServiceImplTest {

    private CultureMediaService cultureMediaService;

    @BeforeEach
    void init() {
        VideoRepository videoRepository = new VideoRepositoryImpl();
        ViewsRepository viewsRepository = new ViewsRepositoryImpl();
        cultureMediaService = new CultureMediaServiceImpl(videoRepository, viewsRepository);
    }

    private void createVideos (){
        List<Video> videos = List.of(new Video("01", "Título 1", "----", 4.5),
                new Video("02", "Título 2", "----", 5.5),
                new Video("03", "Título 3", "----", 4.4),
                new Video("04", "Título 4", "----", 3.5),
                new Video("05", "Clic 5", "----", 5.7),
                new Video("06", "Clic 6", "----", 5.1));


        for ( Video video : videos ) {
            cultureMediaService.save( video );
        }
    }


    @Test
    void when_FindAll_all_videos_should_be_returned_successfully() throws VideoNotFoundException {
        createVideos();
        List<Video> videos = cultureMediaService.findAll();
        assertEquals(6, videos.size());
    }


    @Test
    void when_FindAll_does_not_find_any_video_an_VideoNotFoundException_should_be_thrown_successfully() {
        // Asegura que se lance VideoNotFoundException cuando se llame a findAll() en un repositorio vacío
        assertThrows(VideoNotFoundException.class, () -> cultureMediaService.findAll());
    }

    @Test
    void when_Find_by_title_should_return_matching_video() throws VideoNotFoundException {
        createVideos();

        List<Video> videos = cultureMediaService.find("Título 2");
        assertEquals(1, videos.size());
        assertEquals("Título 2", videos.get(0).title());
    }

    @Test
    void when_Find_by_title_with_nonexistent_title_should_throw_VideoNotFoundException() {
        createVideos();

        assertThrows(VideoNotFoundException.class, () -> cultureMediaService.find("Título 10"));
    }

    @Test
    void when_Find_by_duration_range_should_return_videos_within_range() throws VideoNotFoundException {
        createVideos();

        List<Video> videos = cultureMediaService.find(3.5, 5.0);
        assertEquals(3, videos.size());

        for (Video video : videos) {
            assertTrue(video.duration() >= 3.5 && video.duration() <= 5.0);
        }
    }

    @Test
    void when_Find_by_duration_range_with_invalid_range_should_throw_VideoNotFoundException() {
        createVideos();

        assertThrows(VideoNotFoundException.class, () -> cultureMediaService.find(6.0, 2.0));
    }


    @Test
    void when_Save_video_should_be_saved_successfully() { // Asegura que un Video se guarde correctamente
        Video video = new Video("07", "Título 7", "----", 4.8);
        Video savedVideo = cultureMediaService.save(video);
        assertEquals(video.title(), savedVideo.title());
    }

    @Test
    void when_Save_View_should_be_updated_successfully() { // Asegura que una View se guarde correctamente
        Video video = new Video("07", "Título 7", "----", 4.8);
        View view = new View("Juanito perez", LocalDateTime.now(), 25, video);
        View savedView = cultureMediaService.save(view);
        assertEquals(view.userFullName(), savedView.userFullName());
    }
}
