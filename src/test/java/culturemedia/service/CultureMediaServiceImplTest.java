package culturemedia.service;

import culturemedia.exception.VideoNotFoundException;
import culturemedia.model.Video;
import culturemedia.model.View;
import culturemedia.repository.VideoRepository;
import culturemedia.repository.ViewsRepository;
import culturemedia.service.Impl.CultureMediaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

class CultureMediaServiceImplTest {

    @InjectMocks
    private CultureMediaServiceImpl cultureMediaService;

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private ViewsRepository viewsRepository;

    private final Video video1 = new Video("Título 1", "Descripción 1", 4.5);
    private final Video video2 = new Video("Título 2", "Descripción 2", 5.5);
    private final Video video3 = new Video("Título 3", "Descripción 3", 4.4);
    private final Video video4 = new Video("Título 4", "Descripción 4", 3.5);
    private final Video video5 = new Video("Clic 5", "Descripción 5", 5.7);
    private final Video video6 = new Video("Clic 6", "Descripción 6", 5.1);

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void when_FindAll_all_videos_should_be_returned_successfully() throws VideoNotFoundException {
        doReturn(List.of(video1, video2, video3, video4, video5, video6)).when(videoRepository).findAll();
        List<Video> videos = cultureMediaService.findAll();
        assertEquals(6, videos.size());
    }

    @Test
    void when_FindAll_does_not_find_any_video_an_VideoNotFoundException_should_be_thrown_successfully() {
        doReturn(Collections.emptyList()).when(videoRepository).findAll();
        assertThrows(VideoNotFoundException.class, () -> cultureMediaService.findAll());
    }

    @Test
    void when_Find_by_title_should_return_matching_video() throws VideoNotFoundException {
        doReturn(List.of(video2)).when(videoRepository).findByTitle(eq("Título 2"));
        List<Video> videos = cultureMediaService.findByTitle("Título 2");
        assertEquals(1, videos.size());
        assertEquals(video2, videos.get(0));
    }

    @Test
    void when_Find_by_title_with_nonexistent_title_should_throw_VideoNotFoundException() throws VideoNotFoundException {
        doReturn(Collections.emptyList()).when(videoRepository).findByTitle(eq("Título -1"));
        assertThrows(VideoNotFoundException.class, () -> cultureMediaService.findByTitle("Título -1"));
    }

    @Test
    void when_Find_by_duration_range_should_return_videos_within_range() throws VideoNotFoundException {
        doReturn(List.of(video1, video2, video3, video4, video5)).when(videoRepository).findByDurationBetween(eq(3.5), eq(5.5));
        List<Video> videos = cultureMediaService.findByDuration(3.5, 5.5);
        assertEquals(5, videos.size());
    }

    @Test
    void when_Find_by_duration_range_with_invalid_range_should_throw_VideoNotFoundException() throws VideoNotFoundException {
        doReturn(Collections.emptyList()).when(videoRepository).findByDurationBetween(eq(6.0), eq(2.0));
        assertThrows(VideoNotFoundException.class, () -> cultureMediaService.findByDuration(6.0, 2.0));
    }

    @Test
    void when_Save_video_should_be_saved_successfully() {
        Video videoToSave = new Video("Nuevo Video", "Descripción", 6.0);
        doReturn(videoToSave).when(videoRepository).save(videoToSave);
        Video savedVideo = cultureMediaService.save(videoToSave);
        assertEquals(videoToSave, savedVideo);
    }

    @Test
    void when_Save_View_should_be_updated_successfully() {
        View viewToSave = new View("Juanito Perez", LocalDateTime.now(), 25, video1);
        doReturn(viewToSave).when(viewsRepository).save(viewToSave);
        View savedView = cultureMediaService.save(viewToSave);
        assertEquals(viewToSave, savedView);
    }
}


