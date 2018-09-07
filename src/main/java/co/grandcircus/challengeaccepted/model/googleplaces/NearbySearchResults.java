package co.grandcircus.challengeaccepted.model.googleplaces;

import java.util.List;

public class NearbySearchResults {
	
	private List<Result> results;

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "NearbySearchResults [results=" + results + "]";
	}

}
