package co.grandcircus.challengeaccepted.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import co.grandcircus.challengeaccepted.entity.UserChallenge;

public interface UserChallengeDao extends JpaRepository<UserChallenge, Long> {

}
