package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

	private long userId;
	
	private String username;
	private String firstName;
	private String lastName;
	private String middleName;


	private String jwt;
	private List<UserProfile> userProfile ;
	private String userProfileLargeUrl ;
	private String userProfileSmallUrl ;
	private String userProfileMediumUrl ;
	private String userProfileThumbnailUrl ;

	






	public String getUserProfileLargeUrl() {
		return userProfileLargeUrl;
	}
	public void setUserProfileLargeUrl(String userProfileLargeUrl) {
		this.userProfileLargeUrl = userProfileLargeUrl;
	}
	public String getUserProfileSmallUrl() {
		return userProfileSmallUrl;
	}
	public void setUserProfileSmallUrl(String userProfileSmallUrl) {
		this.userProfileSmallUrl = userProfileSmallUrl;
	}
	public String getUserProfileMediumUrl() {
		return userProfileMediumUrl;
	}
	public void setUserProfileMediumUrl(String userProfileMediumUrl) {
		this.userProfileMediumUrl = userProfileMediumUrl;
	}
	public String getUserProfileThumbnailUrl() {
		return userProfileThumbnailUrl;
	}
	public void setUserProfileThumbnailUrl(String userProfileThumbnailUrl) {
		this.userProfileThumbnailUrl = userProfileThumbnailUrl;
	}
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	public List<UserProfile> getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(List<UserProfile> userProfile) {
		this.userProfile = userProfile;
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
	private String email;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
}
