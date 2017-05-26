package com.gastonsanguinetti.contacts.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Contact implements Serializable {
	
	private static final long serialVersionUID = 4870996310219178260L;
	
	@SerializedName("user_id")
	private String userId;
	
	@SerializedName("created_at")
	private String createdAt;
	
	@SerializedName("birth_date")
	private String birthDate;
	
	@SerializedName("first_name")
	private String firstName;
	
	@SerializedName("last_name")
	private String lastName;
	
	private List<Phone> phones = null;
	
	private List<Address> addresses = null;

	private String thumb;

	private String photo;
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
	public String getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public List<Phone> getPhones() {
		return phones;
	}
	
	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}
	
	public List<Address> getAddresses() {
		return addresses;
	}
	
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	
	public String getThumb() {
		return thumb;
	}
	
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	
	public String getPhoto() {
		return photo;
	}
	
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public String getFullName() {
		return firstName + " " + lastName;
	}
}
