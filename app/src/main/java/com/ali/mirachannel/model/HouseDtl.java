package com.ali.mirachannel.model;

import java.io.Serializable;


public class HouseDtl implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int keyId;
	public int getKeyId() {
		return keyId;
	}

	public void setKeyId(int keyId) {
		this.keyId = keyId;
	}

	private String houseID;
	private String houseNumber;
	private String familyHead;
	private String landMark = "none";
	private long latitude;
	private long longitude;
	private int familyMembers;
	private int marriedWomen;
	private int unmarriedWomen;
	private int adolecenceGorls;
	private int childrens;
	private String uploaded;
	private String createdAt;
	
	public HouseDtl() {
	}
	
	public HouseDtl(String houseNumber) {
		this.houseNumber = houseNumber;
	}
	
	public HouseDtl(String houseID, String houseNumber, String familyHead,
			String landMark, long latitude, long longitude, int familyMembers,
			int marriedWomen, int unmarriedWomen, int adolecenceGorls,
			int childrens) {
		super();
		this.houseID = houseID;
		this.houseNumber = houseNumber;
		this.familyHead = familyHead;
		this.landMark = landMark;
		this.latitude = latitude;
		this.longitude = longitude;
		this.familyMembers = familyMembers;
		this.marriedWomen = marriedWomen;
		this.unmarriedWomen = unmarriedWomen;
		this.adolecenceGorls = adolecenceGorls;
		this.childrens = childrens;
	}



	public String getHouseID() {
		return houseID;
	}

	public void setHouseID(String houseID) {
		this.houseID = houseID;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getFamilyHead() {
		return familyHead;
	}

	public void setFamilyHead(String familyHead) {
		this.familyHead = familyHead;
	}

	public String getLandMark() {
		return landMark;
	}

	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}

	public long getLatitude() {
		return latitude;
	}

	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}

	public long getLongitude() {
		return longitude;
	}

	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}

	public int getFamilyMembers() {
		return familyMembers;
	}

	public void setFamilyMembers(int familyMembers) {
		this.familyMembers = familyMembers;
	}

	public int getMarriedWomen() {
		return marriedWomen;
	}

	public void setMarriedWomen(int marriedWomen) {
		this.marriedWomen = marriedWomen;
	}

	public int getUnmarriedWomen() {
		return unmarriedWomen;
	}

	public void setUnmarriedWomen(int unmarriedWomen) {
		this.unmarriedWomen = unmarriedWomen;
	}

	public int getAdolecenceGorls() {
		return adolecenceGorls;
	}

	public void setAdolecenceGorls(int adolecenceGorls) {
		this.adolecenceGorls = adolecenceGorls;
	}

	public int getChildrens() {
		return childrens;
	}

	public void setChildrens(int childrens) {
		this.childrens = childrens;
	}

	public String getUploaded() {
		return uploaded;
	}

	public void setUploaded(String uploaded) {
		this.uploaded = uploaded;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	
}
