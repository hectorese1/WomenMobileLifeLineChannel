package com.ali.mirachannel.model;

import java.io.Serializable;

public class FamilyPlanning implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int keyId;
	private String name;
	private int cycleDays;
	private String lmcDate;
	
	public FamilyPlanning() {
		// TODO Auto-generated constructor stub
	}
	
	public int getKeyId() {
		return keyId;
	}

	public void setKeyId(int keyId) {
		this.keyId = keyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCycleDays() {
		return cycleDays;
	}

	public void setCycleDays(int cycleDays) {
		this.cycleDays = cycleDays;
	}

	public String getLmcDate() {
		return lmcDate;
	}

	public void setLmcDate(String lmcDate) {
		this.lmcDate = lmcDate;
	}

}
