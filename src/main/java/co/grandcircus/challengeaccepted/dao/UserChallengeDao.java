package co.grandcircus.challengeaccepted.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import co.grandcircus.challengeaccepted.entity.UserChallenge;

public interface UserChallengeDao extends JpaRepository<UserChallenge, Long> {

	UserChallenge findByUserIdEqualsAndStatusIs(Long userId, String status);
	
	UserChallenge findByUserIdEqualsAndChallengeIdEquals(Long userId, Long challengeId);
	
	// Number of User's Challenge Interactions (TOTAL)
	Integer countByUserIdEquals(Long userId);
	
	// Count total number of challenges where status is not something 
	Integer countByUserIdEqualsAndStatusNot(Long userId, String status);
	
	// Count total where status is something
	Integer countByUserIdEqualsAndStatusIs(Long userId, String status);
	
}
