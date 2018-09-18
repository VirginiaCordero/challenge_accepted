package co.grandcircus.challengeaccepted;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.grandcircus.challengeaccepted.dao.UserDao;
import co.grandcircus.challengeaccepted.entity.User;

@Controller
public class SetLocationController {
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping("/set-location")
	public ModelAndView showSetLocation() {
		ModelAndView mav = new ModelAndView("set-location");
		return mav;
	}
	
	@PostMapping("/set-location")
	public ModelAndView saveLocation(String location, @SessionAttribute(name = "user", required = false) User user, HttpSession session, RedirectAttributes redir) {
		User dbUser = userDao.findById(user.getId()).orElse(null);
		dbUser.setLocation(location);
		userDao.save(dbUser);		
		session.setAttribute("user", dbUser);	
		redir.addFlashAttribute("message", "Your location was saved!");			
		return new ModelAndView("redirect:/dashboard");
	}

}
