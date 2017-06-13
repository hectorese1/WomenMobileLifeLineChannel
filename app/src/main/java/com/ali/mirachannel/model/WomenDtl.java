package com.ali.mirachannel.model;

import java.io.Serializable;

public class WomenDtl implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int keyId;
	private String houseID;
	private String houseNumber;
	private String womanId;
	private String pregnentId;
	private String name;
	private String husname;
	private int age;
	private int children;
	private String lmcDate;
	private String status;
	private String uploade;
	private String createdAt;

	private int anc_1;
	private int anc_2;
	private int anc_3;
	private int anc_4;
	
	public WomenDtl() {
		// TODO Auto-generated constructor stub
	}

	public WomenDtl(String womanId) {
		this.womanId = womanId;
	}

	public WomenDtl(String womanId, String lmcDate) {
		this.womanId = womanId;
		this.lmcDate = lmcDate;
	}

	public WomenDtl(String womanId, String name, int age, int children,
			String lmcDate, String status, String uploade) {
		this.womanId = womanId;
		this.name = name;
		this.age = age;
		this.children = children;
		this.lmcDate = lmcDate;
		this.status = status;
		this.uploade = uploade;
	}

	public String getWomanId() {
		return womanId;
	}

	public void setWomanId(String womanId) {
		this.womanId = womanId;
	}

	public String getPregnentId() {
		return pregnentId;
	}

	public void setPregnentId(String pregnentId) {
		this.pregnentId = pregnentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHusname() {
		return husname;
	}

	public void setHusname(String husname) {
		this.husname = husname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getChildren() {
		return children;
	}

	public void setChildren(int children) {
		this.children = children;
	}

	public String getLmcDate() {
		return lmcDate;
	}

	public void setLmcDate(String lmcDate) {
		this.lmcDate = lmcDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUploade() {
		return uploade;
	}

	public void setUploade(String uploade) {
		this.uploade = uploade;
	}

	public int getKeyId() {
		return keyId;
	}

	public void setKeyId(int keyId) {
		this.keyId = keyId;
	}

	public String getHouseID() {
		return houseID;
	}

	public void setHouseID(String houseID) {
		this.houseID = houseID;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}
	
	public int getAnc_1() {
		return anc_1;
	}

	public void setAnc_1(int anc_1) {
		this.anc_1 = anc_1;
	}

	public int getAnc_2() {
		return anc_2;
	}

	public void setAnc_2(int anc_2) {
		this.anc_2 = anc_2;
	}

	public int getAnc_3() {
		return anc_3;
	}

	public void setAnc_3(int anc_3) {
		this.anc_3 = anc_3;
	}

	public int getAnc_4() {
		return anc_4;
	}

	public void setAnc_4(int anc_4) {
		this.anc_4 = anc_4;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}
}
