package co.grandcircus.challengeaccepted.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import co.grandcircus.challengeaccepted.entity.User;

public interface UserDao extends JpaRepository<User, Long> {

}
