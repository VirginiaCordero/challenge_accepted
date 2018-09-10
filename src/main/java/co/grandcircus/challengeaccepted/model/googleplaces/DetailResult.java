package co.grandcircus.challengeaccepted.model.googleplaces;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DetailResult {

	@JsonProperty("formatted_address")
	private String formattedAddress;	
	@JsonProperty("formatted_phone_number")
	private String formattedPhoneNumber;
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
	@JsonProperty("price_level")
	private Integer priceLevel;
	private Double rating;
	private String reference;
	private List<Review> reviews;
	private List<String> types;
	private String url;
	private String website;
	
	public String getFormattedAddress() {
		return formattedAddress;
	}
	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}
	public String getFormattedPhoneNumber() {
		return formattedPhoneNumber;
	}
	public void setFormattedPhoneNumber(String formattedPhoneNumber) {
		this.formattedPhoneNumber = formattedPhoneNumber;
	}
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
	public Integer getPriceLevel() {
		return priceLevel;
	}
	public void setPriceLevel(Integer priceLevel) {
		this.priceLevel = priceLevel;
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
	public List<Review> getReviews() {
		return reviews;
	}
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	public List<String> getTypes() {
		return types;
	}
	public void setTypes(List<String> types) {
		this.types = types;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	
	
}
