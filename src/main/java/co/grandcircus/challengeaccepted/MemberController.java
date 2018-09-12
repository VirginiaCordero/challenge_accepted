package co.grandcircus.challengeaccepted;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.grandcircus.challengeaccepted.dao.ChallengeDao;
import co.grandcircus.challengeaccepted.dao.GroupDao;
import co.grandcircus.challengeaccepted.dao.UserDao;
import co.grandcircus.challengeaccepted.entity.Challenge;
import co.grandcircus.challengeaccepted.entity.Group;
import co.grandcircus.challengeaccepted.entity.User;


@Controller
public class MemberController {
	
	@Autowired
	private GroupDao groupDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ChallengeDao challengeDao;
	
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
	
	
}
