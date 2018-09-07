package co.grandcircus.challengeaccepted.model.googleplaces;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Photo {

	private Integer height;

	@JsonProperty("html_attributions")
	private List<String> htmlAttributions;

	@JsonProperty("photo_reference")
	private String photoReference;

	private Integer width;

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public List<String> getHtmlAttributions() {
		return htmlAttributions;
	}

	public void setHtmlAttributions(List<String> htmlAttributions) {
		this.htmlAttributions = htmlAttributions;
	}

	public String getPhotoReference() {
		return photoReference;
	}

	public void setPhotoReference(String photoReference) {
		this.photoReference = photoReference;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

}
