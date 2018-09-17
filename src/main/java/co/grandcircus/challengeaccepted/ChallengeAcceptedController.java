package co.grandcircus.challengeaccepted;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.grandcircus.challengeaccepted.dao.UserChallengeDao;
import co.grandcircus.challengeaccepted.dao.UserDao;
import co.grandcircus.challengeaccepted.entity.LeaderboardRow;
import co.grandcircus.challengeaccepted.entity.User;

@Controller
public class ChallengeAcceptedController {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserChallengeDao userChallengeDao;


	@RequestMapping("/registration")
	public ModelAndView showFormPage() {
		ModelAndView mav = new ModelAndView("registration");
		return mav;
	}

	@PostMapping("/registration")
	public ModelAndView createUser(User user, HttpSession session, RedirectAttributes redir) {
		userDao.save(user);
		session.setAttribute("user", user);
		//should this be here??
		redir.addFlashAttribute("message", "Logged in.");
		return new ModelAndView("redirect:/dashboard");

	}
	
	@RequestMapping("/test")
	public void testQuery(@RequestParam("groupId") Integer groupId) {
		List<LeaderboardRow> leaderboard = userChallengeDao.getLeaderboard(groupId);
		
		for (LeaderboardRow row : leaderboard) {
			System.out.println(row.getUserId());
			System.out.println(row.getFirstName());
			System.out.println(row.getScore());
		}
		
	}
}
