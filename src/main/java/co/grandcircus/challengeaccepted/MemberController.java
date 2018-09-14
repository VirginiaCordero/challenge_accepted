package co.grandcircus.challengeaccepted;

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
	public ModelAndView showDashboard(@SessionAttribute(name = "user", required = false) User user,
			RedirectAttributes redir) {
		// For this URL, make sure there is a user on the session.
		System.out.println("Dashboard " + user);
		if (user == null) {
			// if not, send them back to the login page with a message.
			redir.addFlashAttribute("message", "Wait! Please log in.");
			return new ModelAndView("redirect:/login");
		}

		// if the user is logged in, proceed as normal.
		ModelAndView mav = new ModelAndView("dashboard");
		mav.addObject("groups", groupDao.findAll());

		PlaceDetailResult placeDetailResult = null;
		
		UserChallenge acceptedChallenge = userChallengeDao.findByUserIdEqualsAndStatusIs(user.getId(), "accepted");
		
		Challenge displayedChallenge = null;
		
//		boolean userHasNoGroups = user.getGroups() == null || user.getGroups().isEmpty();
//		boolean userHasGroups = !userHasNoGroups;
		
		boolean userHasGroups = user.getGroups() != null && !user.getGroups().isEmpty();
		
		if (userHasGroups) {
			// if user has an accepted challenge
			if (acceptedChallenge!=null) {
				displayedChallenge = acceptedChallenge.getChallenge();
			} else {
				// Get list of user's challenges and look for a "fresh" challenge
				List<Challenge> myChallenges = challengeDao.findByGroupInOrderByCreationDateAsc(user.getGroups());
				for (Challenge challenge : myChallenges) {
					if (userChallengeDao.findByUserIdEqualsAndChallengeIdEquals(user.getId(), challenge.getId())==null) {
						displayedChallenge = challenge;
						break;
					}
				}
			}
		}
		
		// TODO: handle what happens when there is no challenge to display!
		
		if (displayedChallenge != null) {

			RestTemplate restTemplate = new RestTemplate();

			String url = "https://maps.googleapis.com/maps/api/place/details/json?" + "placeid="
					+ displayedChallenge.getPlaceId() + "&key=" + apiKey;

			System.out.println(url);

			placeDetailResult = restTemplate.getForObject(url, PlaceDetailResult.class);

		}
		
		// Calculate user stats
		Integer total = userChallengeDao.countByUserIdEquals(user.getId());
		
		if (total!=0) {
			// num accepted/declined/completed/failed
			Integer declined = userChallengeDao.countByUserIdEqualsAndStatusIs(user.getId(), "declined");
			Integer accepted = total - declined;
			Integer completed = userChallengeDao.countByUserIdEqualsAndStatusIs(user.getId(), "completed");
			Integer failed = userChallengeDao.countByUserIdEqualsAndStatusIs(user.getId(), "failed");
			
			
			// calculate user's accept:decline ratio
			Double acceptDeclineRatio = (total - declined) / (total * 1.0); // hacky way to get a double
			
			if ((completed!=0 || failed!=0)) {
				// calculate user's complete:fail ratio
				Double completeFailRatio = completed / ((total - declined) * 1.0); // hacky way to get a double
				System.out.println(completed);
				System.out.println(total);
				System.out.println(failed);
				mav.addObject("completeFailRatio", completeFailRatio);
			}
			
			// calculate how many challenges the user has created
			Integer challengesCreated = challengeDao.countByUserIdEquals(user.getId());
			
			mav.addObject("total", total);
			mav.addObject("accepted", accepted);
			mav.addObject("declined", declined);
			mav.addObject("completed", completed);
			mav.addObject("failed", failed);
			mav.addObject("acceptDeclineRatio", acceptDeclineRatio);
			mav.addObject("created", challengesCreated);
		}
		
		mav.addObject("acceptedChallengeExists", acceptedChallenge);
		mav.addObject("nextChallenge", displayedChallenge);
		mav.addObject("nextChallengeDetails", placeDetailResult);

		return mav;
	}


	@PostMapping("/create-group")
	public ModelAndView createGroup(@SessionAttribute(name = "user", required = false) User user,
			RedirectAttributes redir, Group group) {
		
		// For this URL, make sure there is a user on the session.
		if (user == null) {
			// if not, send them back to the login page with a message.
			redir.addFlashAttribute("message", "Wait! Please log in.");
			return new ModelAndView("redirect:/login");
		}
		
		groupDao.save(group);
		
		redir.addFlashAttribute("You have created the group:" + group.getName() + "!");
		return new ModelAndView("redirect:/dashboard");
	
	}

	@PostMapping("/join-group")
	public ModelAndView joinGroupFromDashboard(@SessionAttribute(name = "user", required = false) User user,
			Group group, HttpSession session, RedirectAttributes redir) {
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


	@PostMapping("/leave-group")
	public ModelAndView leaveGroupFromDashboard(@SessionAttribute(name = "user", required = false) User user,
			Group group, HttpSession session, RedirectAttributes redir) {
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
	public ModelAndView acceptOrDeclineChallenge(@SessionAttribute(name = "user", required = false) User user,
			@RequestParam("challengeId") Challenge challenge, @RequestParam("response") String response) {

		UserChallenge userChallenge = null;
		
		// Check to see if they have previously accepted this challenge
		userChallenge = userChallengeDao.findByUserIdEqualsAndChallengeIdEquals(user.getId(), challenge.getId());
		
		// If it exists, update the row in the table with their outcome
		if (userChallenge!=null) {
			
			if (response.equalsIgnoreCase("completed")) {
				userChallenge.setStatus("completed");
			} else {
				userChallenge.setStatus("failed");	
			}
			
		// If not previously accepted, then set details and save a new row in the table
		} else {
			
			// Row in the user_challenge Table
			userChallenge = new UserChallenge();
	
			// Set all the values
			userChallenge.setChallenge(challenge); // sets challenge_id
			userChallenge.setUser(userDao.findById(user.getId()).orElse(null)); // sets user_id
			userChallenge.setResponseDate(System.currentTimeMillis()); // sets responseDate
	
			if (response.equalsIgnoreCase("accepted")) {
				userChallenge.setStatus("accepted"); // set status
			} else {
				userChallenge.setStatus("declined"); // set status
			}
		}

		// Regardless of how we got here, save the row
		userChallengeDao.save(userChallenge);

		return new ModelAndView("redirect:/dashboard");
	}

}
