package culturemedia.service;

import culturemedia.exception.VideoNotFoundException;
import culturemedia.model.Video;
import culturemedia.model.View;
import culturemedia.repository.VideoRepository;
import culturemedia.repository.ViewsRepository;
import culturemedia.service.Impl.CultureMediaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


class CultureMediaServiceImplTest {

    private CultureMediaService cultureMediaService;
    VideoRepository videoRepository = Mockito.mock();
    ViewsRepository viewsRepository = Mockito.mock();

    private Video video1 = new Video("01", "Título 1", "----", 4.5);
    private Video video2 = new Video("02", "Título 2", "----", 5.5);
    private Video video3 = new Video("03", "Título 3", "----", 4.4);
    private Video video4 = new Video("04", "Título 4", "----", 3.5);
    private Video video5 = new Video("05", "Clic 5", "----", 5.7);
    private Video video6 = new Video("06", "Clic 6", "----", 5.1);


    @BeforeEach
    void init() {
        cultureMediaService = new CultureMediaServiceImpl(videoRepository, viewsRepository);
    }

    private void mockVideoRepositoryFindAll(List <Video> videos){
        doReturn(videos).when(videoRepository).findAll();
    }

    private void mockVideoRepositoryFind(String title, List<Video> videos){
        doReturn(videos).when(videoRepository).find(eq(title));
    }

    private void mockVideoRepositoryFind(Double fromDuration, Double toDuration, List<Video> videos){
        doReturn(videos).when(videoRepository).find(eq(fromDuration), eq(toDuration));
    }



    @Test
    void when_FindAll_all_videos_should_be_returned_successfully() throws VideoNotFoundException {
        mockVideoRepositoryFindAll(List.of(
                video1,
                video2,
                video3,
                video4,
                video5,
                video6)
        );
        List<Video> videos = cultureMediaService.findAll();
        assertEquals(6, videos.size());
    }


    @Test
    void when_FindAll_does_not_find_any_video_an_VideoNotFoundException_should_be_thrown_successfully() {
        // Asegura que se lance VideoNotFoundException cuando se llame a findAll() en un repositorio vacío
        mockVideoRepositoryFindAll(Collections.emptyList());
        assertThrows(VideoNotFoundException.class, () -> cultureMediaService.findAll());
    }

    @Test
    void when_Find_by_title_should_return_matching_video() throws VideoNotFoundException {

        mockVideoRepositoryFind("Titulo 2", List.of(video2));
        List<Video> videos= cultureMediaService.find("Titulo 2");
        assertEquals(1, videos.size());
        assertEquals(video2, videos.get(0));
    }

    @Test
    void when_Find_by_title_with_nonexistent_title_should_throw_VideoNotFoundException() {
        mockVideoRepositoryFind(null,Collections.emptyList());
        assertThrows(VideoNotFoundException.class, () -> cultureMediaService.find("Título -1"));
    }

    @Test
    void when_Find_by_duration_range_should_return_videos_within_range() throws VideoNotFoundException {

        mockVideoRepositoryFind(3.5,5.5,List.of(
                video1,
                video2,
                video3,
                video4,
                video5));
        List<Video> videos = cultureMediaService.find(3.5, 5.5);
        assertEquals(5, videos.size());
    }

    @Test
    void when_Find_by_duration_range_with_invalid_range_should_throw_VideoNotFoundException() {
        mockVideoRepositoryFind(null,null,Collections.emptyList());
        assertThrows(VideoNotFoundException.class, () -> cultureMediaService.find(6.0, 2.0));
    }

    @Test
    void when_Save_video_should_be_saved_successfully() { // Asegura que un Video se guarde correctamente
        Video videoToSave = new Video("07", "Nuevo Video", "Descripción", 6.0);
        when(videoRepository.save(any(Video.class))).thenReturn(videoToSave);
        Video savedVideo = cultureMediaService.save(videoToSave);

        verify(videoRepository).save(videoToSave);
        assertEquals(videoToSave, savedVideo);
    }

    @Test
    void when_Save_View_should_be_updated_successfully() { // Asegura que una View se guarde correctamente
        View viewToSave = new View("Juanito Perez", LocalDateTime.now(), 25, video1);
        when(viewsRepository.save(any(View.class))).thenReturn(viewToSave);
        View savedView = cultureMediaService.save(viewToSave);

        verify(viewsRepository).save(viewToSave);
        assertEquals(viewToSave, savedView);
    }

}

