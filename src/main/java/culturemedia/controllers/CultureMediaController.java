package culturemedia.controllers;

import java.util.List;

import culturemedia.exception.VideoNotFoundException;
import culturemedia.model.Video;
import culturemedia.service.CultureMediaService;
import culturemedia.service.Impl.CultureMediaServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class CultureMediaController {

	@Autowired
	private CultureMediaService cultureMediaService;

	private static final Logger logger = LoggerFactory.getLogger(CultureMediaController.class);

	@GetMapping("/videos")
	public ResponseEntity<List<Video>> findAllVideos() throws VideoNotFoundException {
		logger.info("Finding all videos");
		return ResponseEntity.ok().body(cultureMediaService.findAll());
	}

	@GetMapping("/videos/")
	public ResponseEntity<List<Video>> findVideosByTitle(@RequestParam("title") String title) throws VideoNotFoundException {
		List<Video> videos = cultureMediaService.findByTitle(title);
		logger.info("Finding videos by title: '{}'. Found videos: {}", title, videos);
		return ResponseEntity.ok().body(videos);
	}

	@GetMapping("/videos/duration")
	public ResponseEntity<List<Video>> findVideosByDuration(@RequestParam("from") Double fromDuration,
															@RequestParam("to") Double toDuration) throws VideoNotFoundException {
		logger.info("Finding videos by duration range: {} - {}", fromDuration, toDuration);
		return ResponseEntity.ok().body(cultureMediaService.findByDuration(fromDuration, toDuration));
	}

	@PostMapping("/videos")
	public ResponseEntity<Video> addVideo(@RequestBody @Valid Video video) {
		logger.info("Adding a new video: {}", video);
		Video savedVideo = cultureMediaService.save(video);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedVideo);
	}

	@PostMapping("/videos/bulk")
	public ResponseEntity<List<Video>> addVideos(@RequestBody @Valid List<Video> videos) {
		logger.info("Adding videos in bulk: {}", videos);
		List<Video> savedVideos = cultureMediaService.save(videos);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedVideos);
	}
}
