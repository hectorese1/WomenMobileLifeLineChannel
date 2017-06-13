package com.ali.mirachannel.model;

import java.io.Serializable;

public class ChildDtl implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int keyId;
//	private String houseID;
	private String womanId;
	private String womanName;
	private String childId;
	private String name;
	private String sex;
	private String dob;
	private String status;
	private String upload;
	private String createdAt;
	
	public ChildDtl() {
		// TODO Auto-generated constructor stub
	}
	
	public ChildDtl(String childId) {
		super();
		this.childId = childId;
	}

	public ChildDtl(String childId, String name, String sex, String dob,
			String status, String upload) {
		super();
		this.childId = childId;
		this.name = name;
		this.sex = sex;
		this.dob = dob;
		this.status = status;
		this.upload = upload;
	}
    public String toString(){
        return "keyId "+keyId +"  womanId "+womanId +" womanName "+womanName +"  childId "+childId +" name "+name +"  sex "+sex +" dob "+dob +"  status "+status +" upload "+upload +"  createdAt "+createdAt;
    }

	public int getKeyId() {
		return keyId;
	}

	public void setKeyId(int keyId) {
		this.keyId = keyId;
	}

//	public String getHouseID() {
//		return houseID;
//	}
//
//	public void setHouseID(String houseID) {
//		this.houseID = houseID;
//	}

	public String getWomanId() {
		return womanId;
	}

	public void setWomanId(String womanId) {
		this.womanId = womanId;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getWomanName() {
		return womanName;
	}

	public void setWomanName(String womanName) {
		this.womanName = womanName;
	}


}
