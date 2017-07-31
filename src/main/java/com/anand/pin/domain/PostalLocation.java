package com.anand.pin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="POSTALLOCATION")
public class PostalLocation {

	@Id
	@Column(name = "PINCODE")
	private String pincode;

	@Column(name = "POSTOFFICENAME")
	private String postOfficeName;

	@Column(name = "TALUK")
	private String taluk;

	@Column(name = "DISTRICT")
	private String district;

	@Column(name = "STATE")
	private String state;
	
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
