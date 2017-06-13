package com.ali.mirachannel;

import java.io.Serializable;

public 	class VaccStatus implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String vaccName;
	int PPF;
	public String getVaccName() {
		return vaccName;
	}
	public void setVaccName(String vaccName) {
		this.vaccName = vaccName;
	}
	public int getPPF() {
		return PPF;
	}
	public void setPPF(int pPF) {
		PPF = pPF;
	}		
}
