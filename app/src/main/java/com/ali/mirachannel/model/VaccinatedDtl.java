package com.ali.mirachannel.model;

import java.io.Serializable;


public class VaccinatedDtl implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int keyId;
	private String childId;
	private String name;
	private String date;
	private String upload;
	private String createdAt;
	public int getKeyId() {
		return keyId;
	}
	public void setKeyId(int keyId) {
		this.keyId = keyId;
	}
	public String getChildId() {
		return childId;
	}
	public void setChildId(String childId) {
		this.childId = childId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getUpload() {
		return upload;
	}
	public void setUpload(String upload) {
		this.upload = upload;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

    public String toString(){
        return "keyId "+keyId +"  childId "+childId +" name "+name +"  date "+date +" upload "+upload +"  createdAt "+createdAt;
    }
}
