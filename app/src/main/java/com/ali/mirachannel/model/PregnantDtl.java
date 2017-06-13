package com.ali.mirachannel.model;

import java.io.Serializable;

public class PregnantDtl implements Serializable {

	private static final long serialVersionUID = 1L;
	private int keyId;
	private int womanId;
	private int pregId;
	private int anc_1;
	private int anc_2;
	private int anc_3;
	private int anc_4;
	private String lmcDate;
	private String createdAt;

	public PregnantDtl() {
		
	}

	public int getKeyId() {
		return keyId;
	}

	public void setKeyId(int keyId) {
		this.keyId = keyId;
	}

	public int getWomanId() {
		return womanId;
	}

	public void setWomanId(int womanId) {
		this.womanId = womanId;
	}

	public int getPregId() {
		return pregId;
	}

	public void setPregId(int pregId) {
		this.pregId = pregId;
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

	public String getLmcDate() {
		return lmcDate;
	}

	public void setLmcDate(String lmcDate) {
		this.lmcDate = lmcDate;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
