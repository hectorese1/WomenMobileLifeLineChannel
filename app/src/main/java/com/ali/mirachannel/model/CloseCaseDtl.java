package com.ali.mirachannel.model;

import java.io.Serializable;

public class CloseCaseDtl implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int keyId;

	public String getWomenName() {
		return womenName;
	}


	public void setWomenName(String womenName) {
		this.womenName = womenName;
	}


	public int getKeyId() {
		return keyId;
	}
	public void setKeyId(int keyId) {
		this.keyId = keyId;
	}
	public String getPregnentId() {
		return pregnentId;
	}
	public void setPregnentId(String pregnentId) {
		this.pregnentId = pregnentId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getUploade() {
		return uploade;
	}
	public void setUploade(String uploade) {
		this.uploade = uploade;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	private String pregnentId;	
	private int status;
	private String uploade;
	private String createdAt;
	private String womenName;
	private String location;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
