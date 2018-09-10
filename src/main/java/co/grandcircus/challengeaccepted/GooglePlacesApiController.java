package co.grandcircus.challengeaccepted;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import co.grandcircus.challengeaccepted.model.googleplaces.NearbySearchResults;

@Controller
public class GooglePlacesApiController {

	@RequestMapping("/nearby-search")
	public ModelAndView apiTest(@RequestParam(value="keyword", required=false) String keyword) {
		
		String location = "42.3359244,-83.0497189";
		Integer radius = 2000;

		RestTemplate restTemplate = new RestTemplate();
		
		if (keyword!=null & !keyword.isEmpty()) {
			String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + location +
						 "&radius=" + radius +
						 "&keyword=" + keyword +
						 "&key=AIzaSyAJa6OUqYjtxCUIA95QUqgstguQ32pXVUs";
		
		
			NearbySearchResults nearbySearchResults = restTemplate.getForObject(url, NearbySearchResults.class);
	
			return new ModelAndView("nearby-search", "nearbySearchResults", nearbySearchResults);		
		}
		
		return null;
		
	}

}
