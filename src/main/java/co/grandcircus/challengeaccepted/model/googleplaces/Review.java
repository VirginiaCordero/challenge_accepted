package co.grandcircus.challengeaccepted.model.googleplaces;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Review {

	@JsonProperty("author_name")
	private String authorName;
	@JsonProperty("author_url")
	private String authorUrl;
	@JsonProperty("profile_photo_url")
	private String profilePhotoUrl;
	private String rating;
	@JsonProperty("relative_time_description")
	private String relativeTimeDescription;
	private String text;
	private Long time;
	
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getAuthorUrl() {
		return authorUrl;
	}
	public void setAuthorUrl(String authorUrl) {
		this.authorUrl = authorUrl;
	}
	public String getProfilePhotoUrl() {
		return profilePhotoUrl;
	}
	public void setProfilePhotoUrl(String profilePhotoUrl) {
		this.profilePhotoUrl = profilePhotoUrl;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getRelativeTimeDescription() {
		return relativeTimeDescription;
	}
	public void setRelativeTimeDescription(String relativeTimeDescription) {
		this.relativeTimeDescription = relativeTimeDescription;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	
}
