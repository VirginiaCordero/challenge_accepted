package co.grandcircus.challengeaccepted;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public ModelAndView createUser(User user, HttpSession session, RedirectAttributes redir) {
		userDao.save(user);
		session.setAttribute("user", user);
		//should this be here??
		redir.addFlashAttribute("message", "Logged in.");
		return new ModelAndView("redirect:/set-location");
	}
	
	
	
}
