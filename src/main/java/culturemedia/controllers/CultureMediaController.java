package culturemedia.controllers;

import java.util.List;

import culturemedia.exception.VideoNotFoundException;
import culturemedia.model.Video;
import culturemedia.repository.Impl.VideoRepositoryImpl;
import culturemedia.repository.Impl.ViewsRepositoryImpl;
import culturemedia.service.CultureMediaService;
import culturemedia.service.Impl.CultureMediaServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CultureMediaController {

	private final CultureMediaService cultureMediaService;
	private static final Logger logger = LoggerFactory.getLogger(CultureMediaController.class);

	public CultureMediaController() {
		this.cultureMediaService = new CultureMediaServiceImpl(new VideoRepositoryImpl(), new ViewsRepositoryImpl());
	}

	@GetMapping("/videos")
	public ResponseEntity<List<Video>> findAllVideos() {

		try{
			logger.info("Finding all videos");
			return ResponseEntity.ok().body(cultureMediaService.findAll());
		}
		catch (VideoNotFoundException e) {
			logger.error("Error finding all videos: {}", e.getMessage());
			return ResponseEntity.ok().header("Error-Message", e.getMessage()).build();
		}
	}

	@GetMapping("/videos/")
	public ResponseEntity<List<Video>> findVideosByTitle(@RequestParam("title") String title) {
		try {
			List<Video> video = cultureMediaService.find(title);
			logger.info("Finding videos by title: '{}'. Found videos: {}", title, video);
			return ResponseEntity.ok().body(video);
		} catch (VideoNotFoundException e) {
			logger.error("Error finding videos by title {}: {}", title, e.getMessage());
			return ResponseEntity.ok().header("Error-Message", e.getMessage()).build();
		}
	}

	@GetMapping("/videos/duration")
	public ResponseEntity<List<Video>> findVideosByDuration(@RequestParam("from") Double fromDuration,
															@RequestParam("to") Double toDuration) {
		try {
			logger.info("Finding videos by duration range: {} - {}", fromDuration, toDuration);
			return ResponseEntity.ok().body(cultureMediaService.find(fromDuration, toDuration));
		} catch (VideoNotFoundException e) {
			logger.error("Error finding videos by duration range {} - {}: {}", fromDuration, toDuration, e.getMessage());
			return ResponseEntity.ok().header("Error-Message", e.getMessage()).build();
		}
	}

	@PostMapping("/videos")
	public Video addVideo(@RequestBody @Valid Video video) {
		logger.info("Adding a new video: {}", video);
		return cultureMediaService.save(video);
	}

	@PostMapping("/videos/bulk")
	public List<Video> addVideo(@RequestBody @Valid List<Video> videos) {
		logger.info("Adding videos to bulk: {}", videos);
		return cultureMediaService.save(videos);
	}
}
