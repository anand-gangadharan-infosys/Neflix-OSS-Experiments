package com.anand.common.model;

public class PostalLocation {

	private String pincode;

	private String postOfficeName;

	private String taluk;

	private String district;

	private String state;

	public PostalLocation(String pincode, String postOfficeName, String taluk, String district, String state) {
		this.pincode = pincode;
		this.postOfficeName = postOfficeName;
		this.taluk = taluk;
		this.district = district;
		this.state = state;

	}

	public String getDistrict() {
		return district;
	}

	public String getPincode() {
		return pincode;
	}

	public String getPostOfficeName() {
		return postOfficeName;
	}

	public String getState() {
		return state;
	}

	public String getTaluk() {
		return taluk;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public void setPostOfficeName(String postOfficeName) {
		this.postOfficeName = postOfficeName;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setTaluk(String taluk) {
		this.taluk = taluk;
	}

}
