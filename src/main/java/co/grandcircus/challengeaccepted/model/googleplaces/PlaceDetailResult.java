package co.grandcircus.challengeaccepted.model.googleplaces;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlaceDetailResult {
	
	@JsonProperty("result")
	private DetailResult detailResult;

	public DetailResult getDetailResult() {
		return detailResult;
	}

	public void setDetailResult(DetailResult detailResult) {
		this.detailResult = detailResult;
	}

}
