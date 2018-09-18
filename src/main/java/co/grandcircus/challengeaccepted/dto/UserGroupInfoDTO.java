package co.grandcircus.challengeaccepted.dto;

public class UserGroupInfoDTO {

	private Long id;
	private String name;
	private String description;
	private Integer userRank;
	private Integer numMembers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getUserRank() {
		return userRank;
	}

	public void setUserRank(Integer userRank) {
		this.userRank = userRank;
	}

	public Integer getNumMembers() {
		return numMembers;
	}

	public void setNumMembers(Integer numMembers) {
		this.numMembers = numMembers;
	}

}
