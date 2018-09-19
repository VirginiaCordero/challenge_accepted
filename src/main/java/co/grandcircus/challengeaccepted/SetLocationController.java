package co.grandcircus.challengeaccepted;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.grandcircus.challengeaccepted.dao.UserDao;
import co.grandcircus.challengeaccepted.entity.User;
import co.grandcircus.challengeaccepted.model.googleplaces.GeocodeResult;

@Controller
public class SetLocationController {
	
	@Autowired
	private UserDao userDao;
	
	@Value("${google.api_key}")
	private String apiKey;
	
	@RequestMapping("/set-location")
	public ModelAndView showSetLocation(@SessionAttribute(name = "user", required = false) User user,
			HttpSession session, RedirectAttributes redir) {
		// For this URL, make sure there is a user on the session.
				System.out.println("Dashboard " + user);
				if (user == null) {
					// if not, send them back to the login page with a message.
					redir.addFlashAttribute("message", "Wait! Please log in.");
					return new ModelAndView("redirect:/login");
				}	
		ModelAndView mav = new ModelAndView("set-location");
		return mav;
	}
	
	@PostMapping("/set-location")
	public ModelAndView saveLocation(String zipcode,
									 @SessionAttribute(name = "user", required = false) User user, 
									 HttpSession session, 
									 RedirectAttributes redir) {
		// For this URL, make sure there is a user on the session.
		System.out.println("Dashboard " + user);
		if (user == null) {
			// if not, send them back to the login page with a message.
			redir.addFlashAttribute("message", "Wait! Please log in.");
			return new ModelAndView("redirect:/login");
		}	
		String location = null;
		
		RestTemplate restTemplate = new RestTemplate();

		String url = "https://maps.googleapis.com/maps/api/geocode/json?" +
					 "address=" + zipcode + 
					 "&key=" + apiKey; 
				
		GeocodeResult geocodeResult = restTemplate.getForObject(url, GeocodeResult.class);
		
		location = geocodeResult.getResults().get(0).getGeometry().getLocation().getLat() + 
				   "," +
				   geocodeResult.getResults().get(0).getGeometry().getLocation().getLng();
		
		
		User dbUser = userDao.findById(user.getId()).orElse(null);
		dbUser.setLocation(location);
		userDao.save(dbUser);		
		session.setAttribute("user", dbUser);	
		redir.addFlashAttribute("message", "Your location was saved!");			
		return new ModelAndView("redirect:/dashboard");
	}

}
