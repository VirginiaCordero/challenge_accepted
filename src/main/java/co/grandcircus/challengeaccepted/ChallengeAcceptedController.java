package co.grandcircus.challengeaccepted;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import co.grandcircus.challengeaccepted.dao.UserDao;
import co.grandcircus.challengeaccepted.entity.User;

@Controller
public class ChallengeAcceptedController {

	@Autowired
	private UserDao userDao;

	@RequestMapping("/registration")
	public ModelAndView showFormPage() {
		ModelAndView mav = new ModelAndView("registration");
		return mav;
	}

	@PostMapping("/registration")
	public ModelAndView createUser(User user) {
		userDao.save(user);
		// TODO: add user to session, tricky
		return new ModelAndView("redirect:/dashboard");
	}

}
