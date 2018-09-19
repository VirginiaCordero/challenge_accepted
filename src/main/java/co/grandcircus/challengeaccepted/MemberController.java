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
import co.grandcircus.challengeaccepted.dto.LeaderboardRowDTO;
import co.grandcircus.challengeaccepted.dto.UserGroupInfoDTO;
import co.grandcircus.challengeaccepted.entity.Challenge;
import co.grandcircus.challengeaccepted.entity.Group;
import co.grandcircus.challengeaccepted.entity.User;
import co.grandcircus.challengeaccepted.entity.UserChallenge;
import co.grandcircus.challengeaccepted.model.googleplaces.PlaceDetailResult;
import co.grandcircus.challengeaccepted.projectioninterfaces.ChallengeStatus;
import co.grandcircus.challengeaccepted.projectioninterfaces.LeaderboardRow;

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

		// Get the user's currently-accepted challenge, if any! This might be null
		UserChallenge acceptedChallenge = userChallengeDao.findByUserIdEqualsAndStatusIs(user.getId(), "accepted");

		// Declare variables for the challenge to be displayed, and the results of a
		// google place API call for
		// that challenge's location.
		Challenge displayedChallenge = null;
		PlaceDetailResult placeDetailResult = null;

		// Initialize a boolean for whether or not the user belongs to any groups.
		boolean userHasGroups = user.getGroups() != null && !user.getGroups().isEmpty();
		
		// If the user has groups, look for the correct challenge to display
		if (userHasGroups) {
			// if user has an accepted challenge
			if (acceptedChallenge != null) {
				displayedChallenge = acceptedChallenge.getChallenge();
			} else {
				// Get list of user's challenges and look for a "fresh" challenge
				List<Challenge> myChallenges = challengeDao.findByGroupInOrderByCreationDateAsc(user.getGroups());
				for (Challenge challenge : myChallenges) {
					if (userChallengeDao.findByUserIdEqualsAndChallengeIdEquals(user.getId(),
							challenge.getId()) == null) {
						displayedChallenge = challenge;
						break;
					}
				}
			}
		}

		// If the user has groups, calculate the user's rank in each group and store
		// details about each of those groups
		// to display in the JSP
		if (userHasGroups) {
			List<UserGroupInfoDTO> usersGroupsInfo = new ArrayList<UserGroupInfoDTO>();

			for (Group group : user.getGroups()) {
				// Make a DTO to hold this information
				UserGroupInfoDTO userGroupInfoDTO = new UserGroupInfoDTO();

				// Set all the relevant fields
				userGroupInfoDTO.setId(group.getId());
				userGroupInfoDTO.setName(group.getName());
				userGroupInfoDTO.setDescription(group.getDescription());
				userGroupInfoDTO.setUserRank(getUserRankInGroup(user.getId(), group.getId()));

				if (group.getUsers() == null) {
					// This group was just created and for some reason
					// the database hasn't updated
					userGroupInfoDTO.setNumMembers(1);
				} else {
					// This group is already in the database
					userGroupInfoDTO.setNumMembers(group.getUsers().size());
				}

				// Add the DTO to the list
				usersGroupsInfo.add(userGroupInfoDTO);

			}
			
			Integer displayedChallengeGroupRank = 0;
			for (UserGroupInfoDTO group : usersGroupsInfo) {
				if (displayedChallenge!=null) {
					if (displayedChallenge.getGroup().getName().equalsIgnoreCase(group.getName())) {
						displayedChallengeGroupRank = group.getUserRank();
					}
				}
			}

			// Add all information about all the user's groups to the ModelAndView
			mav.addObject("usersGroupsInfo", usersGroupsInfo);
			mav.addObject("displayedChallengeGroupRank", displayedChallengeGroupRank);

		}


		if (displayedChallenge != null) {

			RestTemplate restTemplate = new RestTemplate();

			String url = "https://maps.googleapis.com/maps/api/place/details/json?" + "placeid="
					+ displayedChallenge.getPlaceId() + "&key=" + apiKey;

			System.out.println(url);

			placeDetailResult = restTemplate.getForObject(url, PlaceDetailResult.class);

		}

		// All Calculate user stats
		Integer total = userChallengeDao.countByUserIdEquals(user.getId());

		if (total != 0) {
			// num accepted/declined/completed/failed
			Integer declined = userChallengeDao.countByUserIdEqualsAndStatusIs(user.getId(), "declined");
			Integer accepted = total - declined;
			Integer completed = userChallengeDao.countByUserIdEqualsAndStatusIs(user.getId(), "completed");
			Integer failed = userChallengeDao.countByUserIdEqualsAndStatusIs(user.getId(), "failed");

			// calculate user's accept:decline ratio
			Double acceptDeclineRatio = (total - declined) / (total * 1.0); // hacky way to get a double

			if ((completed != 0 || failed != 0)) {
				// calculate user's complete:fail ratio
				Double completeFailRatio = completed / ((total - declined) * 1.0); // hacky way to get a double
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

		// All of this is for seeing stats about a specific challenge
		if (displayedChallenge != null) {

			Integer displayedChallengeNumAccepts = userChallengeDao
					.countByChallengeIdAndStatusIs(displayedChallenge.getId(), "accepted");
			Integer displayedChallengeNumDeclines = userChallengeDao
					.countByChallengeIdAndStatusIs(displayedChallenge.getId(), "declined");
			Integer displayedChallengeNumCompleted = userChallengeDao
					.countByChallengeIdAndStatusIs(displayedChallenge.getId(), "completed");
			Integer displayedChallengeNumFailed = userChallengeDao
					.countByChallengeIdAndStatusIs(displayedChallenge.getId(), "failed");
						
			Integer displayedChallengeDifficulty = (
					(1 - (displayedChallengeNumCompleted / (displayedChallenge.getGroup().getUsers().size()))) * 100);

			// Gets the user statuses for the displayed challenge
			List<ChallengeStatus> challengeStatuses = userChallengeDao.getChallengeStatuses(displayedChallenge.getId());

			List<String> accepted = new ArrayList<String>();
			List<String> declined = new ArrayList<String>();
			List<String> completed = new ArrayList<String>();
			List<String> failed = new ArrayList<String>();

			for (ChallengeStatus challengeStatus : challengeStatuses) {

				if ("accepted".equalsIgnoreCase(challengeStatus.getStatus())) {
					accepted.add(challengeStatus.getFirstName() + " " + challengeStatus.getLastName());
				} else if ("declined".equalsIgnoreCase(challengeStatus.getStatus())) {
					declined.add(challengeStatus.getFirstName() + " " + challengeStatus.getLastName());
				} else if ("completed".equalsIgnoreCase(challengeStatus.getStatus())) {
					completed.add(challengeStatus.getFirstName() + " " + challengeStatus.getLastName());
				} else {
					failed.add(challengeStatus.getFirstName() + " " + challengeStatus.getLastName());
				}

			}

			mav.addObject("displayedChallengeNumAccepts", displayedChallengeNumAccepts);
			mav.addObject("displayedChallengeNumDeclines", displayedChallengeNumDeclines);
			mav.addObject("displayedChallengeNumCompleted", displayedChallengeNumCompleted);
			mav.addObject("displayedChallengeNumFailed", displayedChallengeNumFailed);
			mav.addObject("displayedChallengeDifficulty", displayedChallengeDifficulty);
			mav.addObject("displayedChallengeAcceptList", accepted);
			mav.addObject("displayedChallengeDeclineList", declined);
			mav.addObject("displayedChallengeCompleteList", completed);
			mav.addObject("displayedChallengeFailList", failed);

		}
		mav.addObject("apiKey", apiKey);
		return mav;

	}

	@PostMapping("/create-group")
	public ModelAndView createGroup(@SessionAttribute(name = "user", required = false) User user,
			RedirectAttributes redir, Group group, HttpSession session) {

		// For this URL, make sure there is a user on the session.
		if (user == null) {
			// if not, send them back to the login page with a message.
			redir.addFlashAttribute("message", "Wait! Please log in.");
			return new ModelAndView("redirect:/login");
		}

		groupDao.save(group);

		User dbUser = userDao.findById(user.getId()).orElse(null);
		dbUser.getGroups().add(group);

		userDao.save(dbUser);
		session.setAttribute("user", dbUser);

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
		if (userChallenge != null) {

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

	@RequestMapping("group-leaderboard")
	public ModelAndView showGroupLeaderboard(@RequestParam("groupId") Long groupId) {
		return new ModelAndView("leaderboard", "leaderboard", getGroupLeaderboard(groupId));

	}

	// Method for getting a "leaderboard" for a specific group
	private List<LeaderboardRowDTO> getGroupLeaderboard(Long groupId) {
		List<LeaderboardRow> getLeaderboardQueryResult = userChallengeDao.getLeaderboard(groupId);

		int rowNumber = 1;
		int dispRank = 1;
		Integer prevRowNumComp = null;
		Integer prevRowCompletionRate = null;
		List<LeaderboardRowDTO> leaderboard = new ArrayList<LeaderboardRowDTO>();

		for (LeaderboardRow row : getLeaderboardQueryResult) {

			// If the current row is different from the last row, update the disp rank
			// to the current row number (otherwise, there is a tie and the disp rank should
			// not yet be updated).
			if (row.getCompleted() != prevRowNumComp || row.getCompletionRate() != prevRowCompletionRate) {
				dispRank = rowNumber;
			}

			// Assign current row's values and current dispRank to a DTO
			LeaderboardRowDTO rowDTO = new LeaderboardRowDTO();
			rowDTO.setUserId(row.getUserId());
			rowDTO.setRank(dispRank);
			rowDTO.setFirstName(row.getFirstName());
			rowDTO.setLastName(row.getLastName());
			rowDTO.setCompleted(row.getCompleted());
			rowDTO.setDeclined(row.getDeclined());
			rowDTO.setFailed(row.getFailed());

			
			// If the User has completed nothing
			if (row.getCompleted()==0) {
				// Set completion rate to 0.
				rowDTO.setCompletionRate(0);
			// User has never failed/declined ... but SQL won't divide by 0 so
			// this the completion rate is null.
			} else if (row.getCompletionRate() == null) {
				// Set completion rate to 100
				rowDTO.setCompletionRate(100);
			} else {
				rowDTO.setCompletionRate(row.getCompletionRate());
			}

			// Add DTO to the leaderboard
			leaderboard.add(rowDTO);

			// Assign "scored" values for comparison in the next iteration of this loop
			// and increment the row number to reassign dispRank after any ties.
			prevRowNumComp = row.getCompleted();
			prevRowCompletionRate = row.getCompletionRate();
			rowNumber++;

		}

		return leaderboard;

	}

	private Integer getUserRankInGroup(Long userId, Long groupId) {
		List<LeaderboardRowDTO> groupLeaderboard = getGroupLeaderboard(groupId);

		Integer userRank = null;

		for (LeaderboardRowDTO row : groupLeaderboard) {
			if (userId == row.getUserId()) {
				userRank = row.getRank();
				break;
			}
		}

		return userRank;
	}

}
