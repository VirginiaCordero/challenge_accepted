package co.grandcircus.challengeaccepted.model.googleplaces;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlusCode {

	@JsonProperty("compound_code")
	private String compoundCode;
	
	@JsonProperty("global_code")
	private String globalCode;

	public String getCompoundCode() {
		return compoundCode;
	}

	public void setCompoundCode(String compoundCode) {
		this.compoundCode = compoundCode;
	}

	public String getGlobalCode() {
		return globalCode;
	}

	public void setGlobalCode(String globalCode) {
		this.globalCode = globalCode;
	}
}
