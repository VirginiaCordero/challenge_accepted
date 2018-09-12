package co.grandcircus.challengeaccepted;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.grandcircus.challengeaccepted.dao.ChallengeDao;
import co.grandcircus.challengeaccepted.dao.GroupDao;
import co.grandcircus.challengeaccepted.dao.UserChallengeDao;
import co.grandcircus.challengeaccepted.dao.UserDao;
import co.grandcircus.challengeaccepted.entity.Challenge;
import co.grandcircus.challengeaccepted.entity.Group;
import co.grandcircus.challengeaccepted.entity.User;
import co.grandcircus.challengeaccepted.entity.UserChallenge;
import co.grandcircus.challengeaccepted.model.googleplaces.PlaceDetailResult;


@Controller
public class MemberController {
	
	@Value("${google.api_key}")
	private String apiKey;
	
	@Autowired
	private GroupDao groupDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ChallengeDao challengeDao;
	
	@Autowired
	private UserChallengeDao userChallengeDao;
	
	@RequestMapping("/dashboard")
	public ModelAndView showDashboard(@SessionAttribute(name="user", required=false) User user, RedirectAttributes redir) {
		// For this URL, make sure there is a user on the session.
		if (user == null) {
			// if not, send them back to the login page with a message.
			redir.addFlashAttribute("message", "Wait! Please log in.");
			return new ModelAndView("redirect:/login");
		}
		
		// if the user is logged in, proceed as normal.
		ModelAndView mav = new ModelAndView("dashboard");
		mav.addObject("groups", groupDao.findAll());
		
		// Iterate through the user's groups, and get all challenges for each group
		List<Challenge> challenges = new ArrayList<Challenge>();		
		for (Group group : user.getGroups()) {
			for (Challenge challenge : group.getChallenges()) {
				challenges.add(challenge);
			}
		}
		
		Challenge nextChallenge = challengeDao.findFirstByGroupInOrderByCreationDateAsc(user.getGroups());
		
		RestTemplate restTemplate = new RestTemplate();
		
		String url = "https://maps.googleapis.com/maps/api/place/details/json?" +
					 "placeid=" + nextChallenge.getPlaceId() +
					 "&key=" + apiKey;
		
		System.out.println(url);
		
		PlaceDetailResult placeDetailResult = restTemplate.getForObject(url, PlaceDetailResult.class);
		
		mav.addObject("nextChallengeDetails", placeDetailResult);
		mav.addObject("nextChallenge", nextChallenge);
		mav.addObject("challenges", challenges);
	
		return mav;
	}
	
	@PostMapping("/dashboard/join-group")
	public ModelAndView joinGroupFromDashboard(@SessionAttribute(name="user", required=false) User user,
											   Group group,
											   HttpSession session,
											   RedirectAttributes redir) {
		// For this URL, make sure there is a user on the session.
		if (user == null) {
			// if not, send them back to the login page with a message.
			redir.addFlashAttribute("message", "Wait! Please log in.");
			return new ModelAndView("redirect:/login");
		}
		
		User dbUser = userDao.findById(user.getId()).orElse(null);
		dbUser.getGroups().add(group);
		userDao.save(dbUser);
		session.setAttribute("user", dbUser);
		
		redir.addFlashAttribute("You have been added to " + group.getName() + "!");
		return new ModelAndView("redirect:/dashboard");
	}
	
	@PostMapping("/dashboard/leave-group")
	public ModelAndView leaveGroupFromDashboard(@SessionAttribute(name="user", required=false) User user,
											   Group group,
											   HttpSession session,
											   RedirectAttributes redir) {
		// For this URL, make sure there is a user on the session.
		if (user == null) {
			// if not, send them back to the login page with a message.
			redir.addFlashAttribute("message", "Wait! Please log in.");
			return new ModelAndView("redirect:/login");
		}
		
		User dbUser = userDao.findById(user.getId()).orElse(null); // new managed entity "circle"
		dbUser.getGroups().remove(group);
		userDao.save(dbUser);
		session.setAttribute("user", dbUser);
		
		return new ModelAndView("redirect:/dashboard");
	}
	
	@RequestMapping("/challenge-response")
	public ModelAndView acceptOrDeclineChallenge(@SessionAttribute(name="user", required=false) User user,
												 @RequestParam("challengeId") Challenge challenge,
												 @RequestParam("response") String response) {
		
		// Row in the user_challenge Table
		UserChallenge userChallenge = new UserChallenge();
		
		// Set all the values
		userChallenge.setChallenge(challenge); // sets challenge_id
		userChallenge.setUser(userDao.findById(user.getId()).orElse(null)); // sets user_id
		userChallenge.setResponseDate(System.currentTimeMillis()); // sets responseDate
		
		if (response.equalsIgnoreCase("accepted")) {
			userChallenge.setStatus("accepted"); // set status
		} else {
			userChallenge.setStatus("declined"); // set status
		}
		
		// Save the Row
		userChallengeDao.save(userChallenge);
			
		
		return new ModelAndView("redirect:/dashboard");
	}
	
	
}
