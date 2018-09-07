package co.grandcircus.challengeaccepted.model.googleplaces;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
	
	private Geometry geometry;
	private String icon;
	private String id;
	private String name;
	
	@JsonProperty("opening_hours")
	private OpeningHours openingHours;
	
	private List<Photo> photos;
	
	@JsonProperty("place_id")
	private String placeId;
	
	@JsonProperty("plus_code")
	private PlusCode plusCode;
	
	private Double rating;
	private String reference;
	private String scope;
	private List<String> type;
	private String vicinity;
	public Geometry getGeometry() {
		return geometry;
	}
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public OpeningHours getOpeningHours() {
		return openingHours;
	}
	public void setOpeningHours(OpeningHours openingHours) {
		this.openingHours = openingHours;
	}
	public List<Photo> getPhotos() {
		return photos;
	}
	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	public PlusCode getPlusCode() {
		return plusCode;
	}
	public void setPlusCode(PlusCode plusCode) {
		this.plusCode = plusCode;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public List<String> getType() {
		return type;
	}
	public void setType(List<String> type) {
		this.type = type;
	}
	public String getVicinity() {
		return vicinity;
	}
	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}

}
