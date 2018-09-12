package co.grandcircus.challengeaccepted.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import co.grandcircus.challengeaccepted.entity.Challenge;
import co.grandcircus.challengeaccepted.entity.Group;

public interface ChallengeDao extends JpaRepository<Challenge, Long> {

	Challenge findFirstByGroupInOrderByCreationDateAsc(Set<Group> groups);

}
