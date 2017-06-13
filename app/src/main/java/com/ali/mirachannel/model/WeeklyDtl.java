package com.ali.mirachannel.model;

import java.io.Serializable;

public class WeeklyDtl implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int keyId;
	private String womanId;
	private String pregnentId;
	private int weekNum;
	private int quesOne;
	private int quesTwo;
	private int quesThree;
	private int quesFour;
	private int quesFive;
	
	private String uploaded="0";
	private String createdAt;

    public String toString(){
        String s="KeyId "+keyId+" womanId "+womanId+"  pregnentId "+pregnentId+"  weekNum "+weekNum+"  quesOne "+quesOne +
                 " quesTwo "+quesTwo+"  quesThree "+quesThree+"  quesFour "+quesFour+"  quesFive "+quesFive +" uploaded "+uploaded+
                " createdAt "+createdAt;
        return s;
    }

	public WeeklyDtl() {
		// TODO Auto-generated constructor stub
	}

	public int getKeyId() {
		return keyId;
	}

	public void setKeyId(int keyId) {
		this.keyId = keyId;
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

	public int getWeekNum() {
		return weekNum;
	}

	public void setWeekNum(int weekNum) {
		this.weekNum = weekNum;
	}

	public int getQuesOne() {
		return quesOne;
	}

	public void setQuesOne(int quesOne) {
		this.quesOne = quesOne;
	}

	public int getQuesTwo() {
		return quesTwo;
	}

	public void setQuesTwo(int quesTwo) {
		this.quesTwo = quesTwo;
	}

	public int getQuesThree() {
		return quesThree;
	}

	public void setQuesThree(int quesThree) {
		this.quesThree = quesThree;
	}

	public int getQuesFour() {
		return quesFour;
	}

	public void setQuesFour(int quesFour) {
		this.quesFour = quesFour;
	}

	public int getQuesFive() {
		return quesFive;
	}

	public void setQuesFive(int quesFive) {
		this.quesFive = quesFive;
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
