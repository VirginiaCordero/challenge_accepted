package co.grandcircus.challengeaccepted.model.googleplaces;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NearbySearchResults {
	
	private List<Result> results;
	
	@JsonProperty("next_page_token")
	private String nextPageToken;

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	public String getNextPageToken() {
		return nextPageToken;
	}

	public void setNextPageToken(String nextPageToken) {
		this.nextPageToken = nextPageToken;
	}

	@Override
	public String toString() {
		return "NearbySearchResults [results=" + results + "]";
	}

}
