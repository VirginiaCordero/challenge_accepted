package co.grandcircus.challengeaccepted.model.googleplaces;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlusCode {

	// TODO: S and G -- make this a thing.
	@JsonProperty("compound_code")
	private String compoundCode;
	
	@JsonProperty("global_code")
	private String globalCode;
}
