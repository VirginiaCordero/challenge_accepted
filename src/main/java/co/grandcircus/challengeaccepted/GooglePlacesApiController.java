package co.grandcircus.challengeaccepted;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import co.grandcircus.challengeaccepted.dao.ChallengeDao;
import co.grandcircus.challengeaccepted.dao.GroupDao;
import co.grandcircus.challengeaccepted.entity.Challenge;
import co.grandcircus.challengeaccepted.entity.User;
import co.grandcircus.challengeaccepted.model.googleplaces.NearbySearchResults;
import co.grandcircus.challengeaccepted.model.googleplaces.PlaceDetailResult;

@Controller
public class GooglePlacesApiController {

	@Value("${google.api_key}")
	private String apiKey;

	@Autowired
	private ChallengeDao challengeDao;

	@Autowired
	private GroupDao groupDao;

	@RequestMapping("/nearby-search")
	public ModelAndView apiTest(@RequestParam(value = "keyword", required = false) String keyword,
								@RequestParam(value = "pageToken", required = false) String pageToken,
								@SessionAttribute(name = "user", required = false) User user) {

		//TODO: add login redirect if no user
		
		String location = user.getLocation();
		Integer radius = 2000;

		RestTemplate restTemplate = new RestTemplate();
		String url = null;
		
		// For pagination
		if (pageToken!=null && !pageToken.isEmpty()) {
			url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
						 "pagetoken=" + pageToken +
						 "&key=" + apiKey;
		} else {
			url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" + 
					 "location=" + location + 
					 "&radius=" + radius + 
					 "&keyword=" + keyword + 
					 "&key=" + apiKey;
		}

		NearbySearchResults nearbySearchResults = restTemplate.getForObject(url, NearbySearchResults.class);
		
		String embeddedMapUrl = "https://www.google.com/maps/embed/v1/search?" +
								"key=" + apiKey +
								"&q=" + keyword +
								"&center=" + location;

		ModelAndView mav = new ModelAndView("nearby-search");
		mav.addObject("nearbySearchResults", nearbySearchResults);
		mav.addObject("embeddedMapUrl", embeddedMapUrl );
		return mav;

	}

	@RequestMapping("/create-challenge")
	public ModelAndView showChallengeForm(@RequestParam(value = "placeId", required = false) String placeId) {

		RestTemplate restTemplate = new RestTemplate();

		String url = "https://maps.googleapis.com/maps/api/place/details/json?" + "placeid=" + placeId + "&key="
				+ apiKey;

		PlaceDetailResult placeDetailResult = restTemplate.getForObject(url, PlaceDetailResult.class);

		ModelAndView mav = new ModelAndView("create-challenge");
		mav.addObject("placeDetailResult", placeDetailResult);
		mav.addObject("groups", groupDao.findAllByOrderByName());
		return mav;
	}

	@PostMapping("/create-challenge")
	public ModelAndView addChallenge(@SessionAttribute("user") User user, Challenge challenge) {

		challenge.setUser(user);
		challenge.setCreationDate(System.currentTimeMillis());
		challengeDao.save(challenge);

		ModelAndView mav = new ModelAndView("redirect:/dashboard");
		return mav;
	}

}
