package co.grandcircus.challengeaccepted.projectioninterfaces;

public interface LeaderboardRow {
	
	Long getUserId();
	String getFirstName();
	String getLastName();
	Integer getCompleted();
	Integer getDeclined();
	Integer getFailed();
	Integer getCompletionRate();

}
