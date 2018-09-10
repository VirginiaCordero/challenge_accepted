package co.grandcircus.challengeaccepted.model.googleplaces;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpeningHours {

	@JsonProperty("open_now")
	private Boolean openNow;
	@JsonProperty("weekday_text")
	private List<String> weekdayText;
	
	public Boolean getOpenNow() {
		return openNow;
	}
	public void setOpenNow(Boolean openNow) {
		this.openNow = openNow;
	}
	public List<String> getWeekdayText() {
		return weekdayText;
	}
	public void setWeekdayText(List<String> weekdayText) {
		this.weekdayText = weekdayText;
	}

}
