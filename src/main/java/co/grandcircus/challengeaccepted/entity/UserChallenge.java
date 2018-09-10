package co.grandcircus.challengeaccepted.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserChallenge {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="challenge_id")
	private Challenge challenge;
	
	private LocalDate responseDate;
	private LocalDate outcomeDate;
	private String status;
	private Boolean like;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Challenge getChallenge() {
		return challenge;
	}
	public void setChallenge(Challenge challenge) {
		this.challenge = challenge;
	}
	public LocalDate getResponseDate() {
		return responseDate;
	}
	public void setResponseDate(LocalDate responseDate) {
		this.responseDate = responseDate;
	}
	public LocalDate getOutcomeDate() {
		return outcomeDate;
	}
	public void setOutcomeDate(LocalDate outcomeDate) {
		this.outcomeDate = outcomeDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Boolean getLike() {
		return like;
	}
	public void setLike(Boolean like) {
		this.like = like;
	}
	
}
