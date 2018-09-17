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
import co.grandcircus.challengeaccepted.entity.User;
import co.grandcircus.challengeaccepted.projectioninterfaces.LeaderboardRow;

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
	public void testQuery(@RequestParam("groupId") Long groupId) {
		List<LeaderboardRow> leaderboard = userChallengeDao.getLeaderboard(groupId);
		
		int rowNumber = 1;
		int dispRank = 1;
		Integer prevNumComp = null;
		Integer prevScore = null;
		
		for (LeaderboardRow row : leaderboard) {
			
			if (row.getCompleted()==prevNumComp && row.getCompletionRate()==prevScore) {
				System.out.println(dispRank + ". " + row.getFirstName());
				
			} else {
				dispRank = rowNumber;
				System.out.println(dispRank + ". " + row.getFirstName());
				
			}
			
			prevNumComp = row.getCompleted();
			prevScore = row.getCompletionRate();
			rowNumber++;
			
		}
		
		rowNumber = 1;
		dispRank = 1;
		prevNumComp = null;
		prevScore = null;
		
		for (LeaderboardRow row : leaderboard) {
				
			if (row.getCompleted()==prevNumComp && row.getCompletionRate()==prevScore) {
				
			} else {
				dispRank = rowNumber;
				
			}
			
			prevNumComp = row.getCompleted();
			prevScore = row.getCompletionRate();
			rowNumber++;
			
			if (row.getUserId() == 4) {
				System.out.println(dispRank + ". " + row.getFirstName());
			}	
		}
	}
}
