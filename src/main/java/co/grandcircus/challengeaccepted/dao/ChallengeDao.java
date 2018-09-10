package co.grandcircus.challengeaccepted.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import co.grandcircus.challengeaccepted.entity.Challenge;

public interface ChallengeDao extends JpaRepository<Challenge, Long> {

}
