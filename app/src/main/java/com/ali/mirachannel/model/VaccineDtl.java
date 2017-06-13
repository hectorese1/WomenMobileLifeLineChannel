package com.ali.mirachannel.model;

import java.io.Serializable;

public class VaccineDtl implements Serializable{
	private String vaccineName;
	private String vaccineDepend;
	private int startDay;
	private int endDay;
	private int diffDay;
    private String vaccineTakenDate="NA";

    public String getVaccineTakenDate() {
        return vaccineTakenDate;
    }

    public void setVaccineTakenDate(String vaccineTakenDate) {
        this.vaccineTakenDate = vaccineTakenDate;
    }

    public String getVaccineName() {
		return vaccineName;
	}
	public void setVaccineName(String vaccineName) {
		this.vaccineName = vaccineName;
	}
	public String getVaccineDepend() {
		return vaccineDepend;
	}
	public void setVaccineDepend(String vaccineDepend) {
		this.vaccineDepend = vaccineDepend;
	}
	public int getStartDay() {
		return startDay;
	}
	public void setStartDay(int startDay) {
		this.startDay = startDay;
	}
	public int getEndDay() {
		return endDay;
	}
	public void setEndDay(int endDay) {
		this.endDay = endDay;
	}
	public int getDiffDay() {
		return diffDay;
	}
	public void setDiffDay(int diffDay) {
		this.diffDay = diffDay;
	}

    public String toString(){
        return "vaccineName "+vaccineName +"  vaccineDepend "+vaccineDepend +" startDay "+startDay +"  endDay "+endDay +" diffDay "+diffDay +" vaccineTakenDate "+vaccineTakenDate;
    }
	
	
}
