package co.grandcircus.challengeaccepted.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import co.grandcircus.challengeaccepted.entity.Group;

public interface GroupDao extends JpaRepository<Group, Long> {
}
