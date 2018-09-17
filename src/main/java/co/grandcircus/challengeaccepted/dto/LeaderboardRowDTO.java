package co.grandcircus.challengeaccepted.dto;

public class LeaderboardRowDTO {
	
	Long userId;
	Integer rank;
	String firstName;
	String lastName;
	Integer completed;
	Integer declined;
	Integer failed;
	Integer completionRate;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Integer getCompleted() {
		return completed;
	}
	public void setCompleted(Integer completed) {
		this.completed = completed;
	}
	public Integer getDeclined() {
		return declined;
	}
	public void setDeclined(Integer declined) {
		this.declined = declined;
	}
	public Integer getFailed() {
		return failed;
	}
	public void setFailed(Integer failed) {
		this.failed = failed;
	}
	public Integer getCompletionRate() {
		return completionRate;
	}
	public void setCompletionRate(Integer completionRate) {
		this.completionRate = completionRate;
	}
	
	

}
