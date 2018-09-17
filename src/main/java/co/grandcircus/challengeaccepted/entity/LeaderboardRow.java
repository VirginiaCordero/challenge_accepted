package co.grandcircus.challengeaccepted.entity;

public interface LeaderboardRow {
	
	Integer getUserId();
	String getFirstName();
	String getLastName();
	Integer getCompleted();
	Integer getDeclined();
	Integer getFailed();
	Integer getScore();

}
