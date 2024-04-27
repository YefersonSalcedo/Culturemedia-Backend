package culturemedia.controllers;

import java.util.List;

import culturemedia.exception.VideoNotFoundException;
import culturemedia.model.Video;
import culturemedia.service.Impl.CultureMediaServiceImpl;

public class CultureMediaController {

	private final CultureMediaService cultureMediaService;


	public CultureMediaController(CultureMediaServiceImpl cultureMediaService) {
		this.cultureMediaService = cultureMediaService;
	}


	public List<Video> find_allVideos() throws VideoNotFoundException {
		List<Video> videos = null;
		videos = cultureMediaService.findAll();
		return videos;
	}


}
