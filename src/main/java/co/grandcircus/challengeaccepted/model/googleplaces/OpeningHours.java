package co.grandcircus.challengeaccepted.model.googleplaces;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpeningHours {

	@JsonProperty("open_now")
	private Boolean openNow;

	public Boolean getOpenNow() {
		return openNow;
	}

	public void setOpenNow(Boolean openNow) {
		this.openNow = openNow;
	}

}
