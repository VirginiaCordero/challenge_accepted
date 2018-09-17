package co.grandcircus.challengeaccepted.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.grandcircus.challengeaccepted.entity.UserChallenge;
import co.grandcircus.challengeaccepted.projectioninterfaces.ChallengeStatus;
import co.grandcircus.challengeaccepted.projectioninterfaces.LeaderboardRow;

public interface UserChallengeDao extends JpaRepository<UserChallenge, Long> {

	UserChallenge findByUserIdEqualsAndStatusIs(Long userId, String status);

	UserChallenge findByUserIdEqualsAndChallengeIdEquals(Long userId, Long challengeId);
	
	// Number of User's Challenge Interactions (TOTAL)
	Integer countByUserIdEquals(Long userId);

	// Count total number of challenges where status is not something
	Integer countByUserIdEqualsAndStatusNot(Long userId, String status);

	// Count total where status is something
	Integer countByUserIdEqualsAndStatusIs(Long userId, String status);

	// Count number of challenges where status is something
	Integer countByChallengeIdAndStatusIs(Long challengeId, String status);
	
	// Get user statuses for a specific challenge
	@Query(nativeQuery = true, value =
			"SELECT" + 
			"	user_challenge.user_id AS userId," +
			"	`user`.first_name AS firstName," + 
			"	`user`.last_name AS lastName," + 
			"	 user_challenge.`status` AS status" +
			"		FROM user_challenge" + 
			"		JOIN `user` ON user_challenge.user_id=`user`.id" + 
			"		JOIN challenge ON user_challenge.challenge_id=challenge.id WHERE challenge.id = :challengeId" +
			" 		ORDER BY lastName ASC;")
	List<ChallengeStatus> getChallengeStatuses(@Param("challengeId") Long challengeId);
	
	// Get user rankings and statuses within a group
	@Query(nativeQuery = true, value =  
			"SELECT" + 
			"	 user_id AS userId," + 
			"    first_name AS firstName," + 
			"    last_name AS lastName," +  
			"    SUM(`status`='completed') AS completed," + 
			"    SUM(`status`= 'declined') AS declined," + 
			"    SUM(`status`='failed') AS failed," + 
			"    Floor((SUM(`status`='completed') / (SUM(`status`='declined') + SUM(`status`='failed')) * 100)) AS completionRate" + 
			"		FROM (" + 
			"			SELECT user_challenge.user_id, `user`.first_name, `user`.last_name, user_challenge.`status` FROM user_challenge" + 
			"				JOIN `user` ON user_challenge.user_id=`user`.id" + 
			"				JOIN challenge ON user_challenge.challenge_id=challenge.id WHERE challenge.group_id = :groupId" + 
			"				) AS groups_users_statuses" + 
			"	 GROUP BY user_id" + 
			"    ORDER BY completed DESC, completionRate DESC;")
	List<LeaderboardRow> getLeaderboard(@Param("groupId") Long groupId);

}
