package co.grandcircus.challengeaccepted;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import co.grandcircus.challengeaccepted.model.googleplaces.NearbySearchResults;

@Controller
public class GooglePlacesApiController {

	@RequestMapping("/test")
	public ModelAndView apiTest() {
		Double lat = 42.3359244;
		Double lng = -83.0497189;
		String keyword = "taco";

		RestTemplate restTemplate = new RestTemplate();
		String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=42.3359244,-83.0497189&radius=1500&type=restaurant&keyword=taco&key=AIzaSyAJa6OUqYjtxCUIA95QUqgstguQ32pXVUs";
		NearbySearchResults nearbySearchResults = restTemplate.getForObject(url, NearbySearchResults.class);

		return new ModelAndView("test", "nearbySearchResults", nearbySearchResults);
	}

}
